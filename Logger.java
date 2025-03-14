import java.time.LocalDateTime;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
/*
 * Keeps a audit log on everything that happens with the records in the server and who accessed them
 * The log will be saved in a file
 * The log will contain the username of the user that accessed the file, a timestamp and what action was taken
 */
    private static Logger instance;
    private FileWriter file;
    private BufferedWriter buffer;
    private Logger() {
        try {
            file = new FileWriter("log.txt",true);
        } catch (IOException e) {
            System.out.println("Could not find file");
            e.printStackTrace();
        }
        buffer = new BufferedWriter(file);
    }

    //To prevent multiple loggers
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public String constructMessage(String username,String role ,String change) {
        LocalDateTime time = LocalDateTime.now();
        StringBuilder sb = new StringBuilder(); 
        sb.append(time)
        .append(",")
        .append(username)
        .append(",")
        .append(role)
        .append(",")
        .append(change);
        
        return sb.toString();
    }

    public void log(String username,String role, String change) {
        String message = constructMessage(username, role, change);
        try {
            buffer.write(message);
            buffer.newLine();
            buffer.flush();
        } catch (IOException e) {
            System.out.println("Failed to write to file");
            e.printStackTrace();
        }

    }   

    public void closeLog() {
        try {
            buffer.close();
        } catch (IOException e) {
            System.out.println("Failed to close buffer");
            e.printStackTrace();
        }
    }
}
