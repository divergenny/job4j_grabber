package ru.job4j.grabber.dao;

import ru.job4j.grabber.models.Post;

import java.util.List;

public interface Store {
    /**
     * Cохраняет объявление в базе.
     * @param post model
     */
    void save(Post post);

    /**
     * Позволяет извлечь объявления из базы
     * @return List of models elements
     */
    List<Post> getAll();

    /**
     * Позволяет извлечь объявление из базы по id.
     * @param id unique identity
     * @return model
     */
    Post findById(int id);
}
