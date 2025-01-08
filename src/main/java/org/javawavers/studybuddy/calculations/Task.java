package org.javawavers.studybuddy.calculations;


public class Task {
    private String subjName;
    private int taskType; // 1: Studying , 2: revision, 3: assignment
    // stores the time required for studying for the task
    private double taskhours;

    // constructor with the Subject object
    public Task(Subject subj, int taskType) {
        subjName = subj.getCourseName();
        this.taskType = taskType;
        if (taskType == 1 || taskType == 3) {
            this.taskhours = 2.0;
        } else {
            this.taskhours = 1.0 / 3.0;
        }
    }

    // constructor with the subject as a String
    public Task(String subj, int taskType) {
        subjName = subj;
        this.taskType = taskType;
        if (taskType == 1 || taskType == 3) {
            this.taskhours = 2.0;
        } else {
            this.taskhours = 1.0 / 3.0;
        }
    }

    // get subject's name
    public String getSubject() {
        return subjName;
    }

    // get task's type
    public int getTaskType() {
        return taskType;
    }

    // get task's hours
    public double getTaskHours() {
        return taskhours;
    }

    /*
     * set task's hours
     * The above method is only required in task Type 2
     * when the tasks merge is done
     */
    public void setTaskHours(double hours) {
        this.taskhours = hours;
    }

    @Override
    public String toString() {
        String type;

        // Determine the task type
        switch (taskType) {
            case 1:
                type = "Διάβασμα"; // Studying
                break;
            case 2:
                type = "Επανάληψη"; // Revision
                break;
            case 3:
                type = "Εργασία"; // Assignment
                break;
            default:
                type = "Άγνωστο"; // Unknown
                break;
        }

        // Separate hours and minutes from task hours
        int hours = (int) taskhours; // Whole hours
        int min = (int) ((taskhours - hours) * 60); // Remaining minutes

        // Build the string representation based on hours and minutes
        if (hours > 0 && min > 0) {
            return subjName + " - " + type + " (" + hours + " ώρες και " + min + " λεπτά)";
        } else if (hours > 0) {
            return subjName + " - " + type + " (" + hours + " ώρες)";
        } else if (min > 0) {
            return subjName + " - " + type + " (" + min + " λεπτά)";
        } else {
            return subjName + " - " + type + " (0 λεπτά)"; // Fallback for zero duration
        }
    }

}
