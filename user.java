import java.util.Scanner;

/*Here each type of user will have their own methods and attributes
* A user can be Government, Doctor, Nurse, Patient or Admin
* Only Admin can add new users
* Government can read and delete files
* Doctor can read,write and create files
* Nurse can read and write files
* Patient can only read files
* Each user will have their own username and password
*/
public class user {
    private String username;
    private String password;
    private String type;
    private int access;
    public user(String username, String password, String type, int access){
        this.username = username;
        this.password = password;
        this.type = type;
        this.access = access;
    }
    public String getUsername(){
        return this.username;
    } 
    public String getPassword(){
        return this.password;
    }
    public String getType(){
        return this.type;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setType(String type){
        this.type = type;
    }
    public user NewUser(String username, String password, String type, int access){
        if(this.type.equals("Admin")){
            user newUser = new user(username, password, type, access);
            return newUser;
        }
        else{
            System.out.println("New user cannot be created");
            return null;
        }
        
    }
    /*Doctor has access to read, write and create files
     *Nurse has access to read and write files
     *Patient has access to read files
     *Government has access to read and delete files
     *Admin has full access to the system and can create new users*/
    public void userType(){
        if(this.type.equals("Admin")){
            this.access = 10;
        }
        else if(this.type.equals("Government")){
           this.access = 0;
        }
        else if(this.type.equals("Doctor")){
            this.access = 1;
        }
        else if(this.type.equals("Nurse")){
            this.access = 2;
        }
        else if(this.type.equals("Patient")){
            this.access = 3;
        }
        else{
            System.out.println("Invalid user type");
        }
    }

    public void readFiles(){
        if(access == 0 || access == 1 || access == 2 || access == 3){
            System.out.println("Permission to read files granted");
        }
        else{
            System.out.println("Read access denied");
        }
    }
    public void writeFiles(){
        if(access == 0 || access == 1 || access == 2){
            System.out.println("Permission to write files granted");
        }        else{
            System.out.println("Access denied");
        }
    }
    public void createFiles(){
        if(access == 1){
            System.out.println("Permission to create files granted");
        }
        else{
            System.out.println("Access denied");
        }
    }
    public void deleteFiles(){
        if(access == 0){
            System.out.println("Permission to delete files granted");
        }
        else{
            System.out.println("Access denied");
        }
    } 
    public void addUser(){
        if(access == 10){
            System.out.println("Permission to add new users granted");
            Scanner input = new Scanner(System.in);
            System.out.println("Enter new username: ");
            String newUsername = input.nextLine();
            System.out.println("Enter new password: ");
            String newPassword = input.nextLine();
            System.out.println("Enter new user type: ");
            String newType = input.nextLine();
            System.out.println("Enter new user access: ");
            int newAccess = input.nextInt();
            NewUser(newUsername, newPassword, newType, newAccess);
        }
        else{
            System.out.println("Access denied");
        }
    }  
}
