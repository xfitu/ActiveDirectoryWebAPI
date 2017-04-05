
package local.healthportal.javaclass;

import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author Win10
 */
public class UserAttributes {
    
    private String Status;
    private String ErrorMessage;
    private String SuccessMessage;
    private String objectCategory; //CN=Person,CN=Schema,CN=Configuration,DC=healthportal,DC=local
    private String mail; //email or user email address
    private String email; //email or user email address
    private String memberOf; //memberOf groups which are an user belongs to
    private String group; //memberOf groups which are an user belongs to
    private String instanceType;// 4
    private String st; // st or state or province
    private String state; // st or state or province
    private String objectClass; //top
    private String company; //company name
    private String name;// user full name ex: firstname + lastname or firstname+middlename+lastname
    private String description; //description about the user
    private String lastname; // sn or user last name in the active directory
    private String sn; // sn or user last name in the active directory
    private String telephoneNumber; //user phone number
    private String userAccountControl; // ex: 66048
    private String primaryGroupID; //ex: 513
    private String postalCode; //postal code of user state or province
    private String physicalDeliveryOfficeName; // user's office name
    private String country; //co or user's country or region ex: Thailand, has attribute ID co
    private String co; //co or user's country or region ex: Thailand, has attribute ID co
    private String commonname; //cn or user full name or common name
    private String cn; //cn or user full name or common name
    private String title; //user job's title ex: doctor or patient
    private String mobile; //user phone number
    private String sAMAccountType; //user's account type ex:805306368
    private String givenName; //givenname or user's first name in the active directory
    private String firstname; //givenname or user's first name in the active directory
    private String displayName; //user display name in the active directory
    private String userPrincipalName; // testuser@healthportal.local
    private String department;//user's department in active directory
    private String streetAddress; // user's street address
    private String countryCode; //user's country code ex: Thailand = 764
    private String l; // l or user's city in Active directory which has attribute ID is l
    private String city; // l or user's city in Active directory which has attribute ID is l
    private String c; // c or First two letters of user's country or region ex: Thailand = TH
    private String fist_twoletters_of_countryname; // c or First two letters of user's country or region ex: Thailand = TH
    private String distinguishedName; //user's distinguished name ex: CN=test user,OU=portal,DC=healthportal,DC=local
    private String sAMAccountName; //user account name without domain name 
    private String username; //user account name without domain name 
    private String PoBox;
 
//Default constructor    
public UserAttributes(){

}  
//constructor
public UserAttributes(String attributewithvalue){
  String temp[]= attributewithvalue.split(":");//attributename and its value
   /* EX:
    attributewithvalue = description:this is a doctor user
    temp[0] = description
    temp[1] = this is a doctor user
  
  */
        if(temp[0].equalsIgnoreCase("objectCategory")){
         this.objectCategory = temp[1];
        //user.setObjectCategory(objectCategory);
        }
        if(temp[0].equalsIgnoreCase("mail")){
        //this.mail = map.get(key);
         this.email = temp[1];
        //user.setMail(mail);
        }
        if(temp[0].equalsIgnoreCase("memberOf")){
        //this.memberOf = temp;
        this.group = MakeUserGroupName(temp[1]);
        //user.setMemberOf(memberOf);
        }
        if(temp[0].equalsIgnoreCase("instanceType")){
        this.instanceType = temp[1];
        //user.setInstanceType(instanceType);
        }
        if(temp[0].equalsIgnoreCase("st")){
        //this.st = map.get(key);
        this.state = temp[1];
        //user.setSt(st);
        }
        if(temp[0].equalsIgnoreCase("objectClass")){
        this.objectClass = temp[1];
        //user.setObjectClass(objectClass);
        }
        if(temp[0].equalsIgnoreCase("company")){
        this.company = temp[1];
       // user.setCompany(company);
        }
        if(temp[0].equalsIgnoreCase("name")){
        this.name = temp[1];
       // user.setName(name);
        }
        if(temp[0].equalsIgnoreCase("description")){
        this.description = temp[1];
       // user.setDescription(description);
        }
        if(temp[0].equalsIgnoreCase("sn")){
        //this.sn = map.get(key);
        this.lastname = temp[1];
        //user.setSn(sn);
        }
        if(temp[0].equalsIgnoreCase("telephoneNumber")){
        this.telephoneNumber = temp[1];
        //user.setTelephoneNumber(telephoneNumber);
        }
        if(temp[0].equalsIgnoreCase("userAccountControl")){
        this.userAccountControl = temp[1];
        //user.setUserAccountControl(userAccountControl);
        }
        if(temp[0].equalsIgnoreCase("primaryGroupID")){
        this.primaryGroupID = temp[1];
        //user.setPrimaryGroupID(primaryGroupID);
        }
        if(temp[0].equalsIgnoreCase("postalCode")){
        this.postalCode = temp[1];
        //user.setPostalCode(postalCode);
        }
        if(temp[0].equalsIgnoreCase("physicalDeliveryOfficeName")){
        this.physicalDeliveryOfficeName = temp[1];
        //user.setPhysicalDeliveryOfficeName(physicalDeliveryOfficeName);
        }
        if(temp[0].equalsIgnoreCase("co")){
        //this.co = map.get(key);
        this.country = temp[1];
        //user.setCo(co);
        }
        if(temp[0].equalsIgnoreCase("cn")){
        this.cn = temp[1];
        //this.commonname = map.get(key);
        //user.setCn(cn);
        }
        if(temp[0].equalsIgnoreCase("title")){
        this.title = temp[1];
        //user.setTitle(title);
        }
        if(temp[0].equalsIgnoreCase("mobile")){
        this.mobile = temp[1];
       // user.setMobile(mobile);
        }
        if(temp[0].equalsIgnoreCase("sAMAccountType")){
        this.sAMAccountType = temp[1];
        //user.setsAMAccountType(sAMAccountType);
        }
        if(temp[0].equalsIgnoreCase("givenName")){
        //this.givenName = map.get(key);
        this.firstname = temp[1];
        //user.setGivenName(givenName);
        }
        if(temp[0].equalsIgnoreCase("displayName")){
        this.displayName = temp[1];
        //user.setDisplayName(displayName);
        }
        if(temp[0].equalsIgnoreCase("userPrincipalName")){
        this.userPrincipalName = temp[1];
        //user.setUserPrincipalName(userPrincipalName);
        }
        if(temp[0].equalsIgnoreCase("department")){
        this.department = temp[1];
        //user.setDepartment(department);
        }
        if(temp[0].equalsIgnoreCase("streetAddress")){
        this.streetAddress = temp[1];
        //user.setStreetAddress(streetAddress);
        }
        if(temp[0].equalsIgnoreCase("countryCode")){
        this.countryCode = temp[1];
        //user.setCountryCode(countryCode);
        }
        if(temp[0].equalsIgnoreCase("l")){
        //this.l = map.get(key);
        this.city = temp[1];
        //user.setL(l);
        }
        if(temp[0].equalsIgnoreCase("c")){
        //this.c = map.get(key);
        this.fist_twoletters_of_countryname = temp[1];
         
        }
        if(temp[0].equalsIgnoreCase("distinguishedName")){
        //this.distinguishedName = map.get(key);
        //setDistinguishedName(this.distinguishedName);
        this.distinguishedName = temp[1];
        }
        if(temp[0].equalsIgnoreCase("sAMAccountName")){
        //this.sAMAccountName = map.get(key);
        this.username = temp[1];
        //setsAMAccountName(this.sAMAccountName);
        }
        if(temp[0].equalsIgnoreCase("postOfficeBox")){
        this.PoBox = temp[1];
        }


}//ends constructor
//Constructor has HashMap parameter
public UserAttributes(HashMap<String,String> map){
for (String key : map.keySet()) {
        //System.out.println("key: " + key + " value: " + map.get(key));
        if(key.equalsIgnoreCase("objectCategory")){
         this.objectCategory = map.get(key);
        //user.setObjectCategory(objectCategory);
        }
        if(key.equalsIgnoreCase("mail")){
        //this.mail = map.get(key);
        this.email = map.get(key);
        //user.setMail(mail);
        }
        if(key.equalsIgnoreCase("memberOf")){
        this.memberOf = MakeUserGroupName(map.get(key));
        String temp = map.get(key);
        //this.memberOf = temp;
        this.group = MakeUserGroupName(temp);
        //user.setMemberOf(memberOf);
        }
        if(key.equalsIgnoreCase("instanceType")){
        this.instanceType = map.get(key);
        //user.setInstanceType(instanceType);
        }
        if(key.equalsIgnoreCase("st")){
        //this.st = map.get(key);
        this.state = map.get(key);
        //user.setSt(st);
        }
        if(key.equalsIgnoreCase("objectClass")){
        this.objectClass = map.get(key);
        //user.setObjectClass(objectClass);
        }
        if(key.equalsIgnoreCase("company")){
        this.company = map.get(key);
       // user.setCompany(company);
        }
        if(key.equalsIgnoreCase("name")){
        this.name = map.get(key);
       // user.setName(name);
        }
        if(key.equalsIgnoreCase("description")){
        this.description = map.get(key);
       // user.setDescription(description);
        }
        if(key.equalsIgnoreCase("sn")){
        //this.sn = map.get(key);
        this.lastname = map.get(key);
        //user.setSn(sn);
        }
        if(key.equalsIgnoreCase("telephoneNumber")){
        this.telephoneNumber = map.get(key);
        //user.setTelephoneNumber(telephoneNumber);
        }
        if(key.equalsIgnoreCase("userAccountControl")){
        this.userAccountControl = map.get(key);
        //user.setUserAccountControl(userAccountControl);
        }
        if(key.equalsIgnoreCase("primaryGroupID")){
        this.primaryGroupID = map.get(key);
        //user.setPrimaryGroupID(primaryGroupID);
        }
        if(key.equalsIgnoreCase("postalCode")){
        this.postalCode = map.get(key);
        //user.setPostalCode(postalCode);
        }
        if(key.equalsIgnoreCase("physicalDeliveryOfficeName")){
        this.physicalDeliveryOfficeName = map.get(key);
        //user.setPhysicalDeliveryOfficeName(physicalDeliveryOfficeName);
        }
        if(key.equalsIgnoreCase("co")){
        //this.co = map.get(key);
        this.country = map.get(key);
        //user.setCo(co);
        }
        if(key.equalsIgnoreCase("cn")){
        this.cn = map.get(key);
        //this.commonname = map.get(key);
        //user.setCn(cn);
        }
        if(key.equalsIgnoreCase("title")){
        this.title = map.get(key);
        //user.setTitle(title);
        }
        if(key.equalsIgnoreCase("mobile")){
        this.mobile = map.get(key);
       // user.setMobile(mobile);
        }
        if(key.equalsIgnoreCase("sAMAccountType")){
        this.sAMAccountType = map.get(key);
        //user.setsAMAccountType(sAMAccountType);
        }
        if(key.equalsIgnoreCase("givenName")){
        //this.givenName = map.get(key);
        this.firstname = map.get(key);
        //user.setGivenName(givenName);
        }
        if(key.equalsIgnoreCase("displayName")){
        this.displayName = map.get(key);
        //user.setDisplayName(displayName);
        }
        if(key.equalsIgnoreCase("userPrincipalName")){
        this.userPrincipalName = map.get(key);
        //user.setUserPrincipalName(userPrincipalName);
        }
        if(key.equalsIgnoreCase("department")){
        this.department = map.get(key);
        //user.setDepartment(department);
        }
        if(key.equalsIgnoreCase("streetAddress")){
        this.streetAddress = map.get(key);
        //user.setStreetAddress(streetAddress);
        }
        if(key.equalsIgnoreCase("countryCode")){
        this.countryCode = map.get(key);
        //user.setCountryCode(countryCode);
        }
        if(key.equalsIgnoreCase("l")){
        //this.l = map.get(key);
        this.city = map.get(key);
        //user.setL(l);
        }
        if(key.equalsIgnoreCase("c")){
        //this.c = map.get(key);
        this.fist_twoletters_of_countryname = map.get(key);
         
        }
        if(key.equalsIgnoreCase("distinguishedName")){
         this.distinguishedName = map.get(key);
        //setDistinguishedName(this.distinguishedName);
        }
        if(key.equalsIgnoreCase("sAMAccountName")){
        //this.sAMAccountName = map.get(key);
        this.username = map.get(key);
        //setsAMAccountName(this.sAMAccountName);
        }
        if(key.equalsIgnoreCase("postOfficeBox")){
        this.PoBox = map.get(key);
        //this.username = map.get(key);
        //setsAMAccountName(this.PoBox);
        }
        
        }//ends for loop here.......

} //ends constructor..........................................   

//method to create user group name from memberOf attribute    
private String MakeUserGroupName(String memberOf){
    String resultgroup="";
    ArrayList<String> list=new ArrayList<>();    
    String[]temp; 
    String []groups ={};
    String []mygroups=new String[2];
    //String group = "CN=Doctor,CN=Patient,DC=portal,DC=healthportal,DC=local";
    String delimiter = ",";
    temp = memberOf.split(delimiter);
    for(int i =0; i < temp.length ; i++) {
       // System.out.println(temp[i]);
        if(temp[i].contains("CN")){
           
            groups = temp[i].split("=");
            //System.out.println("Group:"+groups[1]);
             list.add(groups[1]);
        } 
    }
    
    for (int i = 0; i < list.size(); i++) {
        System.out.println(list.get(i));
        if(i!=list.size()-1){
         resultgroup=resultgroup+list.get(i)+",";
        }else{
         resultgroup=resultgroup+list.get(i);
        }
    }
    
    return resultgroup;
}//ends method here.........  

