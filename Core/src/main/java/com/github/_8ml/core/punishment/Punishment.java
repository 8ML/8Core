package com.github._8ml.core.punishment;
/*
Created by @8ML (https://github.com/8ML) on 4/27/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.storage.SQL;
import com.github._8ml.core.Core;
import com.github._8ml.core.utils.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


/**
 * This class handles the punishment times and issuing.
 * It is extended by the different types of punishments.
 * When issuing a punishment, the subclasses has to be instanced.
 */
public abstract class Punishment {

    public enum PunishType {

        BAN, MUTE, WARN, KICK

    }

    public enum TimeUnit {
        SECONDS(1000),
        MINUTES(1000 * 60),
        HOURS(1000 * 60 * 60),
        DAYS(1000 * 60 * 60 * 24),
        PERMANENT(-1);

        public int multiplier;

        TimeUnit(int multiplier) {
            this.multiplier = multiplier;
        }

        public String getFormatted() {

            return StringUtils.formatCapitalization(name());

        }
    }

    public static class PunishTime {

        private final long duration;
        private final TimeUnit unit;
        private final boolean permanent;


        /**
         * @param unit     The timeunit of the duration (e.g. TimeUnit.SECONDS)
         * @param duration The duration in the unit (e.g. 10)
         */
        public PunishTime(TimeUnit unit, long duration) {
            this.permanent = false;
            this.unit = unit;
            this.duration = duration * unit.multiplier;
        }


        /**
         * @param duration The duration in milliseconds (Will calculate the unit from that information)
         */
        public PunishTime(long duration) {

            this.duration = duration;
            this.permanent = duration == 0;

            if (this.permanent) {
                this.unit = TimeUnit.PERMANENT;
                return;
            }

            final long maxSecondsMillis = (1000 * 60);
            final long maxMinutesMillis = (maxSecondsMillis * 60);
            final long maxHoursMillis = (maxMinutesMillis * 24);

            if (duration > maxSecondsMillis) {
                if (duration > maxMinutesMillis) {
                    if (duration > maxHoursMillis) {
                        this.unit = TimeUnit.DAYS;
                    } else {
                        this.unit = TimeUnit.HOURS;
                    }
                } else {
                    this.unit = TimeUnit.MINUTES;
                }
            } else {
                this.unit = TimeUnit.SECONDS;
            }

        }


        /**
         * Using this constructor will set the time to permanent
         */
        public PunishTime() {
            this.duration = 0L;
            this.permanent = true;
            this.unit = TimeUnit.PERMANENT;
        }


        /**
         * @return The duration left in milliseconds
         */
        public long getDuration() {
            return this.duration;
        }


        /**
         * @return The unit calculated from the time left
         */
        public TimeUnit getUnit() {
            return this.unit;
        }


        /**
         * @return The time left as a double number in this::unit
         */
        public double getTimeLeft() {

            double duration = Long.valueOf(this.duration).doubleValue();
            double d = duration / this.unit.multiplier;
            return BigDecimal.valueOf(d).setScale(1, RoundingMode.HALF_UP).doubleValue();
        }

    }

    private final PunishType type;
    private final SQL sql = Core.instance.sql;


    /**
     * @param type The type of the punishment
     */
    public Punishment(PunishType type) {
        this.type = type;
    }


    /**
     * This method is called on execute
     */
    public abstract void onExecute();


    /**
     * @return The punish message (Specified in the subclass)
     */
    public abstract String getPunishMessage();


