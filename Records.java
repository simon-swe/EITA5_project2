import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;

/*
Creates records for patients, associates nurses and doctors with patients and some medical data for the specific patient.
 */
public class Records {

    private File record;
    private PrintWriter writer;
    private Scanner reader;
    private Access user;
    private String department;
    private int access;
    private Logger logger;
    private PrintWriter out;
    private BufferedReader in;

    public Records(Access user, PrintWriter out, BufferedReader in) {
        this.user = user;
        this.department = user.getDepartment();
        this.access = user.getAccess();
        logger = Logger.getInstance();
        this.out = out;
        this.in = in;
    }

    // Gets the record for a patient
    public File getRecord(String patient) {
        if ((access == 3) && (!patient.equals(user.getUsername()))
                || ((access == 2 || access == 1) && (!department.equals(user.getDepartment())))) {
            out.println("You do not have permission to access this record");
            out.flush();
            logger.log(user.getUsername(), user.getType(), "ATTEMPT TO ACCESS UNAUTHORIZED RECORD");
            //logger.closeLog();
            return null;
        }
        File record = new File(patient + ".txt");
        this.record = record;
        if (record.exists()) {
            StringBuilder contentBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(record))) {
                String line;
                while ((line = br.readLine()) != null) {
                    out.println("$"); //Send this to not prompt response expectiation
                    out.flush();
                    out.println(line);
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String content = contentBuilder.toString();
            out.println(content);
            out.flush();
            logger.log(user.getUsername(), user.getType(), "ACCESS RECORD SUCCESSFUL");
            return record;
        } else {
            out.println("Record does not exist");
            out.flush();
            logger.log(user.getUsername(), user.getType(), "ATTEMPT TO ACCESS NONEXISTIENT RECORD");
            return null;
        }
    }

    // Creates a record for a patient and associates a doctor and nurse with the
    // record
    public void createRecord(String patient, String doctor, String nurse, String department) {

        if (access == 1 && department.equals(user.getDepartment())) {
            try {
                record = new File(patient + ".txt");
                record.createNewFile();
                this.writer = new PrintWriter(new FileWriter(record));
                writer.println(
                        "Patient: " + patient + "Doctor: " + doctor + "Nurse: " + nurse + "Department: " + department);
                addMedicalData();
                logger.log(user.getUsername(), user.getType(), "CREATED NEW RECORD");
            } catch (IOException e) {
                out.println("Error creating record");
                out.flush();
            }
        } else {
            out.println("You do not have permission to create a record, or you are not in the correct department");
            out.flush();
            logger.log(user.getUsername(), user.getType(), "UNAUTHORIZED ATTEMPT TO CREATE PASSWORD");
        }

    }

    public String getDepartment() {
        return this.department;
    }

    public void addMedicalData() {
        try {
            writer = new PrintWriter(new FileWriter(record, true));
            writer.println("Medical Data: ");
            reader = new Scanner(System.in);
            out.println("Enter new medical data: ");
            out.flush();
            writer.println(in.readLine());
            writer.flush();
            writer.close();
            out.println("Medical data received");
            out.flush();
            logger.log(user.getUsername(), user.getType(), "MEDICAL DATA ADDED TO RECORD");
            //logger.closeLog();
        } catch (IOException e) {
            out.println("Error writing to record");
            out.flush();
        }

    }

    // Deletes a record for a patient
    public void deleteRecord(String patient) {
        File record = new File(patient + ".txt");

        if (access == 0) {
            if (record.exists()) {
                record.delete();
                logger.log(user.getUsername(), user.getType(), "RECORD DELETED");
            } else {
                out.println("Record does not exist");
                out.flush();
                logger.log(user.getUsername(), user.getType(), "ATTEMPT TO RECORD NONEXISTENT RECORD");
            }
        } else {
            out.println("You do not have permission to delete the record");
            out.flush();
            logger.log(user.getUsername(), user.getType(), "UNAUTHORIZED ATTEMPT TO DELETE RECORD");
        }
    }

    public void readRecord() {
        if (record.exists()) {
            try {
                reader = new Scanner(record);
                while (reader.hasNextLine()) {
                    System.out.println(reader.nextLine());
                }
            } catch (IOException e) {
                out.println("Error reading record");
                out.flush();
            }
        } else {
            out.println("Record does not exist");
            out.flush();
        }
    }

    public void writeRecord() {
        if (access == 1 || access == 2) {
            try {
                writer = new PrintWriter(new FileWriter(record, true));
                addMedicalData();
            } catch (IOException e) {
                out.println("Error writing to record");
                out.flush();
            }
        } else {
            out.println("You do not have permission to write to the record");
            out.flush();
        }
    }
}
