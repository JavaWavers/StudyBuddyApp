package org.javawavers.studybuddy.calculations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SpacedRepetition {

    public class Lesson {
        private String title;
        private int step; // σε ποιο στάδιο βρίσκεται η επανάληψη
        private LocalDate nextReviewDate; // την ημερομηνία της επόμενης επανάληψης

        public Lesson(String title, LocalDate learnedDate) { // κατασκευαστής
            this.title = title;
            this.step = 1; // η επανάληψη ξεκινά με στάδιο 1
            this.nextReviewDate = learnedDate.plusDays(1);
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public LocalDate getNextReviewDate() {
            return nextReviewDate; // Return the stored nextReviewDate
        }

        public void advanceStep(boolean remember) { // Updates step and calculates the next review date
            if (!remember) {
                step = 1; // Reset to step 1 if user forgot the lesson
                nextReviewDate = LocalDate.now().plusDays(getInterval(step));
            } else {
                step++; // Move to the next step
                nextReviewDate = nextReviewDate.plusDays(getInterval(step));
            }
        }

        public int getStep() {
            return step;
        }

        private int getInterval(int step) { // Επιστρέφει το διάστημα για την επόμενη επανάληψη
            if (step == 1) return 1;
            if (step == 2) return 7;
            if (step == 3) return 16;
            if (step == 4) return 35;
            return getInterval(step - 1) * 2; // Exponential growth for higher steps
        }

        @Override
        public String toString() {
            return "μάθημα=" + title
                    + ", η επόμενη ημερομηνία για επανάληψη είναι=" + nextReviewDate;
        }
    }

    public List<Lesson> lessons; // λίστα με τα μαθήματα

    public SpacedRepetition() { // Ο κατασκευαστής αρχικοποιεί μια κενή λίστα για την αποθήκευση των μαθημάτων
        this.lessons = new ArrayList<>();
    }

    public void addLesson(String title, LocalDate date) { // Προσθέτει ένα μάθημα στην λίστα
        lessons.add(new Lesson(title, date));
    }

    public List<Lesson> getDueLessons(LocalDate date) { // Επιστρέφει όλα μαθήματα που πρέπει να διαβάσει μια μέρα
        List<Lesson> dueLessons = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getNextReviewDate().isEqual(date)) { // If the next review date matches the given date
                dueLessons.add(lesson);
            }
        }
        return dueLessons;
    }
}