    /**
     * @return the objectCategory
     */
    public String getObjectCategory() {
        return objectCategory;
    }

    /**
     * @param objectCategory the objectCategory to set
     */
    public void setObjectCategory(String objectCategory) {
        this.objectCategory = objectCategory;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the memberOf
     */
    public String getMemberOf() {
        return memberOf;
    }

    /**
     * @param memberOf the memberOf to set
     */
    public void setMemberOf(String memberOf) {
        this.memberOf = MakeUserGroupName(memberOf);
    }

    /**
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(String group) {
        this.group =MakeUserGroupName(group);
    }

    /**
     * @return the instanceType
     */
    public String getInstanceType() {
        return instanceType;
    }

    /**
     * @param instanceType the instanceType to set
     */
    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    /**
     * @return the st
     */
    public String getSt() {
        return st;
    }

    /**
     * @param st the st to set
     */
    public void setSt(String st) {
        this.st = st;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the objectClass
     */
    public String getObjectClass() {
        return objectClass;
    }

    /**
     * @param objectClass the objectClass to set
     */
    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the sn
     */
    public String getSn() {
        return sn;
    }

    /**
     * @param sn the sn to set
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * @return the telephoneNumber
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     * @param telephoneNumber the telephoneNumber to set
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * @return the userAccountControl
     */
    public String getUserAccountControl() {
        return userAccountControl;
    }

    /**
     * @param userAccountControl the userAccountControl to set
     */
    public void setUserAccountControl(String userAccountControl) {
        this.userAccountControl = userAccountControl;
    }

    /**
     * @return the primaryGroupID
     */
    public String getPrimaryGroupID() {
        return primaryGroupID;
    }

    /**
     * @param primaryGroupID the primaryGroupID to set
     */
    public void setPrimaryGroupID(String primaryGroupID) {
        this.primaryGroupID = primaryGroupID;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the physicalDeliveryOfficeName
     */
    public String getPhysicalDeliveryOfficeName() {
        return physicalDeliveryOfficeName;
    }

    /**
     * @param physicalDeliveryOfficeName the physicalDeliveryOfficeName to set
     */
    public void setPhysicalDeliveryOfficeName(String physicalDeliveryOfficeName) {
        this.physicalDeliveryOfficeName = physicalDeliveryOfficeName;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the co
     */
    public String getCo() {
        return co;
    }

    /**
     * @param co the co to set
     */
    public void setCo(String co) {
        this.co = co;
    }

    /**
     * @return the commonname
     */
    public String getCommonname() {
        return commonname;
    }

    /**
     * @param commonname the commonname to set
     */
    public void setCommonname(String commonname) {
        this.commonname = commonname;
    }

    /**
     * @return the cn
     */
    public String getCn() {
        return cn;
    }

    /**
     * @param cn the cn to set
     */
    public void setCn(String cn) {
        this.cn = cn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the sAMAccountType
     */
    public String getsAMAccountType() {
        return sAMAccountType;
    }

    /**
     * @param sAMAccountType the sAMAccountType to set
     */
    public void setsAMAccountType(String sAMAccountType) {
        this.sAMAccountType = sAMAccountType;
    }

    /**
     * @return the givenName
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * @param givenName the givenName to set
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the userPrincipalName
     */
    public String getUserPrincipalName() {
        return userPrincipalName;
    }

    /**
     * @param userPrincipalName the userPrincipalName to set
     */
    public void setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the streetAddress
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * @param streetAddress the streetAddress to set
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return the l
     */
    public String getL() {
        return l;
    }

    /**
     * @param l the l to set
     */
    public void setL(String l) {
        this.l = l;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the c
     */
    public String getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(String c) {
        this.c = c;
    }

    /**
     * @return the fist_twoletters_of_countryname
     */
    public String getFist_twoletters_of_countryname() {
        return fist_twoletters_of_countryname;
    }

    /**
     * @param fist_twoletters_of_countryname the fist_twoletters_of_countryname to set
     */
    public void setFist_twoletters_of_countryname(String fist_twoletters_of_countryname) {
        this.fist_twoletters_of_countryname = fist_twoletters_of_countryname;
    }

    /**
     * @return the distinguishedName
     */
    public String getDistinguishedName() {
        return distinguishedName;
    }

    /**
     * @param distinguishedName the distinguishedName to set
     */
    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }

    /**
     * @return the sAMAccountName
     */
    public String getsAMAccountName() {
        return sAMAccountName;
    }

    /**
     * @param sAMAccountName the sAMAccountName to set
     */
    public void setsAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the PoBox
     */
    public String getPoBox() {
        return PoBox;
    }

    /**
     * @param PoBox the PoBox to set
     */
    public void setPoBox(String PoBox) {
        this.PoBox = PoBox;
    }

    /**
     * @return the Status
     */
    public String getStatus() {
        return Status;
    }

    /**
     * @param Status the Status to set
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }

    /**
     * @return the ErrorMessage
     */
    public String getErrorMessage() {
        return ErrorMessage;
    }

    /**
     * @param ErrorMessage the ErrorMessage to set
     */
    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    /**
     * @return the SuccessMessage
     */
    public String getSuccessMessage() {
        return SuccessMessage;
    }

    /**
     * @param SuccessMessage the SuccessMessage to set
     */
    public void setSuccessMessage(String SuccessMessage) {
        this.SuccessMessage = SuccessMessage;
    }


    
    
    
    
}//ends class here......................................
