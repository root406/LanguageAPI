package de.radjaguar2005.languageAPI.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    private Connection connection;
    private String host, database, username, password;
    private int port;

    public MySQL(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public void connect() throws SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            System.out.println("MySQL Verbindung hergestellt!");
        }
    }

    public void disconnect() throws SQLException {
        if (isConnected()) {
            connection.close();
            System.out.println("MySQL Verbindung geschlossen.");
        }
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConnection() throws SQLException {
        connect();
        return connection;
    }
}
