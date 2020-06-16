package com.gianfrancofois.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class ConnectionManager {
    private String url;
    private String username;
    private String password;

    public ConnectionManager(ConfigLoader loader){
        Map<String, Object> data = loader.loadConfiguration();
        this.url = (String) data.get("url");
        this.username = (String) data.get("username");
        this.password = (String) data.get("password");
    }

    public Connection openConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(false);
        return connection;
    }



}
