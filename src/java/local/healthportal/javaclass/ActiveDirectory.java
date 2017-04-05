
package local.healthportal.javaclass;
import local.healthportal.ConfigClass.AD_Admin_Config;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 *
 * @author vaz
 */

    public class ActiveDirectory {
    
    private String DOMAIN_NAME = "AD-portal.local"; //domain name
    private String DOMAIN_ROOT = "DC=AD-portal,DC=local";//split domain fully qualified name ex: healthportal.local
    private String DOMAIN_URL = "ldap://192.168.14.2:389"; //Domain Controller server IP Address with port number 389 for AD connection
    private String DOMAIN_SSL_URL = "ldaps://192.168.14.2:636"; //server URL for ssl connection
    private String DOMAIN_CONTROLLER_KEYSTORE_PATH ="";
    
    private String ADMIN_NAME = "";// Active Directory Administrator username 
    private String ADMIN_PASS = ""; //Active Directory Administrator password
    private String DOMAIN_ADMIN_LOGINNAME ="";
    private String OrganizationalUnit="portal";
   
    private  DirContext dirContext=null; //DirContext for AD connection
    private SearchControls searchcontrol=null;//searchControls for searching thru AD
    private NamingEnumeration enumeration=null;//NamingEnumeration for user's returned attributes
	
//Default constructor with no parameter 
public ActiveDirectory(){
 //do something here.....
}
//constructor to connect Active Directory with given username and password and without domain controller certificate path
public ActiveDirectory(String username,String password, String domainname,String domainIP){
        this.ADMIN_NAME=username;
        this.ADMIN_PASS=password;
        this.DOMAIN_URL = "ldap://"+domainIP+":389"; //Domain Controller URL for LDAP connection with AD with simple mode
        this.DOMAIN_SSL_URL = "ldaps://"+domainIP+":636"; //Domain Controller URL for LDAP connection with AD over SSL mode
        this.DOMAIN_ADMIN_LOGINNAME = this.ADMIN_NAME+"@"+domainname;//create user principal for login
        this.DOMAIN_NAME=domainname;
        this.DOMAIN_ROOT =getDomainRoot(domainname);
} //ends constructore here.......
    
//constructor to connect Active Directory with given username and password with domain controller certificate path as well
public ActiveDirectory(String username,String password, String domainname,String domainIP,String Domain_Controller_Certificate_Path){
        this.ADMIN_NAME=username;
        this.ADMIN_PASS=password;
        this.DOMAIN_URL = "ldap://"+domainIP+":389"; //Domain Controller URL for LDAP connection with AD with simple mode
        this.DOMAIN_SSL_URL = "ldaps://"+domainIP+":636"; //Domain Controller URL for LDAP connection with AD over SSL mode
        this.DOMAIN_ADMIN_LOGINNAME = this.ADMIN_NAME+"@"+domainname;//create user principal for login
        this.DOMAIN_NAME=domainname;
        this.DOMAIN_ROOT =getDomainRoot(domainname);
        this.DOMAIN_CONTROLLER_KEYSTORE_PATH = Domain_Controller_Certificate_Path;
} //ends constructore here.......
//constructor to connect Active Directory with given username and password with domain controller certificate path as well
public ActiveDirectory(String username,String password, String domainname,String domainIP,String Domain_Controller_Certificate_Path,String OU){
        this.ADMIN_NAME=username;
        this.ADMIN_PASS=password;
        this.DOMAIN_URL = "ldap://"+domainIP+":389"; //Domain Controller URL for LDAP connection with AD with simple mode
        this.DOMAIN_SSL_URL = "ldaps://"+domainIP+":636"; //Domain Controller URL for LDAP connection with AD over SSL mode
        this.DOMAIN_ADMIN_LOGINNAME = this.ADMIN_NAME+"@"+domainname;//create user principal for login
        this.DOMAIN_NAME=domainname;
        this.DOMAIN_ROOT=getDomainRoot(domainname);
        this.DOMAIN_CONTROLLER_KEYSTORE_PATH = Domain_Controller_Certificate_Path;
        this.OrganizationalUnit = OU;
} //ends constructore here.......
   
//Method to create Domain Root based given domain name ex:mydomain.com
//to DC=mydomain,DC=com
private String getDomainRoot(String domainname){
  String domain[] = domainname.split("\\.");
  String domainroot="";
  for(int index=0; index<domain.length; index++){
    if(index==domain.length-1) {  
        domainroot = domainroot+"DC="+domain[index];
    }   
    else{ domainroot = domainroot+"DC="+domain[index]+",";}
  }
        return domainroot;
}//ends method here...............
        
//Method to make a simple connection to active directory    
public boolean SimpleConnectToActiveDirectory() {	
	boolean connectionstatus=false;
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple"); // simple mode connectin with AD
        env.put(Context.SECURITY_PRINCIPAL, this.DOMAIN_ADMIN_LOGINNAME);
        env.put(Context.SECURITY_CREDENTIALS, this.ADMIN_PASS);
        env.put(Context.PROVIDER_URL, this.DOMAIN_URL);
         env.put("com.sun.jndi.ldap.connect.pool", "false");
        env.put("com.sun.jndi.ldap.connect.timeout", "300000");
try {
        this.dirContext = new InitialDirContext(env);
        System.out.println("Connection to AD is successful");
        connectionstatus = true;  
}catch (NamingException e) {
        System.out.println("Connection to AD is failed");
        e.printStackTrace();
        return connectionstatus;
}
     return connectionstatus;
}// ends method here.....
    
//It works fine
//Method to make SSL connection or secure or encrypted connection with Active Directory
public boolean SSLConnectToActiveDirectory(){
        boolean connectionstatus=false;
        System.setProperty("javax.net.ssl.trustStore",this.DOMAIN_CONTROLLER_KEYSTORE_PATH);
        //Hashtable env = new Hashtable();
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_PROTOCOL,"ssl"); // use SSL security protocol to make connection with AD
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, this.DOMAIN_ADMIN_LOGINNAME);
        env.put(Context.SECURITY_CREDENTIALS, this.ADMIN_PASS);
        env.put(Context.PROVIDER_URL, this.DOMAIN_SSL_URL);
        env.put("com.sun.jndi.ldap.connect.pool", "false");
        env.put("com.sun.jndi.ldap.connect.timeout", "300000");
