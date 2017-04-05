
package local.healthportal.ServletClass;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static local.healthportal.ConfigClass.AD_Admin_Config.*;
import local.healthportal.javaclass.ActiveDirectory;
import static local.healthportal.ConfigClass.DomainController_Certificate_Config.DOMAIN_CONTROLLER_CERTIFICATE_PATH;
import local.healthportal.javaclass.ResponseObject;

/**
 *This method has been test and it works fine:test DONE
 * @author Win10
 */
public class ActivateAccount extends HttpServlet {
     private static final long serialVersionUID = 1L;

   public ActivateAccount(){
    super();
   } 
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            doPost(request,response);
}//ends doGet here...............

        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                     
    String fname = request.getParameter("fname");
    String lname = request.getParameter("lname");
    PrintWriter out=null;
    ResponseObject ro=null;
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    
try {
    
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    out = response.getWriter();
    //call activateaccount method to verify and activate user's account in active directory
    ro = ActivateAccount(fname,lname);
    //JSON response
    out.println(gson.toJson(ro));
    out.flush();
               
}catch(NullPointerException e) {
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

}//ends doPost here........................

    
    @Override
    public String getServletInfo() {
        return "This Servlet class is used to activate or enable user account in Active Directory";
    }//ends here..........................

    
//method to activate account user account
private ResponseObject ActivateAccount(String fname,String lname){
  ResponseObject ro;
  String name = new StringBuffer(fname).append(" ").append(lname).toString();
  String Status="",ErrorMessage="",SuccessMessage=""; //variable to set response object value
  
  if(fname.isEmpty()|lname.isEmpty()){
        Status="fail";
        ErrorMessage="some parameters values are empty";  
    
    }else{
        ActiveDirectory ad = new ActiveDirectory(ADMIN_USERNAME,ADMIN_PASSWORD,DOMAIN_NAME,SERVER_IP);
        if(ad.SSLConnectToActiveDirectory(DOMAIN_CONTROLLER_CERTIFICATE_PATH)){
            //check if user is already exist in the active directory
            int accountflag = ad.IsUserExist(name, OU);
            if(accountflag==0){//zero if this account does not exist or not register yet in AD
             Status="fail";
             ErrorMessage="Invalid verification link or this account is not registered yet";  
            }
            //if account flag either 546 or 514 then account has been registered
            //and not activated yet so go ahead and activate the account 
            if(accountflag==546|accountflag==514){
              //important note: in order to enable or activate the user's account  
              //to a normal account then useraccountcontrol flag must be set to 512
              int newaccountcontrolvalue = 512; 
              if(ad.enableUser(name, OU,newaccountcontrolvalue )){
                    Status="success";
                    SuccessMessage="Your account has been activated,you can login now";   
              }else{
                    Status="fail";
                    ErrorMessage="oops,your account cannot be activated";   
              }
              
            }//ends if here...............................
            //if account flag either 512 or 544 then account is already activated or enable
            if(accountflag==512|accountflag==544){
              Status="fail";
              ErrorMessage="expired verification link, or maybe your account is already activated";   
  
            }
         ad.CloseConnection();//close connection to Active Directory after everything is done
    
        }else{      
            Status="fail";
            ErrorMessage="cannot connect to database,your account is not activated yet";    
        }
            
        
   }//ends else here....................................
    ro = new ResponseObject();
    if(Status.equalsIgnoreCase("success")){
        ro.setStatus(Status);
        ro.setSuccessMessage(SuccessMessage);
    }else{
        ro.setStatus(Status);
        ro.setErrorMessage(ErrorMessage);
    }

return ro;
}//ends method here...........    
    
    
}/* ....... ends class here........ */


