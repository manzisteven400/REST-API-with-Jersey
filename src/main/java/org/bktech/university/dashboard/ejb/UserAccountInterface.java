package org.bktech.university.dashboard.ejb;


import javax.ejb.Remote;
import org.bktech.university.dashboard.providers.UnAuthorizedExceptionMapper;
import org.bktech.university.dashboard.providers.UserRequestExceptionMapper;
import org.bktech.university.dashboard.models.UserAccount;
import org.bktech.university.dashboard.models.ThirdPartyAuthentication;



@Remote
public interface UserAccountInterface {
	
	public UserAccount validateUserAccount(String username, String password);
	public ThirdPartyAuthentication validateThirdPartyCredentials(String username, String password);
    

}