try {
        this.dirContext = new InitialDirContext(env);
        System.out.println("Connection to AD is successful");
        connectionstatus = true;
}catch(NamingException e) {
   e.printStackTrace();
   System.out.println("cannot connect to active directory server");
   return connectionstatus;
}
  return connectionstatus;
}// ends method here......
    
//It works fine
//Method to make SSL connection or secure or encrypted connection with Active Directory
public boolean SSLConnectToActiveDirectory(String Domain_Controller_Certificate_Path){
       boolean connectionstatus=false;
       this.DOMAIN_CONTROLLER_KEYSTORE_PATH = Domain_Controller_Certificate_Path;
       System.setProperty("javax.net.ssl.trustStore",this.DOMAIN_CONTROLLER_KEYSTORE_PATH);
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_PROTOCOL,"ssl"); // use SSL security protocol to make connection with AD
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, this.DOMAIN_ADMIN_LOGINNAME);
        env.put(Context.SECURITY_CREDENTIALS, this.ADMIN_PASS);
        env.put(Context.PROVIDER_URL, this.DOMAIN_SSL_URL);
        env.put("com.sun.jndi.ldap.connect.pool", "false");
        env.put("com.sun.jndi.ldap.connect.timeout", "300000");
try {
            this.dirContext = new InitialDirContext(env);
            System.out.println("Connection to AD is successful");
            connectionstatus = true;
}catch(NamingException e) {
    e.printStackTrace();
    System.out.println("Cannot connect to Active Directory Server");
    return connectionstatus;
}
    return connectionstatus;
}// ends method here......


