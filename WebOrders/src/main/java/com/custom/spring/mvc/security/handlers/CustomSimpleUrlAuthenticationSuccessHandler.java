package com.custom.spring.mvc.security.handlers;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


/**
 * Redirect different types of users to different pages after login
 */
public class CustomSimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private static final Logger LOG = LogManager.getLogger("customLog");
	
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
      HttpServletResponse response, Authentication authentication)
      throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }
    
    protected void handle(
	        HttpServletRequest request,
	        HttpServletResponse response, 
	        Authentication authentication
	) throws IOException {
	    String targetUrl = determineTargetUrl(authentication);
	    if (response.isCommitted()) {
	        LOG.debug(
	                "Response has already been committed. Unable to redirect to "
	                        + targetUrl);
	        return;
	    }
	    redirectStrategy.sendRedirect(request, response, targetUrl);
	}
    
    protected String determineTargetUrl(final Authentication authentication) {
    	
	    Map<String, String> roleTargetUrlMap = new HashMap<>();
	    //role_ suffix is automatically added by Spring Security
	    roleTargetUrlMap.put("ROLE_USER", "/orders");
	    roleTargetUrlMap.put("ROLE_ADMIN", "/admin");
	    final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	    for (final GrantedAuthority grantedAuthority : authorities) {
	        String authorityName = grantedAuthority.getAuthority();
	        if (roleTargetUrlMap.containsKey(authorityName)) {
	            return roleTargetUrlMap.get(authorityName);
	        }
	    }
	    throw new IllegalStateException();
	}
    
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
	    HttpSession session = request.getSession(false);
	    if (session == null) {
	        return;
	    }
	    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
