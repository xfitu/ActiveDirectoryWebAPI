
package local.healthportal.ConfigClass;

/**
 *
 * @author Win10
 */
public class DomainController_Certificate_Config {
//it is that path to certificate of the domain controller where Active Directory exists on
 //the certificate contains the public key of the domain controller and has already been
 //imported into the JDK trust store cacerts and new certificate file with the extension JKS
 //is created at the time of certificate imported, it is a certificate file type which is 
 //specific and only recognized by JDK trust store in order to make SSL connection between 
 //the java web application and the Active Directory
 //public final static String DOMAIN_CONTROLLER_CERTIFICATE_PATH = "C:\\dccertificate.JKS";// Get the file of domain controller certificate          
 public final static String DOMAIN_CONTROLLER_CERTIFICATE_PATH = "C:\\healthportal.JKS";// Get the file of domain controller certificate          
     
}

