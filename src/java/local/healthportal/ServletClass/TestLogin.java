
package local.healthportal.ServletClass;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static local.healthportal.ConfigClass.AD_Admin_Config.DOMAIN_NAME;
import static local.healthportal.ConfigClass.AD_Admin_Config.SERVER_IP;
import local.healthportal.javaclass.ActiveDirectory;
import static local.healthportal.ConfigClass.DomainController_Certificate_Config.DOMAIN_CONTROLLER_CERTIFICATE_PATH;
import local.healthportal.javaclass.ResponseObject;


/**
 * This Servlet has test and it works fine: test DONE
 * @author Win10
 */
public class TestLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TestLogin(){
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
String username = request.getParameter("username");
String password = request.getParameter("password");
PrintWriter out=null;
ResponseObject ro = null;
Gson gson = new GsonBuilder().disableHtmlEscaping().create();
     
try{            
     response.setCharacterEncoding("UTF-8");
     response.setContentType("application/json");
     //login to active directory
     ro = Login(username,password);
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
        
                  
}//ends doPost here.........................

    
    @Override
    public String getServletInfo() {
        return "This Servlet class is used to  test user login to Active Directory";
    }//ends here........................................


//method to login to active directory
private ResponseObject Login(String username, String password){
ResponseObject ro;
String Status="",ErrorMessage="",SuccessMessage=""; //variable to set response object value
if(username.isEmpty()|password.isEmpty()){
    Status="fail";
    ErrorMessage="some parameters values are empty";      
}else{        
    
         ActiveDirectory ad = new ActiveDirectory(username,password,DOMAIN_NAME,SERVER_IP);
         
         if(ad.SSLConnectToActiveDirectory(DOMAIN_CONTROLLER_CERTIFICATE_PATH)){
            Status="success";
            SuccessMessage="you are log in";           
         }
         else{
             Status="fail";
             ErrorMessage="either incorrect username or password"; 
           }
        //then close connection to act
        ad.CloseConnection();//close connection to Active Directory
    
}//ends else here............................ 

ro = new ResponseObject();
if(Status.equalsIgnoreCase("fail")){
   ro.setStatus(Status);
   ro.setErrorMessage(ErrorMessage);  
}else{   
   ro.setStatus(Status);
   ro.setErrorMessage(ErrorMessage);
} 
      
return ro;
}//ends method here.......


}//ends class here...........................................
