package ru.job4j.grabber.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class HabrCareerDateTimeParser implements DateTimeParser {
    @Override
    public LocalDateTime parse(String parse) {
        DateTimeFormatter format = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
                .parseLenient()
                .appendOffset("+HH:MM", "Z")
                .toFormatter();
        return LocalDateTime.parse(parse, format);
    }
}
