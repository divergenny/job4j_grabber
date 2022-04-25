package ru.job4j.quartz;

import java.sql.*;

public class PrepareStatementRabbit {
    private Connection connection;
    private Property cfg;

    public PrepareStatementRabbit(Property property) throws SQLException, ClassNotFoundException {
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

    public void insertCreatedDate(Timestamp date) {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into rabbit(created_date) values(?)")) {
            statement.setTimestamp(1, date);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
