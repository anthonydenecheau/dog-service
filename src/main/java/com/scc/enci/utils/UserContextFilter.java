package com.scc.enci.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scc.enci.config.ServiceConfig;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UserContextFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);

    @Autowired
    ServiceConfig config;
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {


        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        UserContextHolder.getContext().setAuthentificationKey( httpServletRequest.getHeader(UserContext.AUTHENTICATION_KEY) );

    	// Swagger Authentification disabled
    	if (httpServletRequest.getRequestURL().toString().indexOf("api-docs")>0
    		 || httpServletRequest.getRequestURL().toString().indexOf("swagger")>0) {
    		filterChain.doFilter(httpServletRequest, servletResponse);
    	} else {
	        logger.debug("Incoming Authentification key: {}", UserContextHolder.getContext().getAuthentificationKey());
	        String authCredentials = UserContextHolder.getContext().getAuthentificationKey();
	
	        if (authenticate(authCredentials)) {
	        	filterChain.doFilter(httpServletRequest, servletResponse);
	        } else {
				if (servletResponse instanceof HttpServletResponse) {
					HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
					httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					logger.error("Erreur d'authentification, clef fournie: {}", authCredentials);
				}
			}
    	}                
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
    
    private boolean authenticate(String authCredentials) {
		if (null == authCredentials)
			return false;
		if (!config.getAuthKey().equals(authCredentials))
			return false;
		Boolean ok = false;

		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		String dateLimiteString = config.getAuthValue();
		if (dateLimiteString != null) {
			Date dateLimite = null;
			try {
				dateLimite = formatter.parse(dateLimiteString);

				if (dateLimite.after(today)) {
					ok = true;
				}
			} catch (ParseException e) {
				logger.error("Le format de la date associé à l'identifiant {} n'est pas au format valide (dd/MM/aaaa)",authCredentials);
			}
		}

		return ok;

	}        
}