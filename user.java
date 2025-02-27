public class user {
    /*Here each type of user will have their own methods and attributes
    *A user can be Government, Doctor, Nurse, Patient, or Admin
    *Only Admin can add new users
    * Government can read and delete files
    * Doctor can read,write and create files
    * Nurse can read and write files
    * Patient can only read files
    *Each user will have their own username and password
    */  
    private String username;
    private String password;
    private String type;
    public user(String username, String password, String type){
        this.username = username;
        this.password = password;
        this.type = type;
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
    public void addNewUser(String username, String password, String type){
        if(this.type.equals("Admin")){
            user newUser = new user(username, password, type);
        }
    }
}