//This method is used for user login test 
public boolean LoginToActiveDirectory(String username,String password,String domainname,String serverIP){	
	boolean connectionstatus=false;
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple"); // simple mode connectin with AD
        env.put(Context.SECURITY_PRINCIPAL, username+"@"+domainname);//user's principal name
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.PROVIDER_URL, "ldap://"+serverIP+":389");
        env.put("com.sun.jndi.ldap.connect.pool", "false");
        env.put("com.sun.jndi.ldap.connect.timeout", "300000");      
try{
        this.dirContext = new InitialDirContext(env);
        System.out.println("Connection to AD is successful");
        connectionstatus = true;
}catch (NamingException e) {
        System.err.println("Connection to AD is failed ");
        e.printStackTrace();
        return connectionstatus;
}
    return connectionstatus;
}// ends method here.....        
/*...................WORKS FINE....................*/
//Method to enable an user's account
public boolean enableUser(String name,String OrganizationalUnitName,int UserAccountControlFlag){
     int userAccountControlValue = UserAccountControlFlag;// value: 512 or 544
     String dn =  getUserDN(name,OrganizationalUnitName);
try{
    ModificationItem[] mods = new ModificationItem[1];
    mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userAccountControl",""+userAccountControlValue));
    this.dirContext.modifyAttributes(dn, mods);
    System.out.println("User's account is enabled");
    return true;
}catch(NamingException e){
      System.out.println("cannot enable user's account");
      e.printStackTrace();
      System.err.println();
      return false;
}
}//ends method here.....
/*......WORKS FINE.......*/
//Method to get useraccount control flag if user exists otherwise returns zero
public int IsUserExist(String name,String OrganizationalUnitName){
    int flag = 0;
    String userdn = getUserDN(name,OrganizationalUnitName);// get user distinguished name ex: CN=name,OU=myorganization,DC=domainname,DC=com
try{    
    String temp = this.dirContext.getAttributes(userdn).get("userAccountControl").get().toString();
    flag = Integer.parseInt(temp);
}catch(NamingException e){
      System.out.println("User does not exist");
      e.printStackTrace();
      System.err.println();
      return flag;// return flag zero if user with given userdn does not exist in the AD
}
     return flag;// return flag value of the given userdn
}//ends method here.....    
//................WORKS FINE..........................
//method to check whether a name is already exist in the active directory
public boolean IsNameExist(String name,String OrganizationalUnitName){
    boolean flag=false ;
    String userdn = getUserDN(name,OrganizationalUnitName);// get user distinguished name ex: CN=name,OU=myorganization,DC=domainname,DC=com
try{    
     String temp = this.dirContext.getAttributes(userdn).get("cn").get().toString();
     if(!temp.equalsIgnoreCase("No attributes")){
       flag = true;
     }
}catch(NamingException e){
 return flag;
}
     return flag;
}//ends method here.....    
    
/*...................WORKS FINE....................*/
//Method to disable an user's account
public boolean DisableUser(String name,String OrganizationalUnitName) throws NamingException {
    String dn =  getUserDN(name,OrganizationalUnitName);
try{     
    int userAccountControlValue = 514; //to disable user's account set its useraccountcontrol value to 514
    ModificationItem[] mods = new ModificationItem[1];
    mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userAccountControl",""+userAccountControlValue));
    this.dirContext.modifyAttributes(dn, mods);
    System.out.println("User's account is enabled");
    return true;
}catch(NamingException e){
      System.out.println("cannot enable user's account");
      e.printStackTrace();
      return false;
     }
}//ends method here.....    
//It works fine
// Method to update user password in AD
public boolean UpdatePassword(String name, String password,String OrganizationalUnitName){       
    boolean check=false;
    String BASE_NAME;
            if(OrganizationalUnitName.equalsIgnoreCase("null")){
            
                 //BASE_NAME = ",cn=users,"+this.DOMAIN_ROOT;// it means user is in the main container users
                 BASE_NAME = getUserDN(name,"users");
            }
            else{
                //BASE_NAME = ",OU="+OrganizationalUnitName+","+this.DOMAIN_ROOT;//it means user is in the given OU
                BASE_NAME = getUserDN(name,OrganizationalUnitName);
            }
            byte pwdArray[] = EncodePassword(password); //encode user's password
try{
	    ModificationItem[] mods = new ModificationItem[1];
	    mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("UnicodePwd", pwdArray));
	    this.dirContext.modifyAttributes(BASE_NAME, mods);
            check=true;
            System.out.println("user password updated!");
}catch (NamingException |NullPointerException e){ 
	e.printStackTrace();
        System.out.println("update password error: " + e);
        return check;
}
        return check;
}//method ends here......
    
   //WORKS FINE 
  //method to update certain user's attribute 
