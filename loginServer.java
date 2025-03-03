import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
        return false;
    }

    public boolean authenticate(String userName, String password) {
        try (Scanner reader = new Scanner(new File("users.txt"))) {
            reader.useDelimiter(",");
            while (reader.hasNextLine()) {
                String line = reader.nextLine(); // Read entire line
                String[] credentials = line.split(","); // Split by commas

                if (credentials.length >= 2) { // Ensure at least username & password exist
                    String storedUser = credentials[0].trim();
                    String storedPass = credentials[1].trim();
                    if (storedUser.equals(userName)) {
                        long failedLoginAttempts = Long.parseLong(credentials[4].trim());
                        if (storedPass.equals(password) && failedLoginAttempts < 5) {
                            resetFailedAttempts(userName);
                            return true; // Authentication successful
                        }
                        incrementFailedAttempts(userName);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
        return false; // Authentication failed
    }
    public void incrementFailedAttempts(String userName) {
        File file = new File("users.txt");
        List<String> lines = new ArrayList<>();

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] credentials = line.split(",");

                if (credentials.length >= 5) { // Ensure there's a failed attempts field
                    String storedUser = credentials[0].trim();

                    if (storedUser.equals(userName)) {
                        int failedAttempts = Integer.parseInt(credentials[4].trim()); // Read current count
                        failedAttempts++; // Increment the failed attempts counter
                        credentials[4] = String.valueOf(failedAttempts); // Update the field
                    }
                }
                lines.add(String.join(",", credentials)); // Store updated line
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            return;
        } catch (NumberFormatException e) {
            System.err.println("Error parsing failed attempts counter: " + e.getMessage());
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

    public void resetFailedAttempts(String userName) {
        File file = new File("users.txt");
        List<String> lines = new ArrayList<>();
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] credentials = line.split(",");

                if (credentials.length >= 5) { // Ensure there's a failed attempts field
                    String storedUser = credentials[0].trim();

                    if (storedUser.equals(userName)) {
                        credentials[4] = "0"; // Reset the failed attempts counter to zero
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
