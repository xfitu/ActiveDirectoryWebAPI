
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
 * This Servlet has test and it works fine:test DONE
 * @author Win10
 */
public class UpdatePassword extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdatePassword(){
        super();
   } 
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
 }//ends doGet method here................

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
String password = request.getParameter("password");
String name = request.getParameter("name");
String username = request.getParameter("username");        
PrintWriter out=null;
ResponseObject ro = null;
Gson gson = new GsonBuilder().disableHtmlEscaping().create();
     
try{            
     response.setCharacterEncoding("UTF-8");
     response.setContentType("application/json");
     //update user's password in active directory
     ro = Update(password,name,username);
     out = response.getWriter();
     //JSON response
     out.println(gson.toJson(ro));
     out.flush();//flush printwritter
            
}catch(NullPointerException e){
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    String Status="fail";
    String ErrorMessage="invalid access,must provide all the required parameter in the request"; 
    ro = new ResponseObject();
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
        
}//ends doPost method here........................

    
    @Override
    public String getServletInfo() {
        return "This Servlet class is used to update user's password in Active Directory";
    }// </editor-fold>


//method to update user's password in active directory
private ResponseObject Update(String password,String name,String username){
ResponseObject ro;
String Status="",SuccessMessage="",ErrorMessage=""; 
if(password.isEmpty()|name.isEmpty()|username.isEmpty()){
    Status="fail";
    ErrorMessage="some parameters values are empty";       
}
else{
    
    String []temp = name.split(" ");
    String fname = temp[0];
    String lname = temp[1];
    //instantiate passwordchecker class to verify all the password requirements
    PasswordChecker check = new PasswordChecker(username,fname,lname,password);
    //return true if the given password passes all the password requirements for Active Directory
    if(check.Is_All_Password_Requirements_Pass()){
        //instantiate active directory class
        ActiveDirectory ad = new ActiveDirectory(AD_Admin_Config.ADMIN_USERNAME,AD_Admin_Config.ADMIN_PASSWORD,AD_Admin_Config.DOMAIN_NAME,AD_Admin_Config.SERVER_IP);
        boolean connection = ad.SSLConnectToActiveDirectory(DomainController_Certificate_Config.DOMAIN_CONTROLLER_CERTIFICATE_PATH);
     if(connection){
        //update user password
        boolean result = ad.UpdatePassword(name, password,AD_Admin_Config.OU);
        if(result){
            Status="success";
            SuccessMessage="your account password is already updated";
        }else{
             Status="fail";
             ErrorMessage="oops,your account password cannot be updated";
            }
     }else{ Status="fail";
            ErrorMessage="No database connection";
     }  
           
    }else{ //otherwise user's password won't be update
        Status="fail";
        ErrorMessage="provided password does not pass password requirements";
    }
}//ends else here.............................................
     ro = new ResponseObject();
     if(Status.equalsIgnoreCase("success")){
        ro.setStatus(Status);
        ro.setSuccessMessage(SuccessMessage);
     }else{
        ro.setStatus(Status);
        ro.setErrorMessage(ErrorMessage);
     }
return ro;
}//ends method here......    
    
}//ends class here........
