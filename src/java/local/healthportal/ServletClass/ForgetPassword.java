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
import local.healthportal.javaclass.ResponseObject;

/**
 * This Servlet has been test and it works fine:test DONE
 * @author Win10
 */

public class ForgetPassword extends HttpServlet {
    private static final long serialVersionUID = 1L;

   public ForgetPassword(){
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
          
    String name = request.getParameter("name");
    String username = request.getParameter("username");
    String email = request.getParameter("email");
   
    PrintWriter out=null;
    ResponseObject ro=null;
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
try{
    
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    out = response.getWriter();
    //call CheckUser method to check user in active directory
    ro = CheckUser(name,username,email);
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
        return "This Servlet class is used to check user to active directory when forget password";
    }//ends here................................
    
//Method to check user's username and name in active directory
private ResponseObject CheckUser(String name,String username,String email){
    ResponseObject ro;
    String Status="",ErrorMessage="",SuccessMessage=""; 
if(name.isEmpty()|email.isEmpty()|username.isEmpty())  
{   
     Status ="fail";
     ErrorMessage="some parameters values are empty";
   
} 
else{  
    //instantiate ActiveDirectory class to add the user to Active Directory
    ActiveDirectory ad = new ActiveDirectory(AD_Admin_Config.ADMIN_USERNAME,AD_Admin_Config.ADMIN_PASSWORD,AD_Admin_Config.DOMAIN_NAME,AD_Admin_Config.SERVER_IP); 
    // make ssl connection to the active directory by using Administrator credential
    //since only administrator accounts have permission to add new user to Active Directory
    if(ad.SSLConnectToActiveDirectory(DomainController_Certificate_Config.DOMAIN_CONTROLLER_CERTIFICATE_PATH)){
          boolean checkusername = ad.IsUsernameExist(username,AD_Admin_Config.OU );
          boolean checkname = ad.IsNameExist(name, AD_Admin_Config.OU);
          if(checkname&&checkusername){  
                Status = "success";
                ErrorMessage="valid user";
           }
          else{
                Status = "fail";
                ErrorMessage="invalid user";
          }
            ad.CloseConnection(); //close connection
           
    }else{ 
        Status = "fail";
        ErrorMessage = "no database connection";
    }   
            
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
