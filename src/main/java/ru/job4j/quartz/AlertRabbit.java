package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class AlertRabbit {

    public static void main(String[] args) {
        Property properties = null;
        try {
            properties = new Property("rabbit.properties");

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ConnectionRabbit connectionRabbit = new ConnectionRabbit(properties);
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", connectionRabbit.getConnection());
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(
                            Integer.parseInt(properties.getProperty("rabbit.interval")))
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (SchedulerException | InterruptedException
                | ClassNotFoundException | SQLException se) {
            se.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("Rabbit runs here ...");
            Connection connectionRabbit = (Connection) context.getJobDetail()
                    .getJobDataMap()
                    .get("connection");
            try (PreparedStatement statement =
                         connectionRabbit.prepareStatement("insert into"
                                 + " rabbit(created_date) "
                                 + " values (?)")) {
                statement.setTimestamp(1,
                        new Timestamp(System.currentTimeMillis()));
                statement.execute();
            } catch (SQLException throwables) {
                System.out.println("ошибка в методе execute");
                throwables.printStackTrace();
            }
        }
    }
}
