package ru.job4j.grabber.dao;

import ru.job4j.grabber.models.Post;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;

    public PsqlStore(Properties cfg) throws SQLException {
        String url, login, password;
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            url = cfg.getProperty("jdbc.url");
            login = cfg.getProperty("jdbc.username");
            password = cfg.getProperty("jdbc.password");

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        cnn = DriverManager.getConnection(url, login, password);
    }

    @Override
    public void close() throws Exception {
        cnn.close();
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement =
                     cnn.prepareStatement(
                             "insert into post(name, text, link, created)"
                                     + " values (?, ?, ?, ?)"
                                     + " on conflict do nothing")) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getDescription());
            statement.setString(3, post.getLink());
            statement.setDate(4, Date.valueOf(post.getCreated().toLocalDate()));
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement statement =
                     cnn.prepareStatement(
                             "select * from post")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(getPostUsingResultSet(resultSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try (PreparedStatement statement =
                     cnn.prepareStatement(
                             "select * from post where id = ?")) {
            statement.setInt(1, id);
            statement.execute();
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    post = getPostUsingResultSet(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private Post getPostUsingResultSet(ResultSet resultSet) throws SQLException {
        Instant instant = Instant.ofEpochMilli(resultSet.getDate("created").getTime());
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        return new Post(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("link"),
                resultSet.getString("text"),
                ldt
        );
    }

}
