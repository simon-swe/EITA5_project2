public class LoggerTest {
    public static void main(String[] args) {
        Logger l = Logger.getInstance();
        l.log("Julius Lam", "Doctor", "Added new medicine to patient");
        l.log("Simon Swedenborg", "Government", "Read patient record");

        Access user1  = new Access("Michael Jacksson", "Patient", "Heart");


    
    }
   
}