public boolean UpdateUserAttribute(String name, String attributeName, String attributeValue,String OrganizationalUnitName){   
    boolean check=false; 
    String BASE_NAME;
        if(OrganizationalUnitName.equalsIgnoreCase("null")){
          BASE_NAME = getUserDN(name,"users");
        }
        else{
           BASE_NAME = getUserDN(name,OrganizationalUnitName);
        }
try{ 
	    System.out.println("updating " + name +"\n");
	    ModificationItem[] mods = new ModificationItem[1];
	    mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(attributeName, attributeValue));
	    this.dirContext.modifyAttributes(BASE_NAME, mods);
            check=true;
            System.out.println("Your "+attributeName+ "is updated to "+ attributeValue);
}catch (NamingException e){  
            e.printStackTrace();
	    System.out.println(" update error: " + e);
	    return check;
}
    return check;    
         
}//method ends here......
//WORKS FINE 
//Method to new user attribute to Active Directory  
public boolean AddNewAttribute(String name, String NewAttributeName, String NewAttributeValue,String OrganizationalUnitName){   
  boolean check=false; 
  String BASE_NAME;
        if(OrganizationalUnitName.equalsIgnoreCase("null")){
          BASE_NAME = getUserDN(name,"users");
        }
        else{
          BASE_NAME = getUserDN(name,OrganizationalUnitName);
        }
try{
	    ModificationItem[] mods = new ModificationItem[1];
	    mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, new BasicAttribute(NewAttributeName, NewAttributeValue));
	    this.dirContext.modifyAttributes(BASE_NAME, mods);
            check=true;
            System.out.println(NewAttributeName+ "is added "+ NewAttributeValue);
}catch (NamingException e){  
            e.printStackTrace();
	    System.out.println(" add new attribute error: " + e);
	    return check;
}
    return check;    
         
}//method ends here...... 
//WORKS FINE
//method to remove user's attributes
public boolean RemoveAttribute(String name, String AttributeName, String AttributeValue,String OrganizationalUnitName){   
    boolean check=false; 
    String BASE_NAME;
        if(OrganizationalUnitName.equalsIgnoreCase("null")){
            BASE_NAME = getUserDN(name,"users");
        }
        else{
            BASE_NAME = getUserDN(name,OrganizationalUnitName);
        }
try{ 
	    ModificationItem[] mods = new ModificationItem[1];
	    mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute(AttributeName, AttributeValue));
	    this.dirContext.modifyAttributes(BASE_NAME, mods);
            check=true;
            System.out.println("Your "+AttributeName+ "with value: " + AttributeValue +" is already removed");
}catch (NamingException e){  
            e.printStackTrace();
	    System.out.println(" remove attribute error: " + e);
	    return check;
}
    return check;    
         
}//method ends here......  


//WORKS FINE
//method to update certain user's attribute 
public boolean UpdateUsername(String name, String NewUsername,String OrganizationalUnitName){   
    boolean check=false; 
    String BASE_NAME;
        if(OrganizationalUnitName.equalsIgnoreCase("null")){
            BASE_NAME = getUserDN(name,"users");
        }
        else{
            BASE_NAME = getUserDN(name,OrganizationalUnitName);
        }
        String sAMAccountName = NewUsername;
        String userPrincipalName = NewUsername+"@"+AD_Admin_Config.DOMAIN_NAME;
        String uid = NewUsername;
try{ 
	 
            System.out.println("updating " + NewUsername +"\n");
	    ModificationItem[] mods = new ModificationItem[3];
	    mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("sAMAccountName",sAMAccountName ));
	    mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPrincipalName",userPrincipalName ));
            mods[2] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("uid",uid));

            this.dirContext.modifyAttributes(BASE_NAME, mods);
            check=true;
            System.out.println("Your username is updated to:"+NewUsername);
}catch (NamingException e){  
            e.printStackTrace();
	    System.out.println(" update error: " + e);
	    return check;
}
    return check;    
         
}//method ends here......

