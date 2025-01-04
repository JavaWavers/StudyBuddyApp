package org.javawavers.studybuddy.courses;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String username;
    private String email;
    private String password;

    int[] avPerDay; // availability for each day of the week
    List<Subject> subjects;
    List<Assignment> assignments;
    List<Exam> exams;

    List<Task> tasks;
    List<Day> days;

    // for the login
    public User(String name, String username, String email, String password, int[] avPerDay, List<Subject> subjects, List<Assignment> assignments, List<Exam> exams) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avPerDay = avPerDay;
        this.subjects = subjects;
        this.assignments = assignments;
        this.exams = exams;
    }
    // constructor for the registration
    public User(String name, String username, String email, String password, int[] avPerDay) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avPerDay = avPerDay;
        this.subjects = new List<Subject>;
        this.assignments = new List<Assignment>;
        this.exams = new List<Exam>;
    }
    // getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int[] getAvPerDay() {
        return avPerDay;
    }
    public void setAvPerDay(int[] avPerDay) {
        this.avPerDay = avPerDay;
    }
    public List<Subject> getSubjects() {
        return subjects;
    }
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
    public List<Assignment> getAssignments() {
        return assignments;
    }
    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }
    public List<Exam> getExams() {
        return exams;
    }
    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    //add, remove and update objects from the lists
    public void addSubject(Subject subj) {
        subjects.add(subj);
    }
    public void removeSubject(Subject subj) {
        subjects.remove(subj);
    }
    public void updateSubject(int index,Subject subj) {
        subjects.set(index, subj);
    }

    public void addAssignment(Assignment assign) {
        assignments.add(assign);
    }
    public void removeAssignment(Assignment assign) {
        assignments.remove(assign);
    }
    public void updateAssignment(int index, Assignment assign) {
        assignments.set(index, assign);
    }

    public void addExam(Exam exam) {
        exams.add(exam);
    }
    public void removeExam(Exam exam) {
        exams.remove(exam);
    }
    public void updateExam(int index, Exam exam) {
        exams.set(index, exam);
    }
    public void updateAvPerDay(int index, int av) {
        avPerDay[index] = av;
    }

}
