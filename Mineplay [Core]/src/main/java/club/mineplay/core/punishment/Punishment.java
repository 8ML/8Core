package club.mineplay.core.punishment;
/*
Created by Sander on 4/27/2021
*/

import club.mineplay.core.Main;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.storage.SQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Punishment {

    enum PunishType {

        BAN, MUTE, WARN, KICK;

    }

    static class PunishTime {

        private final long duration;

        private final int seconds;
        private final int minutes;
        private final int hours;
        private final int days;

        private final boolean permanent;

        public PunishTime(int seconds, int minutes, int hours, int days) {
            this.seconds = seconds;
            this.minutes = minutes;
            this.hours = hours;
            this.days = days;
            this.permanent = false;
            this.duration = (seconds * 1000) + ((minutes * 60) * 1000) + (((hours * 60) * 60) * 1000) + ((((days * 24) * 60) * 60) * 1000);
        }

        public PunishTime(long duration) {
            this.duration = duration;
            this.permanent = duration == 0;

            double dur = Long.valueOf(duration).doubleValue();

            double days = (dur / (1000 * 60 * 60 * 24));
            double dE = dur * (days - Math.floor(days));

            double hours = (dE / (1000 * 60 * 60));
            double hE = dE * (hours - Math.floor(hours));

            double minutes = (hE / (1000 * 60));
            double mE = hE * (minutes - Math.floor(minutes));

            double seconds = (mE / 1000);

            this.days = (int) Math.floor(days);
            this.hours = (int) Math.floor(hours);
            this.minutes = (int) Math.floor(minutes);
            this.seconds = (int) Math.floor(seconds);
        }

        public PunishTime() {
            this.seconds = 0;
            this.minutes = 0;
            this.hours = 0;
            this.days = 0;
            this.duration = 0L;
            this.permanent = true;
        }

        public long getDuration() {
            return this.duration;
        }

        public int getSeconds() {
            return this.seconds;
        }

        public int getMinutes() {
            return this.minutes;
        }

        public int getHours() {
            return this.hours;
        }

        public int getDays() {
            return this.days;
        }
    }

    private final PunishType type;

    private final SQL sql = Main.instance.sql;

    public Punishment(PunishType type) {
        this.type = type;
    }

    public abstract String getPunishMessage();

    public void execute(MPlayer executor, MPlayer target, String reason, PunishTime time) {

        try {

            long now = System.currentTimeMillis();
            long end = now + time.getDuration();

            PreparedStatement st = sql.preparedStatement("INSERT INTO punishments (uuid, playerName, executor, when, end, duration, active) " +
                    "VALUES (?,?,?,?,?,?,?)");

            st.setString(1, target.getUUID());
            st.setString(2, target.getPlayerStr());
            st.setString(3, executor.getPlayerStr());
            st.setLong(4, now);
            st.setLong(5, end);
            st.setLong(6, time.getDuration());
            st.setBoolean(7, true);

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
}
