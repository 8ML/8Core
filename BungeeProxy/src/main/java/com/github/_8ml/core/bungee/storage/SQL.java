/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.bungee.storage;
/*
Created by @8ML (https://github.com/8ML) on 4/26/2021
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQL {

    private final String db, user, host, password;
    private final int port;

    private Connection connection;

    public SQL(String db, String user, String host, String password, int port) {
        this.db = db;
        this.user = user;
        this.host = host;
        this.password = password;
        this.port = port;
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

    public boolean connect() {

        try {

            if (this.getConnection() != null) {
                if (!this.getConnection().isClosed()) return false;
            }

            Class.forName("com.mysql.jdbc.Driver").newInstance();

            setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + "/"+ this.db
                    + "?" + "user=" + this.user + "&password=" + this.password));

            return true;

        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return false;

    }

    public PreparedStatement preparedStatement(String sql) throws SQLException {
        connect();
        return this.getConnection().prepareStatement(sql);
    }

    public void createTable(String sql) throws SQLException {
        try {
            connect();
            PreparedStatement stmt = this.getConnection().prepareStatement(sql);
            stmt.execute();
            stmt.close();
        } finally {
            getConnection().close();
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return this.connection;
    }

}
