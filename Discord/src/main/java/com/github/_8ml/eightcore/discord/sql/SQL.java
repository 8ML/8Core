/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
