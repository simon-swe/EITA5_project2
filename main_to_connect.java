public class main_to_connect {

    public static void main(String[] args) {
        login login = new login();
        if (login.authenticate()) {
            System.out.println("Login successful!");

        } else {
            System.out.println("Wrong username or password");
        }
    }
}
