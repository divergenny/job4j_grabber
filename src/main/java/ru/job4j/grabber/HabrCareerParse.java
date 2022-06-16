package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.models.Post;
import ru.job4j.grabber.utils.DateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {
    private static final String SOURCE_LINK = "https://career.habr.com";

    private static final String PAGE_LINK =
            String.format("%s/vacancies/java_developer", SOURCE_LINK);

    private static final String SURF_LINK =
            String.format("%s?page=", PAGE_LINK);

    private final DateTimeParser dateTimeParser;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    public static void main(String[] args) throws IOException {
        for (int pageNum = 1; pageNum <= 5; pageNum++) {
            Connection connection = Jsoup.connect(SURF_LINK + pageNum);
            Document document = connection.get();
            Elements rows = document.select(".vacancy-card__inner");
            rows.forEach(row -> {
                Element titleElement = row.select(".vacancy-card__title").first();
                Element linkElement = titleElement.child(0);
                String vacancyName = titleElement.text();
                Element dateElement = row.select(".vacancy-card__date").first();
                Element dateLinkElement = dateElement.child(0);
                String date = dateLinkElement.attr("datetime");
                String newFormatDate = date.substring(0, 9);
                String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
                System.out.printf("%s [%s] %s%n", vacancyName, newFormatDate, link);
            });
        }

    }

    private String retrieveDescription(String link) {
        Connection connection = Jsoup.connect(link);
        Document document = null;
        try {
            document = connection.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Element descriptionElement = document.selectFirst(".style-ugc");
        return descriptionElement.text();
    }

    @Override
    public List<Post> list(String link) {
        List<Post> posts = new ArrayList<>();
        int idOfPost = 1;
        for (int pageNum = 1; pageNum <= 5; pageNum++) {
            Connection connection = Jsoup.connect(SURF_LINK + pageNum);
            Document document = null;
            try {
                document = connection.get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements rows = document.select(".vacancy-card__inner");
            for (Element row : rows) {
                Element titleElement = row.select(".vacancy-card__title").first();
                Element linkElement = titleElement.child(0);
                String vacancyName = titleElement.text();
                Element dateElement = row.select(".vacancy-card__date").first();
                Element dateLinkElement = dateElement.child(0);
                String date = dateLinkElement.attr("datetime");
                String vacancyLink = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
                Post post = new Post(idOfPost,
                        vacancyName,
                        vacancyLink,
                        retrieveDescription(vacancyLink),
                        dateTimeParser.parse(date));
                posts.add(post);
                idOfPost++;
            }
        }
        return posts;
    }
}
