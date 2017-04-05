
package local.healthportal.ConfigClass;

/**
 *
 * @author Win10
 */
public class AD_Admin_Config {
public final static String ADMIN_USERNAME ="vaz"; //Active Directory admin user name
public final static String ADMIN_PASSWORD ="Kotonurak123456"; //Active Directory admin password 
//public final static String DOMAIN_NAME ="AD-portal.local"; //Domain controller name
public final static String DOMAIN_NAME ="healthportal.local"; //Domain controller name
//public final static String SERVER_IP ="192.168.14.2"; //Domain controller server IP address
public final static String SERVER_IP ="192.168.255.10"; //Domain controller server IP address
public final static String OU = "portal"; //Organizational Unit in active directory which user will be added to
public final static String AD_NORMAL_PORT="389";//port that used to connect to Active Directory in normal mode or non SSL mode
public final static String AD_SSL_PORT="636"; //port that used to connect to Active Directory in SSL mode  

}
