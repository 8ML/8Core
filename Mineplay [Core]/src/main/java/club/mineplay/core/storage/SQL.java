package club.mineplay.core.storage;
/*
Created by Sander on 4/23/2021
*/

import javax.security.auth.login.LoginException;
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

    public void init() {
        try {
            createTable("CREATE TABLE IF NOT EXISTS users (id int auto_increment primary key not null" +
                    ", uuid varchar(30) not null, playerName varchar(30) not null, rank varchar(30) not null" +
                    ", xp int not null, coins int not null)");
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
