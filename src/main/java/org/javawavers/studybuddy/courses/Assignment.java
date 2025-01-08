package org.javawavers.studybuddy.courses;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;



    public class Assignment {
        private String title;
        private long remaingdays;
        private LocalDate deadline;
        private int estimateHours;//ποσες ωρες πιστευει ο χρηστης οτι θα του παρει
        private LocalDate completeddDate;



//κατασκευαστης χωρις παραμετρους
        public Assignment() {
            this.completeddDate = null;

        }

//κατασκευαστης με παραμετρους
        public Assignment(String title, LocalDate deadline, int estimateHours, LocalDate completeDate) {
            this.title = title;
            this.remaingdays = getRemainingDays(deadline);
            this.deadline = deadline;
            this.estimateHours = estimateHours;
            this.completeddDate = completeDate;
        }
        

// κατασκευαστής μόνο για την ημερομηνία του deadline και τις ώρες που
    // απαιτούνται για την υλοποιηση
    public Assignment(String title, LocalDate deadline, int estimateHours) {
        this.title = title;
        this.deadline = deadline;
        this.estimateHours = estimateHours;
    }
        
        public long getRemainingDays( LocalDate deadline) { //μεθοδος υπολογισμου ποσες μερες μενουν ακομα για την ληξη της προθεσμιας
            LocalDate today = LocalDate.now();
            long daysremaining = ChronoUnit.DAYS.between(today, deadline);//με αυτην την εντολη η βιβλιοθηκη chronounit επιστρεφει τις μερες που εχουν διαφορα δυο ημερομηνιες
            return daysremaining;
    
        }

            
                
        //μεθοδος υπολογισμου αν για καποια εργασια σε συγκεκριμενο μαθημα ειναι κοντα στην προθεσμαι (δηλαδη λιγοτερο απο 10 μερες) 
                public boolean isDuesoon() {
                    if (getRemainingDays(deadline) > 10) {
                        return false;
                    } else {
                        return true;
                    }
                }

@Override
public String toString() {
    return  "Assignment [title:" + title +
    ", Remaining days :" + getRemainingDays(deadline) +
    ", deadline :" + deadline +
    ", estimatehours :" + estimateHours +
    ", completed date :" + completeddDate + "]"; 


}
        
            

//gettersss
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


//setterrss
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

                
        
    }  
