package ru.job4j.quartz;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property {
    Properties properties;

    public Property(String propertyFileName) throws IOException {
        init(propertyFileName);
    }


    private void init(String propertyFileName) throws IOException {
        properties = new Properties();
        try (InputStream in = Property.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            properties.load(in);
        }
    }

    public String getProperty(String nameOfProperty) {
        return properties.getProperty(nameOfProperty);
    }
}
