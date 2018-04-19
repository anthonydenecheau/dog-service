package com.scc.dog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scc.dog.config.AuthenticateConfig;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserContextFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);

    @Autowired
    AuthenticateConfig authenticate;

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
	
	        if (authenticate(authCredentials,httpServletRequest.getRequestURL().toString())) {
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
    
    private boolean authenticate(String authCredentials, String url) {
    	
    	// TODO: migrer la config fichier properties vers DAO
    	// Aujourd'hui, une clé valide l'utilisation de l'API qque soit la version, et il n'existe qu'une date de fin de validité pour l'ensemble des clés
		Boolean ok = false;

		if (null == authCredentials)
			return ok;

		// la clé transmise est-elle reconnue ?
		// On conserve la position de la clé dans le tableau 
		int position = 0;
		for (String _key : authenticate.getKeys()) {
			position++;
			if (_key.equals(authCredentials)) {
				ok = true;
				break;
			}
		}

		if(!ok) {
			return false;
		}
		
		// Recherche du couple key/version API
		Pattern pattern = Pattern.compile("/v(.*?)/");
		Matcher matcher = pattern.matcher(url);
		if (matcher.find())
		{
//			System.out.println(matcher.group(1));
			switch(matcher.group(1)) {
				case "1":
				    // Agria = cle + v1 uniquement
					if (position!=1) { ok = false; }
					break;
				case "2":
					// Ods/Enci = cle + v2 uniquement
					if (position==1) { ok = false; }
					break;
				default:
					ok = false;
					break;
			}
		}

		if(!ok) {
			return false;
		}
		
		ok = false;
		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		// la clé est-elle toujours active ?
		String dateLimiteString = authenticate.getValue();
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