//WORKS FINE
//method to update certain user's attribute 
public boolean UpdateName(String name, String firstname, String lastname,String OrganizationalUnitName){   
    boolean check=false; 
    String BASE_NAME;
    String userOldDn,userNewDn;
    String userNewName = new StringBuffer(firstname).append(" ").append(lastname).toString();
        if(OrganizationalUnitName.equalsIgnoreCase("null")){
            BASE_NAME = getUserDN(name,"users");
            userOldDn = getUserDN(name,"users");
            userNewDn = getUserDN(userNewName,"users");
        }
        else{
            BASE_NAME = getUserDN(name,OrganizationalUnitName);
            userOldDn = getUserDN(name,OrganizationalUnitName);
            userNewDn = getUserDN(userNewName,OrganizationalUnitName);
        }
try{ 
            String cnValue = userNewName;
            System.out.println("updating " + name +"\n");
            ModificationItem[] mods = new ModificationItem[3];
            //update user's lastname or sn
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("sn", lastname));
            //update user's first name or givenName
            mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("givenName", firstname));
            //update user's display name 
            mods[2] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("displayName", cnValue));
	    this.dirContext.modifyAttributes(BASE_NAME, mods);
            //rename user's common name or distinguishedname
            this.dirContext.rename(userOldDn, userNewDn);
            check=true;
            System.out.println("Your name is already updated to "+cnValue);
            
}catch (NamingException|NullPointerException e){  
            e.printStackTrace();
	    System.out.println("update error: " + e);
	    return check;
}
    return check;    
        
}//method ends here......
 
  //WORKS FINE : can get names of all groups an user belongs to 
  //Method to get all attributes of an user's from an organizational unit or container under CN=users
  //if a certain user is under the main container and not under tht OU
  //if certain user's attribute value is null or empty it will be excluded  
public HashMap<String,String> GetUserAttributes(String Name,String OrganizationalUnitName){
    HashMap<String,String> eachattribute = new HashMap();
    String dN;
    //if OU is not given then the search will be done on the main container
    if(OrganizationalUnitName.equalsIgnoreCase("null")){
      dN = getUserDN(Name,"users");
    }
    else{ //Otherwise the search will be done on the given OU container
        dN = getUserDN(Name,OrganizationalUnitName);
    }
try{
    Attributes answer = this.dirContext.getAttributes(dN);
    for (NamingEnumeration<?> ae = answer.getAll(); ae.hasMore();) {
    Attribute attr = (Attribute) ae.next();
    String attributeName = attr.getID();
    String value="";
    for (NamingEnumeration<?> e = attr.getAll(); e.hasMore();) {
           if(attributeName.equalsIgnoreCase("memberOf")){ 
                 value = value+e.next().toString()+",";    
                 System.out.println(attributeName + ":" +value);
           }else{ 
                value = value+e.next().toString();
                System.out.println(attributeName + "=" +value);
           }
           
        
    }
        eachattribute.put(attributeName, value);//add each attribute name and value to map
    }
}catch (NamingException e){
	e.printStackTrace();
        System.out.println("user is not exist");
        return eachattribute;
}
    return eachattribute;
}//ends method here......


//WORK FINE
//method to add an user to a certain group 
public boolean AddToGroup(String Name,String groupName,String organizationalUnitName){
        boolean check = false;
        String userDn ;
        String groupDn ;
    //if OU is not given then the search will be done on the main container
    if(organizationalUnitName.equalsIgnoreCase("null")){
       userDn = getUserDN(Name,"users");
       groupDn = getUserDN(groupName,"users");
    }
    else{ //Otherwise the search will be done on the given OU container
        userDn = getUserDN(Name,organizationalUnitName);
        groupDn = getUserDN(groupName,organizationalUnitName);
    }
        
try {
       ModificationItem mods[] = new ModificationItem[1];
       mods[0]= new ModificationItem(DirContext.ADD_ATTRIBUTE, new BasicAttribute("member", userDn));
       this.dirContext.modifyAttributes(groupDn,mods);
       System.out.println(Name+" is already a member of "+groupName+" group");
       check=true;
}catch(NamingException e) {
       e.printStackTrace();
       System.err.println("Problem adding member: " + e);
       return check;
}
return check;
}// ends method here......

