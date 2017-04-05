# ActiveDirectoryWebAPI
It is Java Web API that Create and Search User's object and its attributes to and from Microsoft Active Directory.There are total fifteen Servlet classes in this project each Servlet class handles different request and returns JSON object as response. The API is created using Java Servlet and deploy on Apache Tomcat Web Server.

 
 There are three packages in this Web API project:
1. Package for configureation Java classes: local.healthportal.ConfigClass
There are three different configuration Java classes in this package:
	1. AD_Admin_Config.java, it contains credential of Active Directory Administrator
	2. DomainController_Certificate_Config.java, it contains the path to the Active Directory server public key certificate for SSL       connection 
	3. Email_Config.jva, it contains email credential that will be used to send email to user's email address
		
2. Package for custom Java classes: local.healhtportal.javaclass
There are total five custom Java classes in this package and each class has different function.
	1. ActiveDirectory.java, it contains all the methods that are used to connect Active Directory server and add new users to Active Directory
	2. Email.java, it is the class used to send email to user's email address by using smtp.gmail.com mail server
	3. PasswordChecker.java, it is the class used to verify password requirements for Active Directory
	4. ResponseObject.java, it is the class used to hold JSON response object 
	5. UserAttributes.java, it is the class used to hold JSON object of user's attributes

3. Package for Java Servlet classes: local.healthportal.ServletClass  
 There are total fifteen Servlet classes in this package and each Servlet class handles different request
 and returns JSON object as response. 
		
1.AddUser

	- Description: register user to active directory
	- access URL: https://localhost:7443/ActiveDirectoryWebAPI/AddUser
	- Required parameter variables: fname,lname,username,email,role, password
	- Send request method: GET or POST
	
	- Reponse is returned as JSON object:
	
		- success: {"Status":"success","SuccessMessage":"your account is already created"} 
		
		- fail: {"Status":"fail","ErrorMessage":"invalid access,must provide all the required parameters in the request"}
	   	
2.SendEmail
	- Description: send email to given email address 
	- access URL: https://localhost:7443/ActiveDirectoryWebAPI/SendEmail
	- Required parameter variables: fname,lname,receiveremail,subject,body,verificationlink
	- Send request method: GET or POST
	
	- Reponse is returned as JSON object:
	
		- success: {"Status":"success","SuccessMessage":"email has sent"} 
		
		- fail: {"Status":"fail","ErrorMessage":"invalid email address"}

