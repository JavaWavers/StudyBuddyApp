package org.javawavers.studybuddy.calculations;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Assignment extends SubjectElement {
    private int estimateHours;
    private String description;
    private LocalDate completeddDate;

    // κατασκευαστης χωρις παραμετρους
    public Assignment() {
        super(null, null);

    }

    // κατασκευαστης με παραμετρους
    public Assignment(String title, long remaingdays, LocalDate deadline, int estimateHours, String description,
            LocalDate completeDate) {
        super(deadline, title);
        this.estimateHours = calculateEstHours();
        this.description = description;
    }

    // κατασκευαστής μόνο για την ημερομηνία του deadline και τις ώρες που
    // απαιτούνται για την υλοποιηση
    public Assignment(String title, LocalDate deadline, int estimateHours) {
        super(deadline, title);
        this.estimateHours = estimateHours;
    }

    // gettersss
    public String getTitle() {
        return super.getName();
    }

    public LocalDate getCompletedDate() {
        return completeddDate;
    }

    public LocalDate getDeadline() {
        return super.getDate();
    }

    public int getEstimateHours() {
        return estimateHours;
    }

    public String getDescription() {
        return description;
    }

    // setterrss
    public void setTitle(String title) {
        super.setName(title);
    }

    public void setCompletedDate(LocalDate completeddDate) {
        this.completeddDate = completeddDate;
    }

    public void setDeadline(LocalDate deadLine) {
        super.setDate(deadLine);
    }

    public void setEstimateHours(int estimateHours) {
        this.estimateHours = estimateHours;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void isDeadLineSoonMessage() {
        // Calculate the remaining days until the exam date
        long remainingDays = ChronoUnit.DAYS.between(LocalDate.now(), getDeadline());

        if (remainingDays <= 10 && remainingDays > 0) {
            System.out.println("Απομένουν μόνο " + remainingDays + " ημέρες μέχρι την εξέταση!");
        } else if (remainingDays == 0) {
            System.out.println("Η εξέταση είναι σήμερα! Καλή επιτυχία!");
        } else if (remainingDays < 0) {
            System.out.println("Η εξέταση έχει ήδη περάσει.");
        }
    }

    @Override
    // returns true if the deadline is in less than 5 days
    public boolean isDeadLineSoon() {
        long remainingDays = ChronoUnit.DAYS.between(LocalDate.now(), getDeadline());
        return remainingDays <= 5;
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

}
