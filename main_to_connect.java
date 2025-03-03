import java.io.BufferedReader;
import java.io.PrintWriter;

public class main_to_connect {
    private PrintWriter out;
    private BufferedReader in;
    private user user;

    public main_to_connect (PrintWriter out, BufferedReader in, user userA){ {
        try {
        this.out = out;
        this.in = in;
        this.user = userA;
        Records records = new Records(user);
        System.out.println("What would you like to do?");
        System.out.println("1. Get a record");
        System.out.println("2. Create a record");
        System.out.println("3. Delete a record");
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
        }
    }    
}
}
