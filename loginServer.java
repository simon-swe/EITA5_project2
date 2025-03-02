import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class loginServer {

    public static boolean authenticate(String userName, String password) {
        try (Scanner reader = new Scanner(new File("users.txt"))) {
            reader.useDelimiter(",");
            while (reader.hasNextLine()) {
                if (reader.hasNext()) {
                    String storedUser = reader.next();
                    if (reader.hasNext()) {
                        String storedPass = reader.next();
                        if (reader.hasNext()) {
                            String type = reader.next();
                            if (reader.hasNext()) {
                                String department = reader.next();
                                if (storedUser.equals(userName) && storedPass.equals(password)) {
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
}