//method to remove user from a group
//WORK FINE..................Test...........................
//method to add an user to a certain group 
public boolean RemoveUserFromGroup(String Name,String groupName,String organizationalUnitName){
    boolean check = false;
    String userDn,groupDn;    
    //if OU is not given then the search will be done on the main container
    if(organizationalUnitName.equalsIgnoreCase("null")){
       userDn = getUserDN(Name,"users");
       groupDn = getUserDN(groupName,"users");
    }
    else{ //Otherwise the search will be done on the given OU container
        userDn = getUserDN(Name,organizationalUnitName);
        groupDn = getUserDN(groupName,organizationalUnitName);
    }

try {
       ModificationItem mods[] = new ModificationItem[1];
       mods[0]= new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute("member", userDn));
       this.dirContext.modifyAttributes(groupDn,mods);
       System.out.println(Name+" is already removed from "+groupName+" group");
       check=true;
}catch(NamingException e) {
	  System.err.println("Problem removing user from group: " + e);
         return check;
 }
       // } //ends else here........
        return check;
}// ends method here......


//WORK FINE
//Method to add new user to an organizational unit in Active Directory
// without adding user password and enable user account
public boolean addUser(String firstName,String lastName,String Email,String userName,String password,String organisationUnit){
        Attributes container = new BasicAttributes();
        // Create the objectclass to add
        Attribute objClasses = new BasicAttribute("objectClass");
        objClasses.add("top");
        objClasses.add("person");
        objClasses.add("organizationalPerson");
        objClasses.add("user");
        // Assign the username, first name, and last name
        String cnValue = new StringBuffer(firstName).append(" ").append(lastName).toString();
        Attribute cn = new BasicAttribute("cn", cnValue);
        Attribute sAMAccountName = new BasicAttribute("sAMAccountName", userName);
        Attribute principalName = new BasicAttribute("userPrincipalName", userName + "@" + this.DOMAIN_NAME);
        Attribute givenName = new BasicAttribute("givenName", firstName);
        Attribute sn = new BasicAttribute("sn", lastName);
        Attribute uid = new BasicAttribute("uid", userName);
        Attribute displayname = new BasicAttribute("displayname",firstName+" "+lastName);
        Attribute email = new BasicAttribute("mail",Email); 
        byte[]UnicodePassword = EncodePassword(password);//encode the user password
        Attribute pass = new BasicAttribute("unicodePwd",UnicodePassword);
        // Add these to the container
        container.put(objClasses);
        container.put(sAMAccountName);
        container.put(principalName);
        container.put(cn);
        container.put(sn);
        container.put(givenName);
        container.put(uid);
        container.put(displayname);
        container.put(email);
        container.put(pass);
try {
            this.dirContext.createSubcontext(getUserDN(cnValue, organisationUnit), container);
            System.out.println("successfully add user to active directory");
            return true;
} catch (NamingException e) {
            System.out.println("There is an error, cannot add new user");
            e.printStackTrace();
            return false;
}
}//ends method here......
//WORKS FINE
//method to delete an user in active directory
public boolean DeleteUser(String name, String OU){
    String userDn;
    boolean check=false;
    //check if user located at users container 
    if(OU.equalsIgnoreCase("users")){
      userDn = getUserDN(name,"users");
    }else{
      userDn = getUserDN(name,OU);  
    }
try{
    this.dirContext.destroySubcontext(userDn);
    System.out.println("user is successfully deleted");
    check = true;
}catch (NamingException e) {
   e.printStackTrace();
   System.out.println("Error,cannot delete the user");
   return check;
}
        return check;
}//ends method here......................

