
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
   
    /*Doctor has access to read, write and create files, access level 1
     *Nurse has access to read and write files, access level 2
     *Patient has access to read files, access level 3
     *Government has access to read and delete files, access level 0*/
    public void userType(){
        if(this.type.equals("Government")){
            this.access = 0;
            System.out.println("Permission to read files granted");
            System.out.println("Read access denied");
            System.out.println("Create access denied");
            System.out.println("Write access denied");
            System.out.println("Permission to delete files granted"); 
        }
        else if(this.type.equals("Doctor")){
            this.access = 1;
            System.out.println("Permission to read files granted");
            System.out.println("Permission to write files granted");
            System.out.println("Permission to create files granted");
            System.out.println("Delete access denied");
        }
        else if(this.type.equals("Nurse")){
            this.access = 2;
            System.out.println("Permission to read files granted");
            System.out.println("Permission to write files granted");
            System.out.println("Create access denied");
            System.out.println("Delete access denied");
        }
        else if(this.type.equals("Patient")){
            this.access = 3;
            System.out.println("Permission to read files granted");
            System.out.println("Write access denied");
            System.out.println("Create access denied");
            System.out.println("Delete access denied");
        }
        else{
            System.out.println("Invalid user type");
        }
    }
}
