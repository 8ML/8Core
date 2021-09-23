package com.github._8ml.eightcore.discord.sql;
/*
Created by @8ML (https://github.com/8ML) on September 13 2021
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQL {

    private final String host;
    private final String database;
    private final String user;
    private final String password;
    private final int port;

    private Connection connection;

    public SQL(String host, String database, String user, String password, int port) {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.port = port;
    }

    public boolean connect() {

        try {

            synchronized (this) {

                Class.forName("com.mysql.jdbc.Driver");

                if (this.connection != null && !connection.isClosed()) return false;

                this.connection = DriverManager.getConnection("jdbc:mysql://"
                        + this.host + ":" + this.port + "/" + this.database, this.user, this.password);

                return true;

            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;

    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        connect();
        return this.connection.prepareStatement(query);
    }

}
