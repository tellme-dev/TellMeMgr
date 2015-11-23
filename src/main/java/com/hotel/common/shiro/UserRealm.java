package com.hotel.common.shiro;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.Sha256CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.hotel.common.utils.Constants;
import com.hotel.service.UserService;


public class UserRealm extends AuthorizingRealm {
	private static Logger logger =Logger.getLogger(UserRealm.class);  
	
//	@Resource(name="userService")
	private UserService userService;

	public void setUserService(UserService userService) {
        this.userService = userService;
    }
	
	 public UserRealm() {
	        super.setName(Constants.THE_REALM_NAME); 
	        super.setCredentialsMatcher(new Sha256CredentialsMatcher());
	 }

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
	   System.out.println(" 由于加入了缓存, 此处只会load一次：doGetAuthorizationInfo.................");
	   String username = (String)principals.getPrimaryPrincipal();

       SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//       authorizationInfo.setRoles(userService.findRoles(username));
//       authorizationInfo.setStringPermissions(userService.findPermissions(username));

       return authorizationInfo;
	}

	
	/**
	 * AuthenticationInfo represents a Subject's (aka user's) stored account information 
	 * relevant to the authentication/log-in process only. 
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
			SimpleAuthenticationInfo authenticationInfo = null;
			ShiroUsernamePasswordToken usernamePasswordToke =(ShiroUsernamePasswordToken)token;
			String username = usernamePasswordToke.getUsername();
			  if( username != null && !"".equals(username) ){
					  authenticationInfo = new SimpleAuthenticationInfo(username, usernamePasswordToke.getBase64Password(), getName());
		              authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username+usernamePasswordToke.getSalt())); 
			  }
	        return authenticationInfo;
	}
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
	
}
