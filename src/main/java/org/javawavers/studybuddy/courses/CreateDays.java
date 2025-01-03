package org.javawavers.studybuddy.courses;
import javafx.concurrent.Task;
import org.javawavers.studybuddy.State.UserSession;
import org.javawavers.studybuddy.calculations.*;
import org.javawavers.studybuddy.availability.Availability;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.Objects;
/* This class takes the result from the algorithm and creates days.
 It calculates for every day from today till the end of the array schedule the availability and the tasks of each day*/
public class CreateDays {

    public List<Day> createDays(int[][] schedule, List<Task> tasks, int[] avail, LinkedList<LocalDate> dates) {
        List<Day> days = new ArrayList<>();
        LocalDate today = LocalDate.now();
        int a = getAvailability(avail, dates, today);
        List<Task> t = new ArrayList<>();
        t = getDayTasks(0, schedule, tasks);
        days.add(new Day(today, a, t));
        for (int j = 1; j < schedule[0].length; j++) {
            today.plusDays(1);
            a = getAvailability(avail, dates, today);
            t = getDayTasks(j, schedule, tasks);
            days.add(new Day(today, a, t));
        }
        return days;
    }
    /* returns the tasks for the day, all the tasks(rows) of the specific column of array schedule  */
    public List<Task> getDayTasks(int column, int[][] schedule, List<Task> tasks) {
        List<Task> dayTasks = new ArrayList<>();
        for (int i = 0; i < schedule.length; i++) {
            dayTasks.add(tasks.get(schedule[i][column]));
        }
        return dayTasks;
    }
    /* input: array for the availability for each day of the week, the array for non available days,
     the current day of the week and the date
       output: the availability of the day */
    public int getAvailability(int[] avail, LinkedList<LocalDate> dates, LocalDate date) {
        int x = avail[getDayofTheWeek(date)];
        for (int i = 0; i < dates.size(); i++) {
            if (dates.get(i) == date) {
                x = 0;
                break;
            }
        }
        return x;
    }


    /* returns the index for the array availability based on the day of the week */
    public int getDayofTheWeek(LocalDate today) {
        DayOfWeek todaysDay = today.getDayOfWeek();
        int x;
        switch (todaysDay) {
            case MONDAY:
                return 1;
            break;
            case TUESDAY:
                return 2;
            break;
            case WEDNESDAY:
                return 3;
            break;
            case THURSDAY:
                return 4;
            break;
            case FRIDAY:
                return 5;
            break;
            case SATURDAY:
                return 6;
            break;
            case SUNDAY:
                return 7;
            break;
        }
    }
}



