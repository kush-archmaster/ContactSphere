package com.contact.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.contact.constants.ContactSphereConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		 for(GrantedAuthority auth : authentication.getAuthorities()) {
			 if (auth.getAuthority().contains(ContactSphereConstants.AUTH_ADMIN)) {
                 response.sendRedirect("/admin/dashboard"); // Redirect to admin dashboard for users with ADMIN role
                 return;
             } else if (auth.getAuthority().contains(ContactSphereConstants.AUTH_USER)) {
                 response.sendRedirect("/user/dashboard"); // Redirect to user dashboard for users with USER role
                 return;
             }
		}
		// default view for users without any role defined
		response.sendRedirect("/dashboard");
	}

}
