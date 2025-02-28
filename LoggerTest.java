package hospital;
public class LoggerTest {
    public static void main(String[] args) {
        Logger l = new Logger();
        l.log("Julius Lam", "Doctor", "Added new medicine to patient");
        l.log("Simon Swedenborg", "Government", "Read patient record");
        l.closeLog();
    }
   
}