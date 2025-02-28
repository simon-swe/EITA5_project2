

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
    private String department;
    private int access;

    public user(String username, String password, String type, String department){
        this.username = username;
        this.password = password;
        this.type = type;
        this.department = department;
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
    public String getDepartment(){
        return this.department;
    }
    public int getAccess(){
        return this.access;
    }
   
    /*Doctor has access to read, write and create files
     *Nurse has access to read and write files
     *Patient has access to read files
     *Government has access to read and delete files
     *Admin has full access to the system and can create new users*/
    public void userType(){
        if(this.type.equals("Government")){
            System.out.println("Permission to read files granted");
           this.access = 0;
        }
        else if(this.type.equals("Doctor")){
            System.out.println("Permission to read files granted");
            this.access = 1;
        }
        else if(this.type.equals("Nurse")){
            System.out.println("Permission to read files granted");
            this.access = 2;
        }
        else if(this.type.equals("Patient")){
            System.out.println("Permission to read files granted");
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
}
