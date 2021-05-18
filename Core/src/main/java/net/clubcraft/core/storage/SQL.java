package net.clubcraft.core.storage;
/*
Created by @8ML (https://github.com/8ML) on 4/23/2021
*/

import net.clubcraft.core.Core;
import net.clubcraft.core.events.event.UpdateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQL implements Listener {

    private final String db, user, host, password;
    private final int port;

    private final List<PreparedStatement> statements = new ArrayList<>();

    private Connection connection;
    private int cleanupCount = 0;

    public SQL(String db, String user, String host, String password, int port) {
        this.db = db;
        this.user = user;
        this.host = host;
        this.password = password;
        this.port = port;
        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);
    }

    public void init() {
        try {
            createTable("CREATE TABLE IF NOT EXISTS users (`id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL" +
                    ", `uuid` VARCHAR(255) NOT NULL, `playerName` VARCHAR(30) NOT NULL, `rank` VARCHAR(30) NOT NULL" +
                    ", `xp` INT NOT NULL, `coins` INT NOT NULL, `firstJoin` BIGINT NOT NULL, `signature` MEDIUMTEXT)");
            createTable("CREATE TABLE IF NOT EXISTS proxy (`proxyPlayer` VARCHAR(100) PRIMARY KEY NOT NULL)");
            createTable("CREATE TABLE IF NOT EXISTS punishments (`id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL" +
                    ", `uuid` VARCHAR(255) NOT NULL" +
                    ", `playerName` VARCHAR(100) NOT NULL" +
                    ", `executor` VARCHAR(100) NOT NULL" +
                    ", `when` BIGINT NOT NULL" +
                    ", `end` BIGINT NOT NULL" +
                    ", `duration` BIGINT NOT NULL" +
                    ", `reason` VARCHAR(255) NOT NULL" +
                    ", `type` VARCHAR(100) NOT NULL" +
                    ", `active` BIT NOT NULL" +
                    ", `uid` VARCHAR(255) NOT NULL)");
            createTable("CREATE TABLE IF NOT EXISTS friends (`uuid` VARCHAR(255) PRIMARY KEY NOT NULL" +
                    ", `friendList` LONGTEXT NOT NULL" +
                    ", `requests` LONGTEXT NOT NULL)");
            createTable("CREATE TABLE IF NOT EXISTS preferences (`uuid` VARCHAR(255) NOT NULL" +
                    ", `key` VARCHAR(255) NOT NULL" +
                    ", `value` VARCHAR(255) NOT NULL)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean testConnection() {

        try {

            boolean success = connect();
            if (success) {
                this.getConnection().close();
            }

            return success;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean connect() {

        synchronized (this) {
            try {

                if (this.getConnection() != null) {
                    if (!this.getConnection().isClosed()) return false;
                }

                Class.forName("com.mysql.jdbc.Driver").newInstance();

                setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + "/"+ this.db
                        + "?" + "user=" + this.user + "&password=" + this.password));

                statements.clear();
                return true;

            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        return false;

    }

    public PreparedStatement preparedStatement(String sql) throws SQLException {
        pollCleanup();
        connect();
        PreparedStatement st = this.getConnection().prepareStatement(sql);
        statements.add(st);
        return st;
    }

    private void createTable(String sql) throws SQLException {
        try {
            connect();
            PreparedStatement stmt = this.getConnection().prepareStatement(sql);
            stmt.execute();
            stmt.close();
        } finally {
            getConnection().close();
        }
    }

    public void closeConnection(PreparedStatement call) throws SQLException {

        this.statements.remove(call);
        if (this.statements.isEmpty()) {
            this.getConnection().close();
        }

        Core.instance.getLogger().info("DEBUG: " + statements.size() + " " + Arrays.toString(statements.toArray()));

    }

    private void setConnection(Connection connection) {
        this.connection = connection;
    }

    private Connection getConnection() {
        return this.connection;
    }

    private void pollCleanup() {
        this.cleanupCount = 0;
    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (!e.getType().equals(UpdateEvent.UpdateType.SECONDS)) return;

        if (cleanupCount >= 20) {

            cleanupCount = 0;

            try {

                this.getConnection().close();
                statements.clear();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        cleanupCount++;

    }

}