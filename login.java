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

    }
}