    /**
     * @param executor The player that issued the punishment
     * @param target   The player that is getting the punishment
     * @param reason   The reason of the punishment
     * @param time     The punishment length
     */
    public void execute(MPlayer executor, MPlayer target, String reason, PunishTime time) {

        this.onExecute();

        try {

            long now = System.currentTimeMillis();
            long end = now + time.getDuration();
            boolean active = (!this.type.equals(PunishType.WARN) && !this.type.equals(PunishType.KICK));

            if (time.permanent) {
                end = 0;
            }

            PreparedStatement check = sql.preparedStatement("SELECT * FROM punishments WHERE uuid=? AND type=? AND active=?");
            check.setString(1, target.getUUID());
            check.setString(2, this.type.toString());
            check.setBoolean(3, true);
            ResultSet rs = check.executeQuery();
            while (rs.next()) {

                PreparedStatement update = sql.preparedStatement("UPDATE punishments SET active=? WHERE uuid=? AND type=?");
                update.setBoolean(1, false);
                update.setString(2, target.getUUID());
                update.setString(3, this.type.toString());

                update.executeUpdate();
                sql.closeConnection(update);

            }
            sql.closeConnection(check);

            PreparedStatement st = sql.preparedStatement("INSERT INTO punishments (`uuid`, `playerName`, `executor`, `when`, " +
                    "`end`, `duration`, `reason`, `type`, `active`, `permanent`, `uid`) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?)");

            st.setString(1, target.getUUID());
            st.setString(2, target.getPlayerStr());
            st.setString(3, executor.getPlayerStr());
            st.setLong(4, now);
            st.setLong(5, end);
            st.setLong(6, time.getDuration());
            st.setString(7, reason);
            st.setString(8, this.type.toString());
            st.setBoolean(9, active);
            st.setBoolean(10, end == 0);
            st.setString(11, UUID.randomUUID().toString());

            try {
                st.execute();
            } finally {
                sql.closeConnection(st);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * @return The punishment type registered for the subclass
     */
    public PunishType getType() {
        return type;
    }


    /**
     * @param player The player to look up
     * @param type   The type to look for
     * @return Info of the current active punishment of the specified type
     */
    public static PunishInfo getActivePunishment(MPlayer player, PunishType type) {

        SQL sql = Core.instance.sql;

        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM punishments WHERE uuid=? AND type=? AND active=?");
            st.setString(1, player.getUUID());
            st.setString(2, type.name());
            st.setBoolean(3, true);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                long timeLeft = 0;

                if (rs.getLong("end") != 0 && rs.getLong("when") != 0) {
                    if (rs.getLong("end") < System.currentTimeMillis()) {
                        sql.closeConnection(st);
                        return new PunishInfo();
                    }
                    timeLeft = rs.getLong("end") - System.currentTimeMillis();
                }

                PunishInfo pInfo = new PunishInfo(player, MPlayer.getMPlayer(rs.getString("executor")),
                        new PunishTime(timeLeft), PunishType.valueOf(rs.getString("type")), rs.getString("reason"),
                        rs.getString("uid"));

                sql.closeConnection(st);

                return pInfo;

            }

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new PunishInfo();

    }


    /**
     * @param player The player to loop up
     * @param type   The type to look for
     * @return A list of all issued punishments of the type specified
     */
    public static List<PunishInfo> getPunishments(MPlayer player, PunishType type) {

        SQL sql = Core.instance.sql;

        List<PunishInfo> activePunishments = new ArrayList<>();
        List<PunishInfo> punishments = new ArrayList<>();

        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM punishments WHERE uuid=? AND type=?");
            st.setString(1, player.getUUID());
            st.setString(2, type.toString());
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                long when = rs.getBoolean("permanent") ? 0 : rs.getLong("when");

                if (rs.getBoolean("active"))
                    activePunishments.add(new PunishInfo(player, MPlayer.getMPlayer(rs.getString("executor")),
                            new PunishTime(rs.getLong("end") - when), type,
                            rs.getString("reason"), rs.getString("uid")));
                else punishments.add(new PunishInfo(player, MPlayer.getMPlayer(rs.getString("executor")),
                        new PunishTime(rs.getLong("end") - when), type,
                        rs.getString("reason"), rs.getString("uid")));
            }

            sql.closeConnection(st);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<PunishInfo> finalList = new ArrayList<>();
        finalList.addAll(punishments);
        finalList.addAll(activePunishments);
        Collections.reverse(finalList);

        return finalList;

    }


    /**
     * @param id The id of the punishment to remove
     */
    public static void removePunishment(int id) {
        SQL sql = Core.instance.sql;

        try {

            PreparedStatement st = sql.preparedStatement("UPDATE punishments SET active=? WHERE id=?");
            st.setBoolean(1, false);
            st.setInt(2, id);

            try {

                st.executeUpdate();

            } finally {
                sql.closeConnection(st);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
