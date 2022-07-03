package ru.job4j.grabber.configuration;

public class Links {

    public static final String HABR_SOURCE_LINK = "https://career.habr.com";

    public static final String HABR_PAGE_LINK =
            String.format("%s/vacancies/java_developer", HABR_SOURCE_LINK);

    public static final String HABR_SURF_LINK =
            String.format("%s?page=", HABR_PAGE_LINK);
}
