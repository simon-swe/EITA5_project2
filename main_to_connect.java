import java.io.BufferedReader;
import java.io.PrintWriter;

public class main_to_connect {
    private PrintWriter out;
    private BufferedReader in;
    private Access access;
    private Records records;

    public main_to_connect (PrintWriter out, BufferedReader in, Access access) {
        this.out = out;
        this.in = in;
        this.access = access;
        records = new Records(access);

    }

    public void program() {
        System.out.println("What would you like to do?");
        System.out.println("1. Get a record");
        System.out.println("2. Create a record");
        System.out.println("3. Delete a record");
        try {
            String c = in.readLine();
            int choice = Integer.parseInt(c);
            if(choice == 1){
                System.out.println("Enter the patient name: ");
                String patient = in.readLine();
                records.getRecord(patient);
            }
            else if (choice == 2){
                System.out.println("Enter the patient name: ");
                String patient = in.readLine();
                System.out.println("Enter the doctor name: ");
                String doctor = in.readLine();
                System.out.println("Enter the nurse name: ");
                String nurse = in.readLine();
                System.out.println("Enter the department: ");
                String department = in.readLine();
                records.createRecord(patient, doctor, nurse, department);
            }
            else if (choice == 3){
                System.out.println("Enter the patient name: ");
                String patient = in.readLine();
                records.deleteRecord(patient);
            }
            else{
                System.out.println("Invalid choice");
            }
        } catch (Exception e) {
            System.out.println("Exception while entering String");
        }
    }

}
