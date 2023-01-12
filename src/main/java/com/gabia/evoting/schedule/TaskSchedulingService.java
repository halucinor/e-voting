package com.gabia.evoting.schedule;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class TaskSchedulingService {

    private final TaskScheduler taskScheduler;

    Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();

    public void scheduleATask(String jobId, Runnable tasklet, LocalDateTime endDateTime) {
        String cronExpression = toCron(endDateTime);
        System.out.println("Scheduling task with job id: " + jobId + " and cron expression: " + cronExpression);
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(tasklet, new CronTrigger(cronExpression, TimeZone.getTimeZone("Asia/Seoul")));
        jobsMap.put(jobId, scheduledTask);
    }

    public void removeScheduledTask(String jobId) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(jobId);
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.put(jobId, null);
        }
    }

    public static String toCron(LocalDateTime dateTime) {
        return String.format("%s %s %s %s %s %s",
                String.valueOf(dateTime.getSecond()),
                String.valueOf(dateTime.getMinute()),
                String.valueOf(dateTime.getHour()),
                String.valueOf(dateTime.getDayOfMonth()),
                String.valueOf(dateTime.getMonth().getValue()),
                String.valueOf(dateTime.getDayOfWeek().getValue()));
//                String.valueOf(dateTime.getYear()));
    }

}