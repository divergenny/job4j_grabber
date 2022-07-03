package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.configuration.Links;
import ru.job4j.grabber.dao.PsqlStore;
import ru.job4j.grabber.models.Post;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class HabrCareerParse implements Parse {

    public static final int NUMBER_OF_PAGES = 5;

    private final DateTimeParser dateTimeParser;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    public static void main(String[] args) {
        Properties properties = null;
        try (InputStream in = HabrCareerParse
                .class
                .getClassLoader()
                .getResourceAsStream("rabbit.properties")) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (PsqlStore db = new PsqlStore(properties)) {
            DateTimeParser dateTimeParser = new HabrCareerDateTimeParser();
            Parse habrParser = new HabrCareerParse(dateTimeParser);
            List<Post> jobVacancies = habrParser.list(Links.HABR_SURF_LINK);
            System.out.println("========> Save Post <========");
            for (Post post : jobVacancies) {
                db.save(post);
            }
            System.out.println("========> Find by id <========");
            for (int i = 1; i <= 5; i++) {
                Post post = db.findById(i);
                System.out.println(post);
            }
            System.out.println("========> Get All <========");
            List<Post> posts = db.getAll();
            for (Post post : posts) {
                System.out.println(post);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

    private String retrieveDescription(String link) {
        Connection connection = Jsoup.connect(link);
        Document document;
        try {
            document = connection.get();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        Element descriptionElement = document.selectFirst(".style-ugc");
        return descriptionElement.text();
    }

    @Override
    public List<Post> list(String link) {
        List<Post> posts = new ArrayList<>();
        for (int pageNum = 1; pageNum <= NUMBER_OF_PAGES; pageNum++) {
            Connection connection = Jsoup.connect(link + pageNum);
            Document document;
            try {
                document = connection.get();
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
            Elements rows = document.select(".vacancy-card__inner");
            for (Element row : rows) {
                posts.add(parsingPost(row));
            }
        }
        return posts;
    }

    private Post parsingPost(Element row) {
        Element titleElement = row.select(".vacancy-card__title").first();
        Element linkElement = titleElement.child(0);
        String vacancyName = titleElement.text();
        Element dateElement = row.select(".vacancy-card__date").first();
        Element dateLinkElement = dateElement.child(0);
        String date = dateLinkElement.attr("datetime");
        String vacancyLink = String.format("%s%s",
                Links.HABR_SOURCE_LINK,
                linkElement.attr("href"));
        return new Post(vacancyName,
                vacancyLink,
                retrieveDescription(vacancyLink),
                dateTimeParser.parse(date));
    }

}
