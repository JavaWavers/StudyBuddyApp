
package org.javawavers.studybuddy.calculations;

import java.util.ArrayList;
import java.util.List;

public class Day {
    List<ScheduledTask> todayTasks;

    //constructor
    public Day (){
        todayTasks=new ArrayList<>();
    }

    //returns the whole list
    private List<ScheduledTask> getTodayTasks(){
        return todayTasks;
    }
    //returns the specified task from the list
    private ScheduledTask getTodayScheduledTask(int index){
        return todayTasks.get(index);
    }
}
