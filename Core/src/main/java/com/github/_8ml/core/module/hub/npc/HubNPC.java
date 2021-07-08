package com.github._8ml.core.module.hub.npc;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.events.event.ServerReadyEvent;
import com.github._8ml.core.game.GameInfo;
import com.github._8ml.core.npc.NPC;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.storage.SQL;
import com.github._8ml.core.ui.PromptGUI;
import com.github._8ml.core.ui.component.Component;
import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.utils.DeveloperMode;
import com.github._8ml.core.utils.PluginMessenger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class HubNPC implements Listener {

    static {
        try {

            Core.instance.sql.createTable("CREATE TABLE IF NOT EXISTS hubNPC (`game` VARCHAR(255) NOT NULL" +
                    ", `uuid` VARCHAR(255)" +
                    ", `world` VARCHAR(255)" +
                    ", `x` DOUBLE" +
                    ", `y` DOUBLE" +
                    ", `z` DOUBLE" +
                    ", `yaw` FLOAT" +
                    ", `pitch` FLOAT");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final SQL sql = Core.instance.sql;

    public HubNPC(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static void addNPC(String game, String uuid, Location location) {

        update(game, uuid, location);
        spawnNPC(game, uuid, location);

    }

    private static void spawnNPC(String game, String uuid, Location location) {
        NPC npc = new NPC(game, game, uuid, player -> {
            DeveloperMode.log("Clicked " + game);

            new PromptGUI(MPlayer.getMPlayer(player.getName()), game) {

                @Override
                protected boolean autoRefresh() {
                    return true;
                }

                @Override
                protected long autoRefreshInterval() {
                    return TimeUnit.SECONDS.toMillis(5);
                }

                @Override
                protected Map<Integer, Component> content() {
                    Map<Integer, Component> components = new HashMap<>();

                    List<GameInfo> servers = GameInfo.getServers(game);
                    List<GameInfo> sortedServers = new ArrayList<>();
                    for (GameInfo info : servers) {
                        if (info.isOffline()) continue;
                        if (!info.getState().equals("Waiting")) continue;
                        sortedServers.add(info);
                    }
                    for (GameInfo info : servers) {
                        if (info.isOffline()) continue;
                        if (info.getState().equals("Waiting")) continue;
                        sortedServers.add(info);
                    }

                    if (sortedServers.size() == 0) {
                        Button entry = new Button(MessageColor.COLOR_ERROR + "No Servers", Material.RED_WOOL, null);
                        entry.setOnClick(() -> {
                            getPlayer().getPlayer().closeInventory();
                        });

                        components.put(13, entry);
                    } else {
                        for (GameInfo info : sortedServers) {

                            Button entry = new Button(MessageColor.COLOR_HIGHLIGHT + info.getServerName(),
                                    info.getState().equals("Waiting") ? Material.GREEN_WOOL : Material.ORANGE_WOOL, null);
                            entry.setLore(new String[]{
                                    "",
                                    ChatColor.WHITE + "Game: " + MessageColor.COLOR_MAIN + game,
                                    ChatColor.WHITE + "State: " + MessageColor.COLOR_MAIN + info.getState(),
                                    "",
                                    ChatColor.WHITE + "Players: " + MessageColor.COLOR_HIGHLIGHT
                                            + info.getOnlinePlayers() + "/" + info.getMaxPlayers()
                            });

                        }
                    }

                    return components;
                }

            };

        });

        npc.spawn(location);
    }

    private static boolean exists(String game) {
        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM hubNPC WHERE `game`=?");
            st.setString(1, game);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                sql.closeConnection(st);
                return true;
            }
            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static void update(String game, String uuid, Location location) {
        if (exists(game)) {

            try {
                PreparedStatement st = sql.preparedStatement("DELETE FROM hubNPC WHERE `game`=?");
                st.setString(1, game);
                try {
                    st.executeUpdate();
                } finally {
                    sql.closeConnection(st);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        try {

            PreparedStatement st = sql.preparedStatement("INSERT INTO hubNPC (`game`, `uuid`, `world`, `x`, `y`, `z`, `yaw`, `pitch`)" +
                    " VALUES (?,?,?,?,?,?,?,?)");

            st.setString(1, game);
            st.setString(2, uuid);
            st.setString(3, Objects.requireNonNull(location.getWorld()).getName());
            st.setDouble(4, location.getX());
            st.setDouble(5, location.getY());
            st.setDouble(6, location.getZ());
            st.setFloat(7, location.getYaw());
            st.setFloat(8, location.getPitch());

            try {
                st.execute();
            } finally {
                sql.closeConnection(st);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @EventHandler
    public void onServerReady(ServerReadyEvent e) {
        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM hubNPC");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                Location location = new Location(
                        Bukkit.getWorld(rs.getString("world")),
                        rs.getDouble("x"),
                        rs.getDouble("y"),
                        rs.getDouble("z"),
                        rs.getFloat("yaw"),
                        rs.getFloat("pitch")
                );

                spawnNPC(rs.getString("game"), rs.getString("uuid"), location);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
