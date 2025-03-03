
/*Here each type of user will have their own methods and attributes
* A user can be Government, Doctor, Nurse, Patient or Admin
* Only Admin can add new users
* Government can read and delete files
* Doctor can read,write and create files
* Nurse can read and write files
* Patient can only read files
* Each user will have their own username and password
*/
public class Access {
    private String username;
    private String type;
    private String department;
    private int access;

    public Access(String username, String type, String department){
        this.username = username;
        this.type = type;
        this.department = department;
        this.access = userType();
    }
    public String getUsername(){
        return this.username;
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
    private int userType(){
        if(this.type.equals("Government")){
            System.out.println("Permission to read files granted");
            System.out.println("Read access denied");
            System.out.println("Create access denied");
            System.out.println("Write access denied");
            System.out.println("Permission to delete files granted");
            return 0;
        }
        else if(this.type.equals("Doctor")){
            System.out.println("Permission to read files granted");
            System.out.println("Permission to write files granted");
            System.out.println("Permission to create files granted");
            System.out.println("Delete access denied");
            return 1;
        }
        else if(this.type.equals("Nurse")){
            System.out.println("Permission to read files granted");
            System.out.println("Permission to write files granted");
            System.out.println("Create access denied");
            System.out.println("Delete access denied");
            return 2;
        }
        else if(this.type.equals("Patient")){
            System.out.println("Permission to read files granted");
            System.out.println("Write access denied");
            System.out.println("Create access denied");
            System.out.println("Delete access denied");
            return 3;
        }
        else{
            System.out.println("Invalid user type");
            return -1;
        }
    }
}
