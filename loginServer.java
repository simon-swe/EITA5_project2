import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class loginServer {
    private String userName;
    public boolean login(PrintWriter out, BufferedReader in) {
        for (int i = 0; i < 3; i++) { // Allow up to 3 attempts
            try {
                out.println("Username");
                out.flush();
                this.userName = in.readLine();  // Read username
                out.println("Password:");
                out.flush();
                String password = in.readLine();  // Read password
                if (authenticate(userName, password)) {
                    out.println("logged in");  // Send success message
                    out.flush();
                    return true;  // Exit loop on successful login
                } else {
                    out.println("Incorrect login"); // Send failure message
                }
            } catch (IOException e) {
                System.err.println("An error occurred while reading input.");
                e.printStackTrace();
            }
        }
        updateLoginTime(userName);
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
                        long lastFailedLogin = Long.parseLong(credentials[4].trim());
                        if((System.currentTimeMillis() - lastFailedLogin) > 100000){
                            return true;
                        }
                        return false; // Authentication success
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
        return false; // Authentication failed
    }
    public static void updateLoginTime(String userName) {
        File file = new File("users.txt");
        List<String> lines = new ArrayList<>();

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] credentials = line.split(",");

                if (credentials.length >= 5) { // Ensure there's a loginTimer field
                    String storedUser = credentials[0].trim();

                    if (storedUser.equals(userName)) {
                        credentials[4] = String.valueOf(System.currentTimeMillis()); // Update loginTimer
                    }
                }

                lines.add(String.join(",", credentials)); // Store updated line
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            return;
        }

        // Write back the updated content
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (String updatedLine : lines) {
                writer.println(updatedLine);
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
