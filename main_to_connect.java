public class main_to_connect {

    public static void main(String[] args) {
        Login login = new Login();
        user user = new user();
        Records records = new Records();
        if (login.authenticate()) {
            System.out.println("Login successful!");

        } else {
            System.out.println("Wrong username or password");
        }
    }
}
