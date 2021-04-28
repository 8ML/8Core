package club.mineplay.core.punishment;
/*
Created by Sander on 4/27/2021
*/

import club.mineplay.core.Main;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.storage.SQL;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Punishment {

    public enum PunishType {

        BAN, MUTE, WARN, KICK;

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

            StringBuilder b = new StringBuilder();

            char f = name().charAt(0);

            b.append(Character.valueOf(f).toString().toUpperCase());

            for (int i = 1; i < name().toCharArray().length; i++) {
                b.append(Character.valueOf(name().toCharArray()[i]).toString().toLowerCase());
            }

            return b.toString();

        }
    }

    public static class PunishTime {

        private final long duration;

        private final TimeUnit unit;

        private final boolean permanent;

        public PunishTime(TimeUnit unit, long duration) {
            this.permanent = false;
            this.unit = unit;
            this.duration = duration * unit.multiplier;
        }

        public PunishTime(long duration) {

            this.duration = duration;
            this.permanent = duration == 0;

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

        public PunishTime() {
            this.duration = 0L;
            this.permanent = true;
            this.unit = TimeUnit.PERMANENT;
        }

        public long getDuration() {
            return this.duration;
        }

        public TimeUnit getUnit() {
            return this.unit;
        }

        public double getTimeLeft() {

            double duration = Long.valueOf(this.duration).doubleValue();
            double d = duration / this.unit.multiplier;
            return BigDecimal.valueOf(d).setScale(1, RoundingMode.HALF_UP).doubleValue();
        }

    }

    private final PunishType type;

    private final SQL sql = Main.instance.sql;

    public Punishment(PunishType type) {
        this.type = type;
    }

    public abstract void onExecute();
    public abstract String getPunishMessage();

    public void execute(MPlayer executor, MPlayer target, String reason, PunishTime time) {

        this.onExecute();

        try {

            long now = System.currentTimeMillis();
            long end = now + time.getDuration();

            PreparedStatement st = sql.preparedStatement("INSERT INTO punishments (`uuid`, `playerName`, `executor`, `when`, `end`, `duration`, `reason`, `type`, `active`) " +
                    "VALUES (?,?,?,?,?,?,?,?,?)");

            st.setString(1, target.getUUID());
            st.setString(2, target.getPlayerStr());
            st.setString(3, executor.getPlayerStr());
            st.setLong(4, now);
            st.setLong(5, end);
            st.setLong(6, time.getDuration());
            st.setString(7, reason);
            st.setString(8, this.type.toString());
            st.setBoolean(9, true);

            try {
                st.execute();
            } finally {
                sql.getConnection().close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public PunishType getType() {
        return type;
    }


    public static PunishInfo getActivePunishment(MPlayer player, PunishType type) {

        SQL sql = Main.instance.sql;

        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM punishments WHERE uuid=? AND type=? AND active=?");
            st.setString(1, player.getUUID());
            st.setString(2, type.name());
            st.setBoolean(3, true);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                if (rs.getLong("end") < System.currentTimeMillis()) return new PunishInfo();
                long timeLeft = rs.getLong("end") - System.currentTimeMillis();

                PunishInfo pInfo = new PunishInfo(player, MPlayer.getMPlayer(rs.getString("executor")),
                        new PunishTime(timeLeft), PunishType.valueOf(rs.getString("type")), rs.getString("reason"));

                sql.getConnection().close();

                return pInfo;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new PunishInfo();

    }

}
