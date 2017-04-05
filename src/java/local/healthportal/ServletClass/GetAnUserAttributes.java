package local.healthportal.ServletClass;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import local.healthportal.ConfigClass.AD_Admin_Config;
import local.healthportal.javaclass.ActiveDirectory;
import local.healthportal.ConfigClass.DomainController_Certificate_Config;
import local.healthportal.javaclass.ResponseObject;
import local.healthportal.javaclass.UserAttributes;

/**
 * This Servlet has been test and it works fine: test DONE
 * @author Win10
 */
public class GetAnUserAttributes extends HttpServlet {
    private static final long serialVersionUID = 1L;

   public GetAnUserAttributes(){
    super();
   } 

        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
}//ends doGet here...........................

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

String name = request.getParameter("name");
PrintWriter out=null;
UserAttributes ua = null;
Gson gson = new GsonBuilder().disableHtmlEscaping().create();

try{
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
     //get user's attributes
    ua = GetAttributes(name);
    out = response.getWriter();
     //JSON response
    out.println(gson.toJson(ua));
    out.flush();//flush printwritter
    
}catch(NullPointerException e){
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    String Status="fail";
    String ErrorMessage="invalid access,must provide all the required parameter in the request"; 
    ResponseObject ro = new ResponseObject();
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
              
}//ends doPost here............

    
    @Override
    public String getServletInfo() {
        return "This Servlet class is used to retrieve all the attributes of an user which has value from Active Directory";
    }//ends here...............................


//method to retrieve all the user's attributes which have values in active directory
private UserAttributes GetAttributes(String name){     
 HashMap<String,String> attributes=null; 
 UserAttributes userattributes = null;
 String Status="",ErrorMessage="",SuccessMessage=""; //variable to set response object value

 if(name.isEmpty()){
    Status="fail";
    ErrorMessage="empty parameter value";       
}
else{
        
     ActiveDirectory ad = new ActiveDirectory(AD_Admin_Config.ADMIN_USERNAME,AD_Admin_Config.ADMIN_PASSWORD,AD_Admin_Config.DOMAIN_NAME,AD_Admin_Config.SERVER_IP);
     ad.SSLConnectToActiveDirectory(DomainController_Certificate_Config.DOMAIN_CONTROLLER_CERTIFICATE_PATH);
     attributes = ad.GetUserAttributes(name,AD_Admin_Config.OU);
     if(attributes.size()>0){
         Status="success";
         SuccessMessage="only attributes which have values";  
              
     }else{
        Status="fail";
        ErrorMessage="oops,name does not exist";  
        
     }
    
    ad.CloseConnection();//close connection to AD
 }//ends else here.................................
     if(Status.equalsIgnoreCase("success")){
        userattributes = new UserAttributes(attributes);
        userattributes.setStatus(Status);
        userattributes.setSuccessMessage(SuccessMessage);

     }else{
        userattributes = new UserAttributes();
        userattributes.setStatus(Status);
        userattributes.setErrorMessage(Status);
     
     }
     
 return userattributes;
}//ends method here.......     
    
    
}//ends class here............................
