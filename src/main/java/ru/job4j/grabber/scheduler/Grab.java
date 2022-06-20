package ru.job4j.grabber.scheduler;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.dao.Store;

public interface Grab {
    void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException;
}
