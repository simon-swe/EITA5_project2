import java.io.File;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class login {
    /*
     * Handles the login of the user
     * The user will enter their username and password
     * The program will check if the username and password are correct
     * The program will check so that the CA certificate connected to that username
     * is correct
     * If the username and password and certificate are correct the user will be
     * logged in
     */
    public static void main(String[] args) {
        String userName, password;

        Scanner input = new Scanner(System.in);

        System.out.println("Enter Username: ");
        userName = input.nextLine();
        System.out.println("Enter Password: ");
        password = input.nextLine();
        input.close();

        try {
            File loginObj = new File("users.txt");
            Scanner reader = new Scanner(loginObj);
            boolean login = false;
            reader.useDelimiter(",");
            while (reader.hasNext()) {
                String storedUser = reader.next();
                String storedPass = reader.next();
                String type = reader.next();
                String department = reader.next();
                if (storedUser.equals(userName) && storedPass.equals(password)) {
                    login = true;
                    break;
                }
                if (!reader.hasNext()) {
                    System.out.println("Wrong username or password");
                }

            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
// after check send username password, type, and department to user.java
// to grant permissions
// ask user.java if username and password exist in user.txt, then check CA
// certificate
// then ask type and department and grant access based on these criteria