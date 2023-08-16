package com.app.distrity.util.security;

import java.io.IOException;

import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.app.distrity.model.auth.Usuario;
import com.app.distrity.service.iservice.IUsuarioService;
import com.app.distrity.util.Constants;
import com.app.distrity.util.tenant.TenantContext;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
	
	@Inject
    private IUsuarioService usuarioService;

    @Inject
    @AuthenticatedUser
    Event<String> userAuthenticatedEvent;
	
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        /** 
         * TOKEN 
         **/
    	String token = "" ; // Initialize variable token	
    	// Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }
     	/** 
         * VALIDATE 
         **/
    	// Extract the token from the HTTP Authorization header
        token = authorizationHeader.substring("Bearer".length()).trim();
        try {
            validate(token);
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
        
    }


	private void validate(String token) throws Exception {
		/** CONNECT TO MASTER TENANT **/
        TenantContext.connectTenant(Constants.MASTER_TENANT);
		// Check if it was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
    	Jws<Claims> claims = Jwts.parser().setSigningKey(Constants.TOKEN_KEY.getBytes("UTF-8")).parseClaimsJws(token);
    	String subject = claims.getBody().getSubject();
    	//String email = (String) claims.getBody().get("email");
    	Usuario usuario = usuarioService.getByEmail(subject);
    	assert subject.equals(usuario.getEmail());
    	System.out.println("AUTENTICADO " + usuario.getEmail()); /* MENSAJE CONSOLA */
    	// Save the userAuthenticateEvent
    	userAuthenticatedEvent.fire(usuario.getEmail());
    }
	
}

