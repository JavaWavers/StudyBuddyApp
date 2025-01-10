package org.javawavers.studybuddy.calculations;

import org.javawavers.studybuddy.courses.ScheduledTask;

import java.util.ArrayList;
import java.util.List;

public class Week {
    public List <Day> daysOfWeek;
    //constructor
    public Week (){
        daysOfWeek = new ArrayList<>();
    }
    //returns the whole list
    private List<Day> getDaysOfWeek(){
        return daysOfWeek;
    }
    //returns the specified task from the list
    private Day getTheDay(int index){
        return daysOfWeek.get(index);
    }
}
