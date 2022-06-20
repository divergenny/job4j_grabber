package ru.job4j.grabber;

import ru.job4j.grabber.models.Post;

import java.util.List;

public interface Parse {

    /**
     * Позволяет собрать короткое описание всех объявлений,
     * а так же загрузить детали по каждому объявлению.
     * @param link weblink
     * @return list of models
     */
    List<Post> list(String link);
}
