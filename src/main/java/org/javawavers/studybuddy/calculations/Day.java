package org.javawavers.studybuddy.calculations;

import org.javawavers.studybuddy.courses.*;
import java.util.ArrayList;
import java.util.List;

public class Day {
    public List<ScheduledTask> todayTasks;

    //constructor
    public Day (){
        todayTasks=new ArrayList<>();
    }

    //returns the whole list
    public List<ScheduledTask> getTodayTasks(){
        return todayTasks;
    }
    //returns the specified task from the list
    public ScheduledTask getTodayScheduledTask(int index){
        return todayTasks.get(index);
    }
}
