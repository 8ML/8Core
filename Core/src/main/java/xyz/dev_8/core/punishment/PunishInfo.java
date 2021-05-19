package xyz.dev_8.core.punishment;
/*
Created by @8ML (https://github.com/8ML) on 4/28/2021
*/

import xyz.dev_8.core.Core;
import xyz.dev_8.core.player.MPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PunishInfo {

    private final MPlayer player;
    private final MPlayer executor;
    private final Punishment.PunishTime punishTime;
    private final Punishment.PunishType punishType;
    private final String reason;
    private final String uid;

    private int id;
    private long when;
    private long end;
    private boolean active = true;
    private boolean permanent = false;

    public PunishInfo(MPlayer player, MPlayer executor, Punishment.PunishTime punishTime, Punishment.PunishType type, String reason, String uid) {
        this.player = player;
        this.executor = executor;
        this.punishTime = punishTime;
        this.punishType = type;
        this.reason = reason;
        this.uid = uid;
        if (punishTime.getUnit().equals(Punishment.TimeUnit.PERMANENT)) this.permanent = true;

        try {

            PreparedStatement st = Core.instance.sql.preparedStatement("SELECT * FROM punishments WHERE uuid=? AND type=? AND uid=?");
            st.setString(1, player.getUUID());
            st.setString(2, this.punishType.name());
            st.setString(3, this.uid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                this.id = rs.getInt("id");
                this.when = rs.getLong("when");
                this.end = rs.getLong("end");
                this.active = rs.getBoolean("active");

            }

            Core.instance.sql.closeConnection(st);

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
        this.uid = null;
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

    public String getUid() {
        return uid;
    }

    public Punishment.PunishTime getOriginalTime() {

        return new Punishment.PunishTime(this.end - this.when);

    }

    public int getID() {
        return this.id;
    }

    public boolean isActive() {
        if (!this.active) return false;
        if (this.permanent) return true;
        if (System.currentTimeMillis() >= this.end) {

            try {

                PreparedStatement st = Core.instance.sql.preparedStatement("UPDATE punishments SET active=? WHERE uuid=? AND type=?");
                st.setBoolean(1, false);
                st.setString(2, this.player.getUUID());
                st.setString(3, this.punishType.toString());

                try {
                    st.executeUpdate();
                } finally {
                    Core.instance.sql.closeConnection(st);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return false;

        }

        return true;
    }
}
