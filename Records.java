import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
/*
Creates records for patients, associates nurses and doctors with patients and some medical data for the specific patient.
 */
public class Records {
    private String patient;
    private String doctor;
    private String nurse;
    private File record;
    private PrintWriter writer;
    private Scanner reader;

    public File getRecords(File record){
        this.record = record;
        //Gets the record for a patient
        if(record.exists()){
            return record;
        }
        else{
            return null;
        }
    }
    //Creates a record for a patient and associates a doctor and nurse with the record
    public void createRecord(String patient, String doctor, String nurse){

        File record = new File(patient + ".txt");
        this.record = record;
        this.patient = patient;
        this.doctor = doctor;
        this.nurse = nurse;
        try{
            this.writer = new PrintWriter(new FileWriter(record));
        }
        catch(IOException e){
            System.out.println("Error creating record");
        }
        writer.println("Patient: " + patient + "Doctor: " + doctor + "Nurse: " + nurse);
    }
    public void addMedicalData(){
        //Adds medical data to a patient, writes to the file
        writer.println("Medical Data: ");
        reader = new Scanner(System.in);
        System.out.println("Enter medical data: ");
        String data = reader.nextLine();
        writer.println(data);
    }
    public void deleteRecord(){
        //Deletes a record for a patient
        if(record.exists()){
            record.delete();
        }
        else{
            System.out.println("Record does not exist");
        }
    }
}
