import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class login {
    public static boolean SSLConnections;

    /*
     * Handles the login of the user
     * The user will enter their username and password
     * The program will check if the username and password are correct
     * The program will check so that the CA certificate connected to that username
     * is correct
     * If the username and password and certificate are correct the user will be
     * logged in
     */
    private String userName, password, storedUser, storedPass, type, department;
    private Logger logger;

    public login() {
        logger = Logger.getInstance();
    }
    public boolean authenticate() {

        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Enter Username: ");
            userName = input.nextLine();
            System.out.println("Enter Password: ");
            password = input.nextLine();

        }
        //loginServer.authenticate(userName, password);
        try (Scanner reader = new Scanner(new File("users.txt"))) {
            reader.useDelimiter(",");
            while (reader.hasNextLine()) {
                if (reader.hasNext()) {
                    storedUser = reader.next();
                    if (reader.hasNext()) {
                        storedPass = reader.next();
                        if (reader.hasNext()) {
                            type = reader.next();
                            if (reader.hasNext()) {
                                department = reader.next();
                                if (storedUser.equals(userName) && storedPass.equals(password)) {
                                    logger.log(storedUser, type, "SUCCESSFULLY LOGGED IN");
                                    logger.closeLog();
                                    return true;
                                }
                            }
                        }
                    }
                }

            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
        return false;
    }
    

    public String returnUser() {
        return userName;
    }

    public String returnType() {
        return type;
    }

    public String returnDepartment() {
        return department;
    }
}

// after check send username password, type, and department to user.java
// to grant permissions
// ask user.java if username and password exist in user.txt, then check CA
// certificate
// then ask type and department and grant access based on these criteria