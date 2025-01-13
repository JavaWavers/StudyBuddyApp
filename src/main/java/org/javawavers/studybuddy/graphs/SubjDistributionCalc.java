package org.javawavers.studybuddy.graphs;

import org.javawavers.studybuddy.calculations.Day;
import org.javawavers.studybuddy.calculations.Week;
import org.javawavers.studybuddy.courses.ScheduledTask;
import org.javawavers.studybuddy.courses.Subject;

import java.util.ArrayList;
import java.util.HashMap;

import static org.javawavers.studybuddy.courses.StaticUser.staticUser;

public class SubjDistributionCalc {
    public static HashMap<String, Double> subjectsDistribution(){
        HashMap<String, Double> subjectDistr = new HashMap<>();
        return subjectDistr;
    }

    private static void percentage(){
        ArrayList<Week> weekList = new ArrayList<>(staticUser.getTotalWeeks()) ;
        ArrayList<Subject> subjLisy = new ArrayList<>(staticUser.getSubjects());
        int totalTasks = 0;
        HashMap<String, Integer> tasksPerSubject = new HashMap<>();

        for (Week w : weekList){
            for (Day d : w.getDaysOfWeek()){
                for (ScheduledTask s : d.getTodayTasks()){
                    totalTasks++;


                }
            }
        }
    }
}
