package club.mineplay.core.punishment;
/*
Created by Sander on 4/28/2021
*/

import club.mineplay.core.Main;
import club.mineplay.core.player.MPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PunishInfo {

    private final MPlayer player;
    private final MPlayer executor;
    private final Punishment.PunishTime punishTime;
    private final Punishment.PunishType punishType;
    private final String reason;

    private long when;
    private long end;
    private boolean active = true;

    public PunishInfo(MPlayer player, MPlayer executor, Punishment.PunishTime punishTime, Punishment.PunishType type, String reason) {
        this.player = player;
        this.executor = executor;
        this.punishTime = punishTime;
        this.punishType = type;
        this.reason = reason;

        try {

            PreparedStatement st = Main.instance.sql.preparedStatement("SELECT * FROM punishments WHERE uuid=? AND type=? AND active=?");
            st.setString(1, player.getUUID());
            st.setString(2, this.punishType.name());
            st.setBoolean(3, true);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                this.when = rs.getLong("when");
                this.end = rs.getLong("end");

            }

            Main.instance.sql.getConnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public PunishInfo() {
        this.player = null;
        this.executor = null;
        this.punishTime = null;
        this.reason = null;
        this.punishType = null;
        this.active = false;
    }

    public MPlayer getPlayer() {
        return player;
    }

    public MPlayer getExecutor() {
        return executor;
    }

    public Punishment.PunishTime getPunishTime() {
        return punishTime;
    }

    public String getReason() {
        return reason;
    }

    public boolean isActive() {
        if (!this.active) return false;
        if (System.currentTimeMillis() >= this.end) {

            try {

                PreparedStatement st = Main.instance.sql.preparedStatement("UPDATE punishments SET active=? WHERE uuid=? AND type=?");
                st.setBoolean(1, false);
                st.setString(2, this.player.getUUID());

                try {
                    st.executeUpdate();
                } finally {
                    Main.instance.sql.getConnection().close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return false;

        }

        return true;
    }
}
