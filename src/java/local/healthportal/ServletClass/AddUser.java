package local.healthportal.ServletClass;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import local.healthportal.ConfigClass.AD_Admin_Config;
import local.healthportal.javaclass.ActiveDirectory;
import local.healthportal.ConfigClass.DomainController_Certificate_Config;
import local.healthportal.javaclass.PasswordChecker;
import local.healthportal.javaclass.ResponseObject;

/**
 * This Servlet has been test and it works fine:test DONE
 * @author Win10
 */

public class AddUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

   public AddUser(){
    super();
   } 

        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            doPost(request,response);    
        
}//ends doGet here..........................

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          
    String fname = request.getParameter("fname");
    String lname = request.getParameter("lname");
    String username = request.getParameter("username");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String role = request.getParameter("role");
    
    PrintWriter out=null;
    ResponseObject ro=null;
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
try{
    
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    out = response.getWriter();
    //call AddNewUser method to register user to active directory
    ro = AddNewUser(fname,lname,username,email,password,role);
    //JSON response
    out.println(gson.toJson(ro));
    out.flush();
    
}catch(NullPointerException e){
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    ro = new ResponseObject();
    String Status="fail";
    String ErrorMessage="invalid access,must provide all the required parameter in the request"; 
    ro.setStatus(Status);
    ro.setErrorMessage(ErrorMessage);
    out = response.getWriter();
    //JSON response
    out.println(gson.toJson(ro));
    out.flush();
    

}finally {
    if (out != null) { 
        System.out.println("Closing PrintWriter");
        out.close(); 
    } else { 
        System.out.println("PrintWriter not open");
    } 
}  

}//ends doPost here..........................

    
    @Override
    public String getServletInfo() {
        return "This Servlet class is used to add a new user to active directory";
    }//ends here................................
    
//Method to register  new user
private ResponseObject AddNewUser(String fname,String lname,String username,String email,String password,String role){
    ResponseObject ro;
    String Status="",ErrorMessage="",SuccessMessage=""; 
    
if(fname.isEmpty()|lname.isEmpty()|email.isEmpty()|username.isEmpty()|password.isEmpty()|role.isEmpty())  
{   
     Status ="fail";
     ErrorMessage="some parameters values are empty";
   
} 
else{ 
      
    String Name = new StringBuffer(fname).append(" ").append(lname).toString();
    //use this class to check password requirements for the Active Directory
    PasswordChecker check = new PasswordChecker(username,fname,lname,password);
    if(check.Is_All_Password_Requirements_Pass()){
       //instantiate ActiveDirectory class to add the user to Active Directory
       ActiveDirectory ad = new ActiveDirectory(AD_Admin_Config.ADMIN_USERNAME,AD_Admin_Config.ADMIN_PASSWORD,AD_Admin_Config.DOMAIN_NAME,AD_Admin_Config.SERVER_IP); 
       // make ssl connection to the active directory by using Administrator credential
       //since only administrator accounts have permission to add new user to Active Directory
       if(ad.SSLConnectToActiveDirectory(DomainController_Certificate_Config.DOMAIN_CONTROLLER_CERTIFICATE_PATH)){
      //if(ad.SimpleConnectToActiveDirectory()){
          boolean checkusername = ad.IsUsernameExist(username,AD_Admin_Config.OU );
          boolean checkname = ad.IsNameExist(Name, AD_Admin_Config.OU);
          if(checkname|checkusername){  
            Status = "fail";
            ErrorMessage="either name or username is taken";
           }
          else{
              boolean adduserflag = ad.addUser(fname, lname, email, username, password, AD_Admin_Config.OU);
              if(adduserflag){
                 String GroupName = role;//set user's role as group name 
                 boolean addusertogroupflag = ad.AddToGroup(Name, GroupName,AD_Admin_Config.OU);
                    
                 if(addusertogroupflag){ 
                    Status = "success";
                    SuccessMessage = "account is created";
                 }else{
                    Status = "success";
                    SuccessMessage = "account is created but role is not set ";
                 }
                
              }//ends if add user successfully here.......
              else{
                    Status = "fail";
                    ErrorMessage = "fail to create account";
               }
             
            }//ends else register new user here.................
            //close connection to AD after everything is done
            ad.CloseConnection(); //close connection
           
        }//ends if connection to AD here....... 
        else{ 
               Status = "fail";
               ErrorMessage = "no database connection";
        }   
          
    }//ends if all password requirements pass here.....
    // if any of the password requirements for the Active Directory does not pass
    // then return the Active Directory password requirement policy as response
    else{              
        Status = "fail";
        ErrorMessage = "password does not pass all the password requirements";
              
        }//ends else here.. 
 
}//ends else here..........
ro = new ResponseObject();
if(Status.equalsIgnoreCase("success")){
 ro.setStatus(Status);
 ro.setSuccessMessage(SuccessMessage);
}else{
 ro.setStatus(Status);
 ro.setErrorMessage(ErrorMessage);
}

return ro;
}//ends method here.........    
    
    
}//ends class here...............................
