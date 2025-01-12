package org.javawavers.studybuddy.graphs;

import java.util.ArrayList;
import java.util.List;
import javax.security.auth.Subject;

public class ProgressCalculator {
    //λιστες για τα tasks
    List<Subject> subjects = new ArrayList<>();
    List<String> examTasks = new ArrayList<>();
    List<String> assignmentsTasks = new ArrayList<>();

    //λιστα με τα συνολικα Tasks που εχει το καθε μαθημα (αυτο θα το παιρνουμε απο τον αλγοριθμο) με τα ονοματα των task --> ονομα task , subject
    List<List<String>> totalTasksPerSubject = new ArrayList<>();
    //λιστα που θα την παρουμε απο το scheduleTask για το status του καθε task --> status, ονομα task
    List<List<String>> statusperTask = new ArrayList<>();

    //μετρητης πληθους μαθηματων
    int countsubject = 0;
    //μπορει να μην χρειαζονται ολα αυτα
    double weeklyProgress;
    double dailyProgress;
    double[] totalDistribution;
    double[] weeklyDistribution;
    double[] dailyDistribution;
    //μεθοδος που καλειται για ενα μαθημα και υπολογιζει για αυτο το μαθημα την προοδο που εχει γινει
    public double calculateProgressofSubject(Subject subject) {
//προσθετει μαθηματα καθε φορα που καλειται αυτη η μεθοδος
        countsubject++;
//αρχικοποιηση μετρητων που θα βοηθησουν για να μετρησουμε τα task που εχει ξεκινησει,ολοκληρωση,αργησει να παραδωσει, δεν τα εχει αρχισει ακομα
        int notStartedYet = 0;
        int ongoing = 0;
        int late = 0;
        int completed = 0;
//αρχικοποιησει του totaltask για να μετραμε στην συνχεια ποσα task εχει το καθε μαθημα
        int totaltask = 0 ;
//παιρνουμε το ονομα του task και το μαθημα που ανηκει το συγκεκριμενο task και αν ανηκει στο μαθημα που εχει για ορισμα η μεθοδος τοτες προστιθεται ενα στα συνολικα task του μαθηματος αυτου(totaltask)
        for (List<String> taskinfo : totalTasksPerSubject) {
            String taskname = taskinfo.get(0);
            String tasksubject = taskinfo.get(1);

            if (tasksubject.equals(subject)) {
                totaltask++;
//παιρνουμε την κατασταση και το ονομα του task ψαχνουμε να βρουμε ποτε ειναι ιδιο το ονομα του task ωστε να τα ταιριαξουμε και στην συνεχεια αφου ειναι ιδιο μπαινει μεσα στην if και προστιθεται ενα αναλογα το status του task
                for (List<String> taskstatus : statusperTask) {
                    String taskstatusname = taskstatus.get(0);
                    String status = taskstatus.get(1);

                    if (taskname.equals(taskstatusname)) {

                        if (status.equals("Completed")) {
                            completed++;
                        } else if (status.equals("Late")) {
                            late++;
                        } else if (status.equals("Ongoing")) {
                            ongoing++;
                        } else {
                            notStartedYet++;
                        }
                        break;
                    }
                }
            }


        }
//αν αυτο το μαθημα για καποιον λογο δεν εχει task τοτε επιστρεφουμε 0 της εκατο ποσοστο
        if (totaltask == 0) {
            return 0;
        }
//υπολογιζουμε την προοδο ως τα task που εχουν ολοκληρωθει / τα συνολικα task του μαθηματος επι 100 για το ποσοστο
        double progress = ((double)completed / (double)totaltask) * 100;
        return progress;


    }
    //φτιαχνουμε λιστα για την προοδο ολων των μαθηματων ωστε να βγαλουμε την συνολικη προοδο
    List<Double> subjectprogress = new ArrayList<>();
//μεθοδος που μετραει την συνολικη προοδο του χρηστη

    public double calculateTotalProgress() {
        double sum = 0;
        int count = 0;
//γεμιζουμε την λιστα με της προοδους ολων των μαθηματων
        for (Subject subject : subjects) {
            double progress = calculateProgressofSubject(subject);
            subjectprogress.add(progress);
        }
//μετραμε την συνολικη προοδο για ολα τα μαθηματα + μετρητης για τα μαθηματα που εχουμε μετρηση
        for(double progress : subjectprogress) {
            sum = sum + progress;
            count++;
        }
//εαν δεν υπαρχουν μαθηματα επιστρεφει 0
        if (count == 0) {
            return 0;
        } else {
//επιστρεφουμε το αθροισμα των προοδων / το συνολο των μαθηματων * 100
            return (sum / count) * 100;
        }


    }
    //μεθοδος υπολογισμου προοδου ανα μαθημα
    public void calculateDistribution() {
//καλουμε την μεθοδο υπολογισμου της συνολικης προοδου
        double total = calculateTotalProgress();
//φτιαχνουμε μια λιστα για να βαλουμε μεσα την προοδο ανα μαθημα
        List<Double> subjectDistribution = new ArrayList<>();
//για το καθε μαθημα υπολογιζουμε την προοδο του δια την συνολικη προοδο επι 100 για να βρουμε το ποσοστο του συγκεκριμενου μαθηματος
        for (int i = 0; i < subjectprogress.size(); i++) {
            double distribution = (subjectprogress.get(i) / total) * 100 ;
            subjectDistribution.add(distribution);
        }
    }




}









