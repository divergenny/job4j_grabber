package ru.job4j.quartz;

import java.sql.*;

public class ConnectionRabbit {
    private Connection connection;
    private Property cfg;

    public ConnectionRabbit(Property property) throws SQLException, ClassNotFoundException {
        this.cfg = property;
        initConnection();
    }

    private void initConnection() throws SQLException, ClassNotFoundException {
        Class.forName(cfg.getProperty("jdbc.driver"));
        String url = cfg.getProperty("jdbc.url");
        String login = cfg.getProperty("jdbc.username");
        String password = cfg.getProperty("jdbc.password");
        connection = DriverManager.getConnection(url, login, password);
    }

    public java.sql.Connection getConnection() {
        return connection;
    }

}
