package org.javawavers.studybuddy.courses;



import java.util.List;
import java.time.LocalDate;
import java.time.DayOfWeek;

public class Day {
    LocalDate date;
    int availability;
    List<Task> dayTasks;


    public Day (LocalDate date, int availability, List<Task> dayTasks) {
        this.date = date;
        this.availability = availability;
        this.dayTasks = dayTasks;
    }
}

