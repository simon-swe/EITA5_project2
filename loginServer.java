import java.io.*;
import java.util.Scanner;

public class loginServer {

    public boolean login(PrintWriter out, BufferedReader in) {
        for (int i = 0; i < 3; i++) { // Allow up to 3 attempts
            try {
                out.println("Username");
                out.flush();
                String userName = in.readLine();  // Read username
                out.println("Password:");
                out.flush();
                String password = in.readLine();  // Read password

                if (authenticate(userName, password)) {
                    out.println("logged in");  // Send success message
                    return true;  // Exit loop on successful login
                } else {
                    out.println("Incorrect login"); // Send failure message
                }
            } catch (IOException e) {
                System.err.println("An error occurred while reading input.");
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean authenticate(String userName, String password) {
        try (Scanner reader = new Scanner(new File("users.txt"))) {
            reader.useDelimiter(",");
            while (reader.hasNextLine()) {
                String line = reader.nextLine(); // Read entire line
                String[] credentials = line.split(","); // Split by commas

                if (credentials.length >= 2) { // Ensure at least username & password exist
                    String storedUser = credentials[0].trim();
                    String storedPass = credentials[1].trim();

                    if (storedUser.equals(userName) && storedPass.equals(password)) {
                        return true; // Authentication success
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
        return false; // Authentication failed
    }
}
