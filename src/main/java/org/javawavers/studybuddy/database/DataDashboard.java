package org.javawavers.studybuddy.database;

import org.javawavers.studybuddy.calculations.Week;

import java.util.ArrayList;

public class DataDashboard {
    /*δέχεται τη συνολική λίστα με τις βδομάδες
    *μολισ ενα τασκ γινεται completed ανανεώνεται η dashboard
    * παίρνει όλα τα τασκ από όλες τις εβδομάδες και βγάζει το ποσοστό
    * completed task * 100 / συνολικά τασκ και τα εμφανίζει
     */
    private static ArrayList<Week> totalWeeks ;

    public static void setTotalWeeks(ArrayList<Week> tw){
        totalWeeks = new ArrayList<>(tw);
    }

}
