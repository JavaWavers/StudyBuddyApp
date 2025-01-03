/*package org.javawavers.studybuddy.courses;

public class Week {
    Day[] days = new Day[7];

    public Week(Day[] days) {
        this.days = days;
    }

    Day[] getDays() {return days;};

    void setDays(Day[] days) {this.days = days;};

// οι συνολικες ωρες διαβασματος της καθε εβδομάδας
    int getTotalWeeklyHours() {
        int x = 0;
        for (int i = 0; i < days.length; i++) {
            for (int hours : days[i].subjectHours) {
                x += hours;
            }
            for (int hours : days[i].assignmentsHours) {
                x += hours;
            }
        }
        return x;
    }
    int[] getSubjectWeeklyHours() {
        int j = 0;
        int[] x = new int[0];
        for (int i = 0; i < days.length; i++) {
            for (String subjects : days[i].subjectNames) {
                for (int hours : days[i].subjectHours) {
                    x[j] += hours;
                }
                j ++;
            }
        }
        return x;
    }
}
*/