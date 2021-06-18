package com.github._8ml.core.storage;
/*
Created by @8ML (https://github.com/8ML) on 4/23/2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.events.event.UpdateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class is used for managing the database connection.
 * This class should be instanced in the main class and
 * should not be created multiple instances of
 */
public class SQL implements Listener {

    private final String db, user, host, password;
    private final int port;

    private final List<PreparedStatement> statements = new ArrayList<>();

    private Connection connection;
    private int cleanupCount = 0;


    /**
     *
     * @param db Database name
     * @param user Username
     * @param host Database host
     * @param password The password of the user
     * @param port Port (Default 3306)
     */
    public SQL(String db, String user, String host, String password, int port) {
        this.db = db;
        this.user = user;
        this.host = host;
        this.password = password;
        this.port = port;
        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);
    }


    /**
     * This is called when the class is initialized.
     * Create tables here
     */
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
                    ", `permanent` BIT NOT NULL" +
                    ", `uid` VARCHAR(255) NOT NULL)");
            createTable("CREATE TABLE IF NOT EXISTS friends (`uuid` VARCHAR(255) PRIMARY KEY NOT NULL" +
                    ", `friendList` LONGTEXT NOT NULL" +
                    ", `requests` LONGTEXT NOT NULL)");
            createTable("CREATE TABLE IF NOT EXISTS preferences (`uuid` VARCHAR(255) NOT NULL" +
                    ", `key` VARCHAR(255) NOT NULL" +
                    ", `value` VARCHAR(255) NOT NULL)");
            createTable("CREATE TABLE IF NOT EXISTS achievements (`uuid` VARCHAR(255) PRIMARY KEY NOT NULL" +
                    ", `type` VARCHAR(255) NOT NULL" +
                    ", `description` LONGTEXT NOT NULL" +
                    ", `when` BIGINT NOT NULL)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to test the connection when the class is first initialized
     *
     * @return Will return true if the connection was successfully established
     */
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


    /**
     * Used to establish a connection to the database
     *
     * @return Will return true if the connection was successfully established
     */
    private boolean connect() {

        synchronized (this) {
            try {

                if (this.getConnection() != null) {
                    if (!this.getConnection().isClosed()) return false;
                }

                Class.forName("com.mysql.jdbc.Driver").newInstance();

                setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.db
                        + "?" + "user=" + this.user + "&password=" + this.password));

                statements.clear();
                return true;

            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        return false;

    }


    /**
     * Used to prepare a statement, it executes all the required tasks such as polling the cleanup
     * and opening a connection if one is not already open
     *
     * @param query The sql query
     * @return The prepared statement
     * @throws SQLException Will throw a SQLException if something goes wrong on the mysql side
     */
    public PreparedStatement preparedStatement(String query) throws SQLException {

        /*
        Resets the cleanup counter so the query wont get interrupted
         */
        pollCleanup();


        connect();
        PreparedStatement st = this.getConnection().prepareStatement(query);
        statements.add(st);
        return st;
    }


    /**
     * Used to create tables.
     * ONLY use this for creating tables
     *
     * @param query The create table sql query
     * @throws SQLException Will throw a SQLException if something goes wrong on the mysql side
     */
    private void createTable(String query) throws SQLException {
        try {
            connect();
            PreparedStatement stmt = this.getConnection().prepareStatement(query);
            stmt.execute();
            stmt.close();
        } finally {
            getConnection().close();
        }
    }


    /**
     * Used to close connections.
     *
     * will check if there are no other statements
     * to complete its query, if there are none
     * it will close the connection.
     * The last statement to finish will close the connection.
     *
     * If something does not go right and the connection remains
     * open after all queries are complete, the @OnUpdate will take
     * care of it
     *
     * @param call The statement that is done with its query.
     * @throws SQLException Will throw a SQLException if something goes wrong on the mysql side
     */
    public void closeConnection(PreparedStatement call) throws SQLException {

        this.statements.remove(call);
        if (this.statements.isEmpty()) {
            this.getConnection().close();
        }

        Core.instance.getLogger().info("DEBUG [SQL]: " + statements.size() + " " + Arrays.toString(statements.toArray()));

    }


    /**
     * @param connection The instance of a connection
     */
    private void setConnection(Connection connection) {
        this.connection = connection;
    }


    /**
     * Sets the cleanup count to 0 so the current open connection does not close before a statement has completed
     */
    private void pollCleanup() {
        this.cleanupCount = 0;
    }


    /**
     * @return Get the connection
     */
    private Connection getConnection() {
        return this.connection;
    }


    /**
     * This event will cleanup the connection if no new queries has been made
     */
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