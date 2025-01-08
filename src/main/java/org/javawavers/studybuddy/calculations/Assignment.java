package org.javawavers.studybuddy.calculations;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Assignment {
    private String title;
    private long remaingdays;
    private LocalDate deadline;
    private int estimateHours;
    private String description;
    private LocalDate completeddDate;

    /*
     * //θεωρω πως εχουμε παρει απο την βαση δεδομενων ειτε απο την μνημη της
     * εφαρμογης : μια λιστα με τις εργασιες που εχει ο χρηστης , μια λιστα με τα
     * deadllines της καθε εργασιας και την εκφωνηση της ασκησης
     *
     * ListAssignment listassignment = new ListAssignment();
     * //περνουμε απο την κλαση listAssignment τις λιστες για τις προθεσμιες και για
     * τα μαθηατα και τις εργασιες . στην πραγματικοτητα αυτη η κλαση θα ειναι η
     * σελιδα που θα εισαγει ο χρηστης τα μαθηματα
     * ArrayList<ArrayList<String>> assigment = listassignment.getAssigments();
     * ArrayList<ArrayList<LocalDate>> deadline = listassignment.getDeadline();
     * //λιστα για της εκφωνησης
     * ArrayList<ArrayList<String>> description = listassignment.getDescription();
     * ArrayList<ArrayList<Integer>> remaingdays = new ArrayList<>(); //δημιουργια
     * νεας λιστας η οποια θα εχει τις μερες που απομενουν μεσα
     * //παιρνουμε την σημερινη ημερα
     */

    // κατασκευαστης χωρις παραμετρους
    public Assignment() {
        this.completeddDate = null;

    }

    // κατασκευαστης με παραμετρους
    public Assignment(String title, long remaingdays, LocalDate deadline, int estimateHours, String description,
                      LocalDate completeDate) {
        this.title = title;
        this.remaingdays = getRemainingDays(today, deadline);
        this.deadline = deadline;
        this.estimateHours = calculateEstHours();
        this.description = description;
        this.completeddDate = completeDate;
    }
    // κατασκευαστής μόνο για την ημερομηνία του deadline και τις ώρες που
    // απαιτούνται για την υλοποιηση
    public Assignment(String title, LocalDate deadline, int estimateHours) {
        this.title = title;
        this.deadline = deadline;
        this.estimateHours = estimateHours;
    }
    LocalDate today = LocalDate.now();

    public long getRemainingDays(LocalDate today, LocalDate deadline) { // μεθοδος υπολογισμου ποσες μερες μενουν ακομα
        // για την ληξη της προθεσμιας

        long daysremaining = ChronoUnit.DAYS.between(today, deadline);// με αυτην την εντολη η βιβλιοθηκη chronounit
        // επιστρεφει τις μερες που εχουν διαφορα δυο
        // ημερομηνιες
        return daysremaining;

    }

    // μεθοδος υπολογισμου αν για καποια εργασια σε συγκεκριμενο μαθημα ειναι κοντα
    // στην προθεσμαι (δηλαδη λιγοτερο απο 10 μερες)
    public boolean isDuesoon() {
        if (getRemainingDays(today, deadline) > 10) {
            return false;
        } else {
            return true;
        }
    }

    // δημιουργια λιστων με λεξεις κλειδια για τον υπολογισμο της δυσκολιας της καθε
    // εργασιας αναλογα με την εκφωνηση και το επιπεδο δυσκολιας
    public static final ArrayList<String> keywords1 = new ArrayList<>() {
        {
            add("x");
        }
    };

    public static final ArrayList<String> keywords2 = new ArrayList<>() {
        {
            add("x");
        }
    };

    public static final ArrayList<String> keywords3 = new ArrayList<>() {
        {
            add("x");
        }
    };

    // μετραει ποσες λεξεις κλειδια εχει η εκφωνηση και με ποια δυσκολια επιστρεφει
    public double difficultyestmator(String description) {
        // μετρητες για τις λεξεις κλειδια ανα δυσκολια (ευκολο,μετριο,δυσκολο)
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        // βαζουμε ολες τις λεξεις της εκφωμησης σε πινακα μεμονομενες ωστε να μπορουμε
        // να τις ελενξουμε
        String[] words = description.toLowerCase().split("\\s+");
        // μετραμε για καθε λιστα ποσες λεξεις περιεχει ανα κατηγορια
        for (String word : words) {
            if (keywords1.contains(word)) {
                count1++;
            }
        }
        for (String word : words) {
            if (keywords2.contains(word)) {
                count2++;
            }
        }
        for (String word : words) {
            if (keywords3.contains(word)) {
                count3++;
            }
        }
        // Μια if η οποια ελενχει αν η εκφωνση περιεχει ενα αριθμο και αμεσως μετα την
        // λεξη : λεξεις ετσι ωστε αν συμβαινει αυτο τοτε να αποθηκευεται ο αριθμος τον
        // λεξεων που χρειαζετι να γραφτουν
        int i = 0;
        for (String word : words) {

            if (words[i + 1].equals("λέξεις")) {
                try {
                    double number = Double.parseDouble(word);
                    double Text = Double.parseDouble(word);
                } catch (NumberFormatException e) {
                }
            } else {
                i++;
            }
        }
        // μετραμε το score της συγκεκριμενης εκφωνησης
        double difficulty = count1 * 0.2 + count2 * 0.3 * count3 * 0.5;
        // μετραμε την συνολικη μεγιστη βαθμολογια δυσκολιας που μπορει να παρει καποιος
        final double maxdiff = (keywords1.size() * 0.2) + (keywords2.size() * 0.3) + (keywords3.size() * 0.5);
        // βγαζουμε το score για κλιμακα απο 1 - 10
        double score = 1 + (difficulty / maxdiff) * 9;
        // μηχανισμος ασσφαλειας σε λαθος εισαγωγη δεδομενων να μην μπορει το αποτελεσμα
        // να ειναι πανω απο 10. η κατω απο 1 , 0.
        double finascore = Math.min(10, Math.max(1, score));
        // επισστρεφουμε την συνολικη βαθμολογια
        return finascore;
    }

    private int calculateEstHours() {
        double difficulty = difficultyestmator(description);
        // θελουμε μια κλιμακα που να επιστρεφει ωρες αναλογα με την εργασια
        return 2;
    }

    // gettersss
    public String getTitle() {
        return title;
    }

    public LocalDate getCompletedDate() {
        return completeddDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public int getEstimateHours() {
        return estimateHours;
    }

    public String getDescription() {
        return description;
    }

    // setterrss
    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompletedDate() {
        this.completeddDate = completeddDate;
    }

    public void setDeadline() {
        this.deadline = deadline;
    }

    public void setEstimateHours() {
        this.estimateHours = estimateHours;
    }

    public void setDescription() {
        this.description = description;
    }
}
