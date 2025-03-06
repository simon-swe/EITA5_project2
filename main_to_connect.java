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
        records = new Records(access, out, in);

    }

    public void printMenuPrompt() {
        out.println("What would you like to do? 1. Get a record 2. Create a record 3. Delete a record");
        out.flush();
    }

    public void program() {
        Boolean programIsRunning = true;
        while (programIsRunning) {

            printMenuPrompt();
            
            try {
                String c = in.readLine();
                out.println("received");
                out.flush();

                if (c == null || c.trim().isEmpty()) {
                    out.println("Please enter a valid choice");
                    out.flush();
                    continue;
                }

                try {
                    int choice = Integer.parseInt(c);
                    if(choice == 1){
                        out.println("Enter the patient name: ");
                        out.flush();
                        String patient = in.readLine();
                        out.println("received");
                        out.flush();
                        records.getRecord(patient);
                        
                    }
                    else if (choice == 2){
                        out.println("Enter the patient name: ");
                        out.flush();
                        String patient = in.readLine();
                        out.println("received");
                        out.flush();
                        System.out.println(patient);
                        out.println("Enter the doctor name: ");
                        out.flush();
                        String doctor = in.readLine();
                        out.println("received");
                        out.flush();
                        out.println("Enter the nurse name: ");
                        out.flush();
                        String nurse = in.readLine();
                        out.println("received");
                        out.flush();
                        out.println("Enter the department: ");
                        out.flush();
                        String department = in.readLine();
                        out.println("received");
                        out.flush();
                        records.createRecord(patient, doctor, nurse, department);
                        
                    }
                    else if (choice == 3){
                        out.println("Enter the patient name: ");
                        out.flush();
                        String patient = in.readLine();
                        records.deleteRecord(patient);
                    }
                    else if (choice == 4) {
                        out.println("Exiting program...");
                        out.flush();
                        programIsRunning = false;
                        break;
                    }
                    else{
                        out.println("Invalid choice. Please enter a number.");
                        out.flush();
                    }
                } catch (NumberFormatException e) {
                    out.println("Please enter a valid number");
                    out.flush();
                }
                
            } catch (Exception e) {
                out.println("An error occured.");
                out.flush();
                e.printStackTrace();
            }
        }
    }

}
