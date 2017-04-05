
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
 *This Servlet has test and it works fine:test DONE
 * @author Win10
 */
public class RemoveUserFromGroup extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RemoveUserFromGroup(){
    super();
   } 
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);           
}//doGet here........................

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
String name = request.getParameter("name");//name of the user whose address will be updated
String groupname = request.getParameter("groupname");
PrintWriter out=null;
ResponseObject ro = null;
Gson gson = new GsonBuilder().disableHtmlEscaping().create();

try{
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
     //remove user from a group in active directory 
    ro = Remove(name,groupname);
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
                        
}//doPost here...............................

    
    @Override
    public String getServletInfo() {
        return "This Servlet class is used to remove an user from a group in active directory";
    }//ends here...........................

    
//method to remove user from a group
private ResponseObject Remove(String name,String groupname){
ResponseObject ro;
String Status="",ErrorMessage="",SuccessMessage=""; //variable to set response object value
if(name.isEmpty()|groupname.isEmpty()){
    Status="fail";
    ErrorMessage="some parameters values are empty";       
}else{
              
     ActiveDirectory ad = new ActiveDirectory(AD_Admin_Config.ADMIN_USERNAME,AD_Admin_Config.ADMIN_PASSWORD,AD_Admin_Config.DOMAIN_NAME,AD_Admin_Config.SERVER_IP);
     ad.SSLConnectToActiveDirectory(DomainController_Certificate_Config.DOMAIN_CONTROLLER_CERTIFICATE_PATH);
     boolean check = ad.RemoveUserFromGroup(name, groupname,AD_Admin_Config.OU );
     if(check){
       Status="success";
       SuccessMessage="user is already removed from "+groupname+" group";    
     
     }else{
        Status="fail";
        ErrorMessage="oops,user cannot be removed from "+groupname+ " group" ;  
     
     }
    ad.CloseConnection();//close connection to AD
 }//ends else here.................................
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



}//ends class here................................
