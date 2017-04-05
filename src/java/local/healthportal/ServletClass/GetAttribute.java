
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
import local.healthportal.javaclass.UserAttributes;

/**
 * This Servlet has test and it works fine: test DONE
 * @author Win10
 */
public class GetAttribute extends HttpServlet {
 private static final long serialVersionUID = 1L;

   public GetAttribute(){
    super();
   } 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
        
}//ends doGet here.............................

        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
String username = request.getParameter("username");//name of the user whose address will be updated
String attributename = request.getParameter("attributename");
PrintWriter out=null;
UserAttributes ua = null;
Gson gson = new GsonBuilder().disableHtmlEscaping().create();

try{
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
     //get user's attributes
    ua = GetAttribute(username,attributename);
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
         
       
}//ends doPost here.........................

    
    @Override
    public String getServletInfo() {
        return "This Servlet class is used to retrieve an user's attribute from Active Directory";
    }//ends here........................................

//method to retrieve an user's attribute from active directory
private UserAttributes GetAttribute(String username,String attributename){
UserAttributes attribute = null; 
String result = null;
String Status="",ErrorMessage="",SuccessMessage=""; //variable to set response object value
if(username.isEmpty()|attributename.isEmpty()){
    Status="fail";
    ErrorMessage="some parameters values are empty";       
}else{
    //instantiate active directory class   
     ActiveDirectory ad = new ActiveDirectory(AD_Admin_Config.ADMIN_USERNAME,AD_Admin_Config.ADMIN_PASSWORD,AD_Admin_Config.DOMAIN_NAME,AD_Admin_Config.SERVER_IP);
     ad.SSLConnectToActiveDirectory(DomainController_Certificate_Config.DOMAIN_CONTROLLER_CERTIFICATE_PATH);
     //boolean check = ad.DeleteUser(name,AD_Admin_Config.OU );
     result = ad.GetOneAttribute(username,attributename,AD_Admin_Config.OU );
     if(!result.equalsIgnoreCase("attribute:No attributes")){
       Status="success";
       SuccessMessage="attribute is found";
     }else{ 
        Status="fail";
        ErrorMessage="user attribute has no value" ;  
     }
    ad.CloseConnection();//close connection to AD
 }//ends else here.................................
     if(Status.equalsIgnoreCase("success")){
        attribute = new UserAttributes(result); 
        attribute.setStatus(Status);
        attribute.setSuccessMessage(SuccessMessage);   
     }else{
        attribute = new UserAttributes();
        attribute.setStatus(Status);
        attribute.setErrorMessage(ErrorMessage);
        
     }
return attribute;    
}//ends method here.....    
    
}//ends class here....
