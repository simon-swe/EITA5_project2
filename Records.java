import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
/*
Creates records for patients, associates nurses and doctors with patients and some medical data for the specific patient.
 */
public class Records {

    private File record;
    private PrintWriter writer;
    private Scanner reader;
    private user user;
    private String department;
    private int access;

    public Records(user user){
        this.user = user;
        this.department = user.getDepartment();
        this.access = user.getAccess();  
    }

    //Gets the record for a patient
    public File getRecord(String patient){
        if ((access == 3) && (patient != user.getUsername()) || ((access == 2 || access == 1) && (department != user.getDepartment()))){
            System.out.println("You do not have permission to access this record");
            return null;
        }
        File record = new File(patient + ".txt");
        this.record = record;
        if(record.exists()){
            return record;
        }
        else{
            System.out.println("Record does not exist");
            return null;
        }
    }
    //Creates a record for a patient and associates a doctor and nurse with the record
    public void createRecord(String patient, String doctor, String nurse, String department){
        this.department = department;
        if(access == 1){
            try{
                record.createNewFile();
                this.writer = new PrintWriter(new FileWriter(record));
                writer.println("Patient: " + patient + "Doctor: " + doctor + "Nurse: " + nurse + "Department: " + department);
                addMedicalData();
            }
            catch(IOException e){
                System.out.println("Error creating record");
            }  
        }
        else{
            System.out.println("You do not have permission to create a record");
        }
        
    }
    public String getDepartment(){
        return this.department;
    }
    public void addMedicalData(){
        //Adds medical data to a patient, writes to the file
        writer.println("Medical Data: ");
        reader = new Scanner(System.in);
        System.out.println("Enter new medical data: ");
        String data = reader.nextLine();
        writer.println(data);
    }

    //Deletes a record for a patient
    public void deleteRecord(String patient){
        File record = new File(patient + ".txt");

        if(access == 0){
            if(record.exists()){
                record.delete();
            }
            else{
                System.out.println("Record does not exist");
            }
        }
        else{
            System.out.println("You do not have permission to delete the record");
        }
    }

    public void readRecord(){
        if(record.exists()){
            try{
                reader = new Scanner(record);
                while(reader.hasNextLine()){
                    System.out.println(reader.nextLine());
                }
            }
            catch(IOException e){
                System.out.println("Error reading record");
            }
        }
        else{
            System.out.println("Record does not exist");
        }
    }

    public void writeRecord(){
        if(access == 1 || access == 2){
            try{
                writer = new PrintWriter(new FileWriter(record, true));
                addMedicalData();
            }
            catch(IOException e){
                System.out.println("Error writing to record");
            }
        }
        else{
            System.out.println("You do not have permission to write to the record");
        }
    }
}
