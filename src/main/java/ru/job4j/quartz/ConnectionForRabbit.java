package ru.job4j.quartz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionForRabbit {
    private final Property cfg;

    public ConnectionForRabbit(Property property) {
        this.cfg = property;
    }

    public Connection initConnection() throws SQLException, ClassNotFoundException {
        Class.forName(cfg.getProperty("jdbc.driver"));
        String url = cfg.getProperty("jdbc.url");
        String login = cfg.getProperty("jdbc.username");
        String password = cfg.getProperty("jdbc.password");
        return DriverManager.getConnection(url, login, password);
    }

}
