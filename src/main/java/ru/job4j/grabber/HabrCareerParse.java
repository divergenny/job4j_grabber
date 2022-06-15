package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HabrCareerParse {
    private static final String SOURCE_LINK = "https://career.habr.com";

    private static final String PAGE_LINK =
            String.format("%s/vacancies/java_developer", SOURCE_LINK);

    private static final String SURF_LINK =
            String.format("%s?page=", PAGE_LINK);

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
        StringBuilder builder = new StringBuilder();
        Connection connection;
        try {
            connection = Jsoup.connect(link);
            Document document = connection.get();
            Elements rows = document.select(".job_show_description__vacancy_description");
            rows.forEach(row -> {
                Element descriptionElements = row.select(".style-ugc").first();
                for (Element element : descriptionElements.children()) {
                    builder.append(element.text());
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }
}
