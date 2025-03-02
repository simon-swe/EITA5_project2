import java.util.Scanner;

public class main_to_connect {

    public static void main(String[] args) {
        login login = new login();
        if (login.authenticate()) {
            System.out.println("Login successful!");

        } else {
            System.out.println("Wrong username or password");
        }
        user user = new user(login.returnUser(), login.returnType(), login.returnDepartment());
        Records records = new Records(user);
        Scanner input = new Scanner(System.in);
        System.out.println("What would you like to do?");
        System.out.println("1. Get a record");
        System.out.println("2. Create a record");
        System.out.println("3. Delete a record");
        int choice = input.nextInt();
        if(choice == 1){
            System.out.println("Enter the patient name: ");
            String patient = input.next();
            records.getRecord(patient);
        }
        else if (choice == 2){
            System.out.println("Enter the patient name: ");
            String patient = input.next();
            System.out.println("Enter the doctor name: ");
            String doctor = input.next();
            System.out.println("Enter the nurse name: ");
            String nurse = input.next();
            System.out.println("Enter the department: ");
            String department = input.next();
            records.createRecord(patient, doctor, nurse, department);
        }
        else if (choice == 3){
            System.out.println("Enter the patient name: ");
            String patient = input.next();
            records.deleteRecord(patient);
        }
        else{
            System.out.println("Invalid choice");
        }
    }
}
