/*package org.javawavers.studybuddy.courses;
import org.javawavers.studybuddy.State.UserSession;
import org.javawavers.studybuddy.calculations.*;
import org.javawavers.studybuddy.availability.Availability;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.Objects;

public class CreateDays {
    List<Day> = days;

    public createDays() {
        List<Day> days = new ArrayList<>();
        LocalDate today = LocalDate.now();
        // it finds the latest deadline : lastDeadline
        while (!today.isAfter(lastDeadline)) {
            org.javawavers.studybuddy.courses.Day newDay = createObject(getDayofTheWeek(today));
            days.add(newDay);
            today = today.plusDays(1);
        }
    }

    public Day createObject(int dayWeek) {
        var s = new SimulateAnnealing();
        var av = new Availability();

        int[][] sched = s.schedule; //it takes the schedule array with the indexes
        List<Task> tasks = s.tasks;
        List<Task> dayTasks = getDayTasks(sched, tasks, dayWeek);

        int[] avail = av.avperday;
        int availability = getAvailability(avail, dayWeek);
        Day obj = new Day(int availability, List<Task> dayTasks);
        return obj;
    }
    public int getAvailability(int[] avail, int dayWeek) {
        return avail[dayWeek];
    }

    public List<Task> getDayTasks (int[][] schedule, List<Task> tasks, int dayWeek) {
        List<Task> dayTasks = new ArrayList<>();

        for (int i = 0; i < schedule[dayWeek].length; i++) {
            int taskIndex = schedule[dayWeek][i];
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                dayTasks.add(tasks.get(taskIndex));
            }
        }
        return dayTasks;
    }

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
*/


