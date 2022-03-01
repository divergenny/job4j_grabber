package ru.job4j;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class MainTest {

    @Test
    public void sum() {
        Main main = new Main();
        assertThat(main.sum(1,1), is(2));
    }
}