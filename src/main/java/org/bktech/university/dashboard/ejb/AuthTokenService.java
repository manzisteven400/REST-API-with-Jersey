package org.bktech.university.dashboard.ejb;

import javax.ejb.Remote;
import org.bktech.university.dashboard.models.AuthToken;
import java.sql.Timestamp;

@Remote
public interface AuthTokenService {
	
	public void saveToken(AuthToken authToken);
	public AuthToken getTokenObject(String token);
	public int deleteToken(String previousDay);

}
