# StudyBuddy

## Οδηγίες Μεταγλώττισης του Προγράμματος
Για να μεταγλωττίσετε την εφαρμογή, βεβαιωθείτε ότι έχετε εγκατεστημένο το Maven και εκτελέστε την παρακάτω εντολή στο κύριο directory του project:

```bash
mvn clean install
```

Αυτό θα κατεβάσει όλες τις απαιτούμενες εξαρτήσεις και θα δημιουργήσει το εκτελέσιμο `.jar` αρχείο στο φάκελο `target/`.

### Εγκατάσταση Maven
1. Μεταβείτε στον ιστότοπο: [Maven Official Site](https://maven.apache.org/).
2. Κατεβάστε το αρχείο `apache-maven-3.9.9-bin.zip` από [αυτόν τον σύνδεσμο](https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.zip).
3. Αποσυμπιέστε το αρχείο.
4. Προσθέστε το φάκελο `bin` του Maven στη μεταβλητή περιβάλλοντος `Path`:
   - Windows: Ανοίξτε τις **Ρυθμίσεις Συστήματος** → **Advanced System Settings** → **Environment Variables**.
   - Προσθέστε το μονοπάτι, π.χ., `C:\Users\YourUser\apache-maven-3.9.9\bin`.
5. Επαληθεύστε την εγκατάσταση εκτελώντας την εντολή:

```bash
mvn -v
```

### Εγκατάσταση JavaFX
1. Μεταβείτε στον ιστότοπο: [Gluon JavaFX](https://gluonhq.com/products/javafx/).
2. Κατεβάστε την κατάλληλη έκδοση SDK για το λειτουργικό σας σύστημα.
3. Αποσυμπιέστε το αρχείο.
4. Προσθέστε το JavaFX SDK στις ρυθμίσεις του project (δείτε παρακάτω το `pom.xml`).



## Οδηγίες Εκτέλεσης του Προγράμματος
Για να εκτελέσετε την εφαρμογή, χρησιμοποιήστε τις παρακάτω εντολές:

1. Μεταγλωττίστε το πρόγραμμα:

```bash
mvn clean install
```

2. Εκτελέστε την εφαρμογή μέσω του Maven plugin για JavaFX:

```bash
mvn javafx:run
```

Αυτό θα εκκινήσει την εφαρμογή StudyBuddy και θα εμφανίσει την κύρια σελίδα.


## Οδηγίες Χρήσης του Προγράμματος

1. **Αρχική Σελίδα / Είσοδος**:
   - Συνδεθείτε με το λογαριασμό σας ή εγγραφείτε με τα στοιχεία σας.

2. **Εισαγωγή Μαθημάτων**:
   - Μετά τη σύνδεση, μεταφέρεστε απευθείας στην επιλογή **Courses**.
   - Συμπληρώστε πληροφορίες όπως όνομα μαθήματος, αριθμός σελίδων και ημερομηνία εξέτασης.
   - Πατήστε **OK** για αποθήκευση και μεταφερθείτε αυτόματα στα **Assignments**.

3. **Εισαγωγή Εργασιών**:
   - Συμπληρώστε λεπτομέρειες όπως τίτλος, χρόνος ολοκλήρωσης, ημερομηνία παράδοσης και δυσκολία.
   - Πατήστε **OK** και μεταφερθείτε αυτόματα στη σελίδα **Διαθεσιμότητας**.

4. **Διαθεσιμότητα**:
   - Ορίστε τη διαθεσιμότητά σας για κάθε μέρα της εβδομάδας.
   - Πατήστε **OK** και μεταφερθείτε στο ημερολόγιο.

5. **Οργάνωση στο Ημερολόγιο**:
   - Δείτε το προτεινόμενο πλάνο διαβάσματος που έχει ορίσει η εφαρμογή.
   - Πατήστε **Refresh** για νέο πλάνο, αν απαιτείται.

6. **Προβολή Στατιστικών**:
   - Στην ενότητα **Dashboard**, δείτε γραφήματα και στατιστικά όπως:
     - Κατανομή χρόνου ανά δραστηριότητα.
     - Παραγωγικότητα μέσα στην εβδομάδα.
     - Ποσοστά ολοκλήρωσης για εργασίες, μαθήματα και στόχους.


## Παρουσίαση της Δομής των Περιεχομένων του Αποθετηρίου

### Ριζικός Φάκελος
- `pom.xml`: Το αρχείο διαμόρφωσης του Maven για τη διαχείριση εξαρτήσεων και μεταγλώττιση του προγράμματος.
- `README.md`: Το παρόν αρχείο τεχνικής αναφοράς.
- `LICENSE`: Άδεια χρήσης του προγράμματος.

### Δομή του Φακέλου `src/`
- **`java/org/javawavers/studybuddy`**:
  - **`StudyBuddyApp.java`**: Η κύρια κλάση που εκκινεί την εφαρμογή.
  - **Υποφάκελοι**:
    - `calculations/`: Περιέχει τους υπολογισμούς για τα στατιστικά και τις λειτουργίες της εφαρμογής.
    - `courses/`: Κλάσεις για τη διαχείριση μαθημάτων.
    - `database/`: Κλάσεις για τη σύνδεση και τη διαχείριση δεδομένων στη βάση SQLite.
    - `graphs/`: Κλάσεις για την απεικόνιση στατιστικών σε γραφήματα.
    - `ui_ux/`: Περιέχει το UI και τις σκηνές της εφαρμογής.
    - `utility/`: Βοηθητικές κλάσεις.

- **`resources/`**:
  - Αρχεία ρυθμίσεων και γραφικά στοιχεία για την εφαρμογή.

## Επισκόπηση των Δομών Δεδομένων και των Αλγορίθμων που Χρησιμοποιεί η Εφαρμογή

Η εφαρμογή αξιοποιεί έναν βελτιστοποιημένο αλγόριθμο για τη δημιουργία προσωποποιημένων προγραμμάτων μελέτης, ο οποίος βασίζεται σε ισχυρές δομές δεδομένων και πολυεπίπεδη λογική. Παρακάτω περιγράφονται οι βασικοί άξονες λειτουργίας του αλγορίθμου και οι χρησιμοποιούμενες δομές δεδομένων.

### Περιγραφή του Αλγορίθμου

1. **Υπολογισμοί**:
   - Χρήση της κλάσης `CalculativeAlgorithm` για τον υπολογισμό:
     - Συνολικού χρόνου μελέτης ανά μάθημα.
     - Απαιτούμενων εργασιών (2ωρες ανά task).
   - Διαχείριση προθεσμιών μέσω της κλάσης `Dates`, με λειτουργίες όπως:
     - Υπολογισμός τελευταίας επιτρεπόμενης ημέρας (`lastIsDue`).
     - Έλεγχος έγκαιρης προγραμματισμού (`checkDate`).

2. **Κατανομή Εργασιών**:
   - Δημιουργία και τυχαία κατανομή των `Task` σε διαθέσιμες ημέρες και ώρες.
   - Ενσωμάτωση επαναληπτικών μελετών μέσω **Spaced Repetition** για ενίσχυση της μνήμης.

3. **Βαθμολόγηση και Επιλογή**:
   - Δημιουργία 50 εναλλακτικών προγραμμάτων με την κλάση `Heristic`.
   - Βαθμολόγηση μέσω της κλάσης `Scoring`, με ποινές για:
     - Επαναλαμβανόμενες εργασίες την ίδια ημέρα.
     - Συνεχόμενες ημέρες μελέτης του ίδιου μαθήματος.
   - Επιλογή του προγράμματος με την υψηλότερη βαθμολογία.

4. **Επικύρωση**:
   - Έλεγχος εγκυρότητας με την κλάση `Validate`, διασφαλίζοντας:
     - Τήρηση προθεσμιών (`deadlineValidity`).
     - Ευθυγράμμιση με τη διαθεσιμότητα (`availabilityValidity`).
     - Κατανομή όλων των υποχρεωτικών εργασιών (`assignmentsValidity`).

5. **Αποθήκευση**:
   - Δομή του προγράμματος σε εβδομαδιαία και ημερήσια βάση μέσω της κλάσης `CreateWeekDay`.
   - Αποθήκευση του προγράμματος στον χρήστη μέσω της κλάσης `StaticUser` και της μεθόδου `saveToDataB`.

### Χρησιμοποιούμενες Δομές Δεδομένων

- **Λίστες**:
  - Αποθήκευση και διαχείριση σειριακών δεδομένων, όπως οι εργασίες (`Task`).
  
- **Δισδιάστατοι Πίνακες**:
  - Ο πίνακας `schedule` (12xΝ) οργανώνει τις ώρες της ημέρας (γραμμές) και τις ημέρες (στήλες).

- **Στατικά Δεδομένα**:
  - Ο `staticUser` περιέχει βασικές πληροφορίες του χρήστη (π.χ. μαθήματα, διαθεσιμότητα), προσβάσιμες παγκοσμίως.

- **Πολυεπίπεδα Αντικείμενα**:
  - Οι κλάσεις `Week` και `Day` παρέχουν δομημένη οργάνωση εβδομάδων και ημερών.

### Συνοψίζοντας

Ο αλγόριθμος και οι δομές δεδομένων της εφαρμογής διασφαλίζουν την αποτελεσματική διαχείριση των εργασιών, την τήρηση προθεσμιών και τη δημιουργία εξατομικευμένων, βελτιστοποιημένων προγραμμάτων μελέτης για τον χρήστη.