//WORKS FINE
//method to split attribute name and values
private String Spliter(String value){
   String temp[] = value.split(":");
   String attributename = temp[0].substring(temp[0].indexOf("=")+1);
   String attributevalue = temp[1].substring(1, temp[1].indexOf("}"));
   String result=attributename+":"+attributevalue;
   return result;
}//ends method here..........

//WORKS FINE
//method to  only get one user's attribute by passing attribute name and username
public String GetOneAttribute(String username,String attributename,String OU){
   String value="attribute:No attributes"; 
   String searchBase; 
    if(OU.equalsIgnoreCase("null")){
      searchBase = "CN=users,"+this.DOMAIN_ROOT;   
    }
    else{ //Otherwise the search will be done on the given OU container
      searchBase = "OU="+OU+","+this.DOMAIN_ROOT;  
    }          
    this.searchcontrol = new SearchControls();
    String returnedAtts[];
    returnedAtts = new String[1];
    returnedAtts[0]=attributename;
    this.searchcontrol.setReturningAttributes(returnedAtts);
    this.searchcontrol.setSearchScope(SearchControls.SUBTREE_SCOPE);
    String searchFilter = "(&(objectClass=user)(sAMAccountName=" +username+ "))";
try{            
    this.enumeration = this.dirContext.search(searchBase, searchFilter, this.searchcontrol);
    while (this.enumeration.hasMoreElements()){
       SearchResult sr = (SearchResult)this.enumeration.next();
       if(sr.getAttributes().toString().equalsIgnoreCase("No attributes")){
         value="attribute:No attributes";
       }else{//otherwise attribute has value
         value = Spliter(sr.getAttributes().toString());
        }                
    }//ends while here..................
        
}catch (NamingException | NullPointerException e) {
    System.out.println("User attribute value is empty");
    return value;// return e.g attributename:attributevalue
}
 
return value;

}//ends method here..........................
/* ........................ JUST ADDED NOT FINISH YET ......................... */
//method to fetch user's attributes from active directory using username or sAMAccountName
public HashMap<String,String> FetchUserAttributes(String username,String OU){
   HashMap<String,String> attribute=null;
   String searchBase; 
   NamingEnumeration<SearchResult> result=null;
    if(OU.equalsIgnoreCase("null")){
      searchBase = "CN=users,"+this.DOMAIN_ROOT;   
    }
    else{ //Otherwise the search will be done on the given OU container
      searchBase = "OU="+OU+","+this.DOMAIN_ROOT;  
    }          
    this.searchcontrol = new SearchControls();
    String[] returnedAtts = {"objectCategory","mail","memberOf","instanceType","st","objectClass",
            "company","name","description","sn","telephoneNumber","mobile","userAccountControl","primaryGroupID",
            "postalcode","accountExpires","physicalDeliveryOfficeName","co","cn","title","logonCount",
            "sAMAccountType","givenName","displayName","userPrincipalName","department","streetAddress",
            "countryCode","l","distinguishedName","c","sAMAccountName"}; 
        
    this.searchcontrol.setReturningAttributes(returnedAtts);
    this.searchcontrol.setSearchScope(SearchControls.SUBTREE_SCOPE);
    String Filter = "(&((&(objectCategory=Person)(objectClass=User)))"+"(samaccountname=" +username+ "))";
try{            
    result = this.dirContext.search(searchBase, Filter, this.searchcontrol);    
    if(result.hasMore()) {
	SearchResult rs= (SearchResult)result.next();
        Attributes attrs = rs.getAttributes();
        //attributes = "attributes of the user are found!";
        String temp = attrs.get("samaccountname").toString();
        //username = temp;
        username = temp.substring(temp.indexOf(":")+1);
	System.out.println("Username	: " + temp.substring(temp.indexOf(":")+1));
        temp = attrs.get("givenname").toString();
        // name = temp;
        String name = temp.substring(temp.indexOf(":")+1);
        System.out.println("Name: " + temp.substring(temp.indexOf(":")+1));
        temp = attrs.get("mail").toString();
        // emailID = temp;
        String emailID = temp.substring(temp.indexOf(":")+1);
	System.out.println("Email ID	: " + temp.substring(temp.indexOf(":")+1));
        temp = attrs.get("cn").toString();
        // Display_name = temp;
        String Display_name= temp.substring(temp.indexOf(":")+1);
	System.out.println("Display Name : " + temp.substring(temp.indexOf(":")+1) + "\n\n"); 
                        
        temp = attrs.get("userPrincipalName").toString();
        String principalname = temp.substring(temp.indexOf(":")+1);
                         
        temp = attrs.get("streetAddress").toString();
        String address = temp.substring(temp.indexOf(":")+1);
                          
        temp = attrs.get("memberOf").toString();
        String group = temp.substring(temp.indexOf(":")+1);            
    } 
  return attribute;     
}catch (NamingException | NullPointerException e) {
    e.printStackTrace();
    System.out.println("User attribute value is empty");
    return attribute=null;
}finally{
if(result!=null){
try{
   result.close();
}catch(NamingException ex) {
 System.out.println(ex.getMessage()); 
}
}else{
       System.out.println("enumeration is null,no need to be close"); 
}
}
}//ends method here..........................
//...............WORK FINE......................
//method to check wheter a certain username already exist in active directory
public boolean IsUsernameExist(String username,String OU){
   boolean value=false; 
   String searchBase; 
    if(OU.equalsIgnoreCase("null")){
      searchBase = "CN=users,"+this.DOMAIN_ROOT;   
    }
    else{ //Otherwise the search will be done on the given OU container
      searchBase = "OU="+OU+","+this.DOMAIN_ROOT;  
    }          
    this.searchcontrol = new SearchControls();
    String returnedAtts[];
    returnedAtts = new String[1];
    returnedAtts[0]="cn";
    this.searchcontrol.setReturningAttributes(returnedAtts);
    this.searchcontrol.setSearchScope(SearchControls.SUBTREE_SCOPE);
    String searchFilter = "(&(objectClass=user)(sAMAccountName=" +username+ "))";
try{            
    this.enumeration = this.dirContext.search(searchBase, searchFilter, this.searchcontrol);
    //Loop through the search results
    while (this.enumeration.hasMoreElements()) 
    {
       SearchResult sr = (SearchResult)this.enumeration.next();
        String temp = sr.getAttributes().toString();
       if(!temp.equalsIgnoreCase("No attributes")){
         value=true;
       }              
    }//ends while here..................
        
}catch (NamingException e) {
    return value;
}
 
return value;

}//ends method here..........................

     
//WORK FINE 
//Method to encoded user's password into uniCodePassword
private byte[] EncodePassword(String password){
    String temp = "\"" + password + "\"";
    byte[]UnicodePassword=null;
try{
        UnicodePassword = temp.getBytes("UTF-16LE");
}catch(UnsupportedEncodingException e) {
   System.out.println("Problem encoding password: " + e);
   e.printStackTrace();
}
 return UnicodePassword;//return the encoded password
}// ends method here........ 
     
//WORK FINE
//method to create user distinguished name with domain root
private String getUserDN(String aUsername, String aOU){
    String result="";
    //check if user located at users container 
    if(aOU.equalsIgnoreCase("users")){
         result = "cn=" + aUsername + ",cn=" + aOU + "," + this.DOMAIN_ROOT;
    }else{
         result = "cn=" + aUsername + ",ou=" + aOU + "," + this.DOMAIN_ROOT;
    }
  return result;
}//ends method here......................

// WORKS FINE   
//Method to close the connection to Active Directory
public void CloseConnection(){
try {
        if(this.dirContext != null){
            this.dirContext.close();
            System.out.println("Connection to AD is already closed!");
        }
} catch (NamingException e) {
  System.out.println("Cannot close AD connection!,since it is null");
}
try{ 
        if(this.enumeration!=null){
            this.enumeration.close();
            System.out.println("enumeration is already closed!");
        }
}catch (NamingException e) {
System.out.println("Cannot close enumeration!,since it is null");
}
} //ends method here.......
    
}/* ...............................................ends class here.......................................*/


