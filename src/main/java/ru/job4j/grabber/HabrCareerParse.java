package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.models.Post;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {

    public static final int NUMBER_OF_PAGES = 5;
    private static final String SOURCE_LINK = "https://career.habr.com";

    private static final String PAGE_LINK =
            String.format("%s/vacancies/java_developer", SOURCE_LINK);
    private static final String SURF_LINK =
            String.format("%s?page=", PAGE_LINK);

    private final DateTimeParser dateTimeParser;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    public static void main(String[] args) {
        DateTimeParser dateTimeParser = new HabrCareerDateTimeParser();
        Parse habrParser = new HabrCareerParse(dateTimeParser);
        List<Post> jobVacancies = habrParser.list(SURF_LINK);
        for (Post post : jobVacancies) {
            System.out.println(post);
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
        String vacancyLink = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
        return new Post(vacancyName,
                vacancyLink,
                retrieveDescription(vacancyLink),
                dateTimeParser.parse(date));
    }

}
