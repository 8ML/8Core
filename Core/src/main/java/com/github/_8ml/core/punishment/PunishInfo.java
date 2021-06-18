package com.github._8ml.core.punishment;
/*
Created by @8ML (https://github.com/8ML) on 4/28/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.Core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * PunishInfo is used to reference the information of a punishment.
 * All punishment references must return an instance of this class.
 */
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


    /**
     * @param player     The punished player
     * @param executor   The issuing player
     * @param punishTime The time of the punishment
     * @param type       The type of the punishment
     * @param reason     The reason of the punishment
     * @param uid        The unique id of the punishment
     */
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


    /**
     * Used to reference an empty punishment
     */
    public PunishInfo() {
        this.player = null;
        this.executor = null;
        this.punishTime = null;
        this.reason = null;
        this.punishType = null;
        this.active = false;
        this.uid = null;
    }


    /**
     * @return The player who was issued the punishment
     */
    public MPlayer getPlayer() {
        return player;
    }


    /**
     * @return The player who issued the punishment
     */
    public MPlayer getExecutor() {
        return executor;
    }


    /**
     * @return The punish time instance (Stores the time left and formats it correctly)
     */
    public Punishment.PunishTime getPunishTime() {
        return punishTime;
    }


    /**
     * @return The reason for the punishment
     */
    public String getReason() {
        return reason;
    }


    /**
     * @return The punishment's unique id (Used to separate the punishments easier)
     */
    public String getUid() {
        return uid;
    }


    /**
     * @return When punishment was issued in epoch milliseconds
     */
    public long getWhen() {
        return this.when;
    }


    /**
     * @return The original time of the punishment (The total time the punishment was issued for)
     */
    public Punishment.PunishTime getOriginalTime() {

        if (this.permanent) return new Punishment.PunishTime(0);
        return new Punishment.PunishTime(this.end - this.when);

    }


    /**
     * @return The id of the punishment
     */
    public int getID() {
        return this.id;
    }


    /**
     * @return Will return true if the punishment is active
     */
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