3.ActivateAccount
	- Description: activate or enable user's account in active directory 
	- access URL: https://localhost:7443/ActiveDirectoryWebAPI/ActivateAccount
	- Required parameter variables: fname,lname
	- Send request method: GET or POST
	
	- Reponse is returned as JSON object:
	
		- success: {"Status":"success","SuccessMessage":your account has been activated"} 
		
		- fail: {"Status":"fail","ErrorMessage":"oops,cannot activate your account"}
		
	
	
 
4.AddAttribute
	- Description: add a new user's attribute to active directory 
			See LDAP Name for each user's attibute in Active Directory here: https://www.manageengine.com/products/ad-manager/help/csv-import-management/active-directory-ldap-attributes.html		
	- access URL: https://localhost:7443/ActiveDirectoryWebAPI/AddAttribute
	- Required parameter variables: name,newattributename,newattributevalue
								
	- Send request method: GET or POST
		EX: name=test user & newattributename=description & newattributevalue= this is an user
	
	- Reponse is returned as JSON object:
	
		- success: {"Status":"success","SuccessMessage":your new displayName is already added"} 
		
		- fail: {"Status":"fail","ErrorMessage":"oops,your new displayName cannot be added"}
		
5.GetAnUserAttributes
	- Description: retrieve all the user's attributes which have values set in active directory 
			See LDAP Name for each user's attibute in Active Directory here: https://www.manageengine.com/products/ad-manager/help/csv-import-management/active-directory-ldap-attributes.html		
	- access URL: https://localhost:7443/ActiveDirectoryWebAPI/GetAnUserAttributes
	- Required parameter variables: name
								
	- Send request method: GET or POST
						EX: name=test user
	
	- Reponse is returned as JSON object:
	
		- success: {"Status":"success","objectCategory":"CN=Person,CN=Schema,CN=Configuration,DC=AD-portal,DC=local","email":"test@gmail.com",
					"memberOf":"patient,doctor","group":"patient,doctor","instanceType":"4","state":"Bangkok"
					,"objectClass":"toppersonorganizationalPersonuser","name":"test user","lastname":"user"
					,"telephoneNumber":"345874587","userAccountControl":"512","primaryGroupID":"513"
					,"postalCode":"74635","physicalDeliveryOfficeName":"ICT","country":"Thailand"
					,"cn":"test user","mobile":"783748736","sAMAccountType":"805306368","firstname":"test"
					,"displayName":"test user","userPrincipalName":"test@AD-portal.local","streetAddress":"salaya soi 11"
					,"countryCode":"764","city":"Nakhom pathom","fist_twoletters_of_countryname":"TH"
					,"distinguishedName":"CN=test user,OU=portal,DC=AD-portal,DC=local","username":"test","PoBox":"3847"} 
		
		- fail: {"Status":"fail","ErrorMessage":"oops,name does not exist"}
		
6.GetAttribute
	- Description: retrieve an user's attribute which has value in active directory 
			See LDAP Name for each user's attibute in Active Directory here: https://www.manageengine.com/products/ad-manager/help/csv-import-management/active-directory-ldap-attributes.html		
	- access URL: https://localhost:7443/ActiveDirectoryWebAPI/GetAttribute
	- Required parameter variables: username,attributename
								
	- Send request method: GET or POST
						EX: username=test & attributename=mail
	
	- Reponse is returned as JSON object:
	
		- success: {"Status":"success","email":"test@gmail.com"}
		- fail: {"Status":"fail","ErrorMessage":"user attribute has no value"}		
		

7.UpdateAttribute
	- Description: update value of an user's attribute which has value in active directory 
			See LDAP Name for each user's attibute in Active Directory here: https://www.manageengine.com/products/ad-manager/help/csv-import-management/active-directory-ldap-attributes.html		
	- access URL: https://localhost:7443/ActiveDirectoryWebAPI/UpdateAttribute
	- Required parameter variables: name,attributename,attributevalue
								
	- Send request method: GET or POST
						EX: name=test user & attributename=mail &attributevalue=test@email2.com
	
	- Reponse is returned as JSON object:
	
		- success: {"Status":"success","SuccessMessage":"your mail is already updated to test@email2.com"}
		- fail: {"Status":"fail","ErrorMessage":"your mail cannot be updated to test@email2.com"}		
		
8.UpdatePassword
	- Description: update user's password in active directory 
			See LDAP Name for each user's attibute in Active Directory here: https://www.manageengine.com/products/ad-manager/help/csv-import-management/active-directory-ldap-attributes.html		
	- access URL: https://localhost:7443/ActiveDirectoryWebAPI/UpdatePassword
	- Required parameter variables: name,username,password
								
	- Send request method: GET or POST
						EX: name=test user & username=test & password=dkjfkdfjHG123
	
	- Reponse is returned as JSON object:
	
		- success: {"Status":"success","SuccessMessage":"your account password is already updated"}
		- fail: {"Status":"fail","ErrorMessage":"oops,your account password cannot be updated "}		
		

9.UpdateName
	- Description: update user's name in active directory 
			See LDAP Name for each user's attibute in Active Directory here: https://www.manageengine.com/products/ad-manager/help/csv-import-management/active-directory-ldap-attributes.html		
	- access URL: https://localhost:7443/ActiveDirectoryWebAPI/UpdateName
	- Required parameter variables: name,newfname,newlname
								
	- Send request method: GET or POST
						EX: name=test user & newfname=test2 & newlname=user2
	
	- Reponse is returned as JSON object:
	
		- success: {"Status":"success","SuccessMessage":"your name is already updated }
		- fail: {"Status":"fail","ErrorMessage":"oops,your name cannot be updated "}		
		
		
10.UpdateUsername
	- Description: update user's username in active directory 
			See LDAP Name for each user's attibute in Active Directory here: https://www.manageengine.com/products/ad-manager/help/csv-import-management/active-directory-ldap-attributes.html		
	- access URL: https://localhost:7443/ActiveDirectoryWebAPI/UpdateUsername
	- Required parameter variables: name,newusername
								
	- Send request method: GET or POST
						EX: name=test user & newusername=testuser2
	
	- Reponse is returned as JSON object:
	
		- success: {"Status":"success","SuccessMessage":"your username is already updated"}
		- fail: {"Status":"fail","ErrorMessage":"oops,your username cannot be updated "}		

11.RemoveUserFromGroup
	- Description: remove user from a certain group in active directory 
			See LDAP Name for each user's attibute in Active Directory here: https://www.manageengine.com/products/ad-manager/help/csv-import-management/active-directory-ldap-attributes.html		
	- access URL: https://localhost:7443/ActiveDirectoryWebAPI/RemoveUserFromGroup
	- Required parameter variables: name,groupname
								
	- Send request method: GET or POST
						EX: name=test user & groupname=doctor
	
	- Reponse is returned as JSON object:
	
		- success: {"Status":"success","SuccessMessage":"user is already removed from doctor group"}
		- fail: {"Status":"fail","ErrorMessage":"oops,user cannot be removed from doctor group"}		

12.AddUserToGroup
	- Description: Add  a user to a certain group in active directory 
			See LDAP Name for each user's attibute in Active Directory here: https://www.manageengine.com/products/ad-manager/help/csv-import-management/active-directory-ldap-attributes.html		
	- access URL: https://localhost:7443/ActiveDirectoryWebAPI/AddUserToGroup
	- Required parameter variables:name,groupname
								
	- Send request method: GET or POST
						EX: name=testuser & groupname=doctor
	
	- Reponse is returned as JSON object:
	
		- success: {"Status":"success","SuccessMessage":"user is already member of the group"}
		- fail: {"Status":"fail","ErrorMessage":"cannot set user as member of the group"}		
		
13.DeleteAttribute
	- Description: Delete an user's attribute which has value in the active directory 
			See LDAP Name for each user's attibute in Active Directory here: https://www.manageengine.com/products/ad-manager/help/csv-import-management/active-directory-ldap-attributes.html		
	- access URL: https://localhost:7443/ActiveDirectoryWebAPI/DeleteAttribute
	- Required parameter variables:name,attributename,attributevalue
								
	- Send request method: GET or POST
						EX: name=test user & attributename=mail & attributevalue=testemail@gmail.com
	
	- Reponse is returned as JSON object:
	
		- success: {"Status":"success","SuccessMessage":"your mail with value:testemail@gmail.com is already removed"}
		- fail: {"Status":"fail","ErrorMessage":"oops,cannot remove mail with value:testemail@gmail.com"}		

14.DeleteUser
	- Description: Delete an user or account from active directory 
			See LDAP Name for each user's attibute in Active Directory here: https://www.manageengine.com/products/ad-manager/help/csv-import-management/active-directory-ldap-attributes.html		
	- access URL: https://localhost:7443/ActiveDirectoryWebAPI/DeleteUser
	- Required parameter variables:name
								
	- Send request method: GET or POST
						EX: name=test user 
	
	- Reponse is returned as JSON object:
	
		- success: {"Status":"success","SuccessMessage":"user is already deleted"}
		- fail: {"Status":"fail","ErrorMessage":"oops,user cannot be deleted"}		

 15.ForgetPassword
	- Description:Check user's name and username in active directory when user forgets password
	- access URL: https://localhost:7443/ActiveDirectoryWebAPI/ForgetPassword
	- Required parameter variables: name,username,email
	- Send request method: GET or POST
	
	- Reponse is returned as JSON object:
	
		- success: {"Status":"success","SuccessMessage":"valid user"} 
		
		- fail: {"Status":"fail","ErrorMessage":"invalid user"}
				

