package local.healthportal.javaclass;

/**
 *
 * @author Win10
 */


/*
 This java class has function to check whether user's password pass all 
 the password policy requirements of Microsoft Active Directory 
 For the reference have a look at below microsoft website link
 https://technet.microsoft.com/en-us/library/cc786468(v=ws.10).aspx   
    
*/
public class PasswordChecker {
private String password; 
private boolean check=false;
private String username;
private String firstname;
private String lastname;
private String name;
 // default constructor
public PasswordChecker(){

}
//constructor with parameter
public PasswordChecker(String username,String firstname,String lastname,String password){
this.username = username;
this.firstname = firstname;
this.lastname = lastname;
this.password = password;
this.name = firstname+lastname;
}
//Method to check whether user's password contains his or her username
//return true if the password does not contain username otherwise return false
public boolean Is_Password_Not_Contain_Username(){
 boolean result = false;
 if(this.password.toLowerCase().contains(this.username.toLowerCase())){
    result = false;
 }else{result=true;}
return result;
}//ends method here..... 

//Method to check whether user's password contains his or her name(fname+lname)
//return true if the password does not contain name otherwise return false
public boolean Is_Password_Not_Contain_Name(){
 boolean result = false;
 if(this.password.toLowerCase().contains(this.name.toLowerCase())){
    result = false;
 }else{result=true;}
return result;

}//ends method here................

//Method to check other password policy requirements for Microsoft Active Directory
// For the details of the requirements please refere to the link above this class 
public boolean Is_All_Password_Requirements_Pass(){  
boolean hasUppercase = !this.password.equals(password.toLowerCase());//true if password contains uppercase
boolean hasLowercase = !this.password.equals(password.toUpperCase()); //true if password contains lowercase
boolean isAtLeast8   = this.password.length() >=8;//true if password length is at least equal to 8 or greater
boolean hasSpecial = this.password.matches(".*[!@#$%^&*?+=-_].*");//true if password contains any special character
boolean hasNumeric = this.password.matches(".*\\d.*"); //true if password contains any numeric or number

//check if password does not contain either username and name 
if(Is_Password_Not_Contain_Username()&& Is_Password_Not_Contain_Name()){
//both true then continue checking for other password requirements 

if(hasUppercase && hasLowercase && hasNumeric && isAtLeast8){
 this.check = true;
}
else if(hasUppercase && hasLowercase && hasSpecial && isAtLeast8){
 this.check = true;
}
else if(hasUppercase && hasNumeric && hasSpecial && isAtLeast8){
 this.check = true;
}
else if(hasNumeric && hasLowercase && hasSpecial && isAtLeast8){
 this.check = true;
}

else if(hasUppercase && hasLowercase && hasSpecial && hasNumeric && isAtLeast8){
 this.check = true;
}
else{
 this.check = false;
}
}//ends outer if here................
else{
//either username or name contains in password then stop checking for other password requirements
// and return false right away

this.check = false;
}

return this.check;
}//ends method here......
    
    
}//ends class



