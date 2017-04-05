
package local.healthportal.ServletClass;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import local.healthportal.javaclass.Email;
import static local.healthportal.ConfigClass.Email_Config.SENDER_EMAIL;
import static local.healthportal.ConfigClass.Email_Config.SENDER_PASSWORD;
import local.healthportal.javaclass.ResponseObject;

/**
 * This Servlet has been test and it works fine:test DONE
 * @author Win10
 */
public class SendEmail extends HttpServlet {
     private static final long serialVersionUID = 1L;

   public SendEmail(){
    super();
   } 

       @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    
    
}//ends doGet here...............................

        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    String subject = request.getParameter("subject");
    String body= request.getParameter("body");
    String link = request.getParameter("link");
    String fname = request.getParameter("fname");
    String lname = request.getParameter("lname");
    String receiveremail = request.getParameter("receiveremail");

    PrintWriter out=null;
    ResponseObject ro = null;
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
     
try{            
     response.setCharacterEncoding("UTF-8");
     response.setContentType("application/json");
     //Send email by calling SendingEmail method
     ro = SendingEmail(subject,body,link, fname,lname,receiveremail);
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
        

}//ends doPost here...............................

        @Override
    public String getServletInfo() {
        return "This Servlet class is used to send email";
    }//ends here...................
    
    
//Method to send Email 
private ResponseObject SendingEmail(String subject,String body,String link,String fname,String lname,String receiveremail){          
String Status="",ErrorMessage="",SuccessMessage=""; //variable to set response object value
ResponseObject ro;//responseobject
if(fname.isEmpty()|lname.isEmpty()|receiveremail.isEmpty()|subject.isEmpty()|body.isEmpty()|link.isEmpty()){
    Status="fail";
    ErrorMessage="empty parameter value";       
}
else{         
   String Emailbody = body+" "+link+"?fname="+fname+"&lname="+lname+" ";
   //instantiate SendEmail class
   Email sendemail = new Email(SENDER_EMAIL,SENDER_PASSWORD);
   sendemail.sendMail(receiveremail,subject, Emailbody);//send the verification email
   if(sendemail.IsEmailSent()){
    Status="success";
    SuccessMessage="email has sent";  
   }else{
    Status="fail";
    ErrorMessage="invalid email address";  
   }

}//ends else here............................   
     ro = new ResponseObject();
     if(Status.equalsIgnoreCase("fail")){
        ro.setStatus(Status);
        ro.setErrorMessage(ErrorMessage);
        
     }else{ 
       ro.setStatus(Status);
       ro.setSuccessMessage(SuccessMessage); 
     }
      
       
return ro; //return object

}//ends method here........    

}/* ends class here.......*/
