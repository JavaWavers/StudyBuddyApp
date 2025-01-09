package org.javawavers.studybuddy.courses;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;

public class ScheduledTask {

    // Fields
    private String taskName;
    private int hoursAllocated;
    private LocalTime timeStarted;
    private LocalTime timeCompleted;
    private TaskStatus taskStatus;
    private LocalDate taskDate;
    private String subjectName;

    // Enum for Task Status
    public enum TaskStatus {
        COMPLETED,
        IN_PROGRESS,
        LATE,
        UPCOMING
    }

    // Constructor
    public ScheduledTask(String taskName, int hoursAllocated, TaskStatus taskStatus, LocalTime timeStarted,
                         LocalTime timeCompleted, LocalDate taskDate, Subject subject) {
        this.taskName = taskName;
        this.hoursAllocated = hoursAllocated;
        this.taskStatus = taskStatus;
        this.timeStarted = timeStarted;
        this.timeCompleted = timeCompleted;
        this.taskDate = taskDate;
        this.subjectName = subject.getCourseName();
    }

    // Constructor with default settings
    public ScheduledTask(String taskName, int hoursAllocated, LocalDate taskDate, Subject subject) {
        this(taskName,hoursAllocated,TaskStatus.UPCOMING,null,null,taskDate, subject);
    }


    // Getter and Setter for taskName
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    // Getter and Setter for hoursAllocated
    public int getHoursAllocated() {
        return hoursAllocated;
    }

    public void setHoursAllocated(int hoursAllocated) {
        this.hoursAllocated = hoursAllocated;
    }

    // Getter and Setter for taskStatus
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;

        if (taskStatus == TaskStatus.IN_PROGRESS) {
            this.timeStarted = LocalTime.now();
        } else if (taskStatus == TaskStatus.COMPLETED) {
            this.timeCompleted = LocalTime.now();
        }
    }

    //getters for TimeStarted and TimeCompleted
    public LocalTime getTimeStarted() {
        return timeStarted;
    }

    public LocalTime getTimeCompleted() {
        return timeCompleted;
    }

    //Getter and Setter for TaskDate
    public LocalDate getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(LocalDate taskDate) {
        this.taskDate = taskDate;
    }

    public String getSubjectName() {
        return subjectName;
    }

    // Method to check if the task is complete, return true when task is completed
    public boolean isComplete() {
        return taskStatus == TaskStatus.COMPLETED;
    }

    // Method to check if the task is in progress
    public boolean isInProgress() {
        return taskStatus == TaskStatus.IN_PROGRESS;
    }

    // Method to calculate time variance
    public String timeVariance() {
        if (timeStarted != null && timeCompleted != null) {
            Duration duration = Duration.between(timeStarted, timeCompleted);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            return hours + " ώρες και " + minutes + " λεπτά";
        } else {
            return "Η ώρα έναρξης ή ολοκλήρωσης δεν έχει οριστεί";
        }
    }

    // toString method
    @Override
    public String toString() {
        return "ScheduledTask{" +
                "Όνομα Εργασίας= " + taskName  +
                ", Ώρες Διατεθειμένες=" + hoursAllocated +
                ", Ώρα Έναρξης=" + (timeStarted != null ? timeStarted.toString() : "δεν έχει οριστεί") +
                ", Ώρα Ολοκλήρωσης=" + (timeCompleted != null ? timeCompleted.toString() : "δεν έχει οριστεί") +
                ", Κατάσταση Εργασίας=" + taskStatus +
                ", Αφορά το μάθημα=" + subjectName +
                '}';
    }
}

