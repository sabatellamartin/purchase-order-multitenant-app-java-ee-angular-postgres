/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distrity for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.app.distrity.rest;

import java.io.UnsupportedEncodingException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.app.distrity.model.auth.Usuario;
import com.app.distrity.service.iservice.IUsuarioService;
import com.app.distrity.util.Constants;
import com.app.distrity.util.dto.Sesion;
import com.app.distrity.util.security.AuthenticatedUser;
import com.app.distrity.util.security.Credentials;
import com.app.distrity.util.security.Role;
import com.app.distrity.util.security.Secured;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("/authentication")
@RequestScoped
public class AuthenticationRESTService {
    
    @Inject
    private IUsuarioService usuarioService;
    
    @Inject
    @AuthenticatedUser
    private Usuario authenticatedUser;

	@GET
    @Path("/current")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.ADMINISTRACION, Role.VENTAS})
    public Response getCurrent() {
    	Usuario usuario = usuarioService.getByEmail(authenticatedUser.getEmail());
    	usuario.setPassword("");
        return Response.ok(usuario).build();
    }
	
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response authenticateUser(Credentials credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();
    	try {
            // Authenticate the user using the credentials provided
            Boolean result = authenticate(username, password);
            if (result) {
            	Usuario usuario = this.usuarioService.getByEmail(username);
            	String rol = usuarioService.getRolByEmail(usuario.getEmail());
            	Long expiration = (System.currentTimeMillis() / 1000L) + 60*60*24; /* Sumo 24 horas en segundos */ 
            	String token = this.issueToken(usuario, rol, expiration); /* Return the token on the response */
            	Sesion sesion = new Sesion();
            	sesion.setUsuarioId(usuario.getId());
            	sesion.setUsername(usuario.getUsername());
            	sesion.setEmail(usuario.getEmail());
            	sesion.setRol(rol);
            	sesion.setToken(token);
            	sesion.setTokenExpiration(expiration);
            	return Response.ok(sesion).build();
            } else {
            	return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    private Boolean authenticate(String username, String password) throws Exception {
    	Boolean result = true;
    	// Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
    	if (!usuarioService.login(username, password)) {
    		//throw new IllegalArgumentException("Invalid credentials");
    		result = false;
    	}
    	return result;
    }

    // Issue a token for the user
    private String issueToken(Usuario usuario, String rol, Long expiration) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
    	// We need a signing key, so we'll create one just for this example. Usually
    	// the key would be read from your application configuration instead.
    	String token = "";
    	try {
			token = Jwts.builder()
					.setSubject(usuario.getEmail())
					//.setExpiration(new Date(exp))
					.claim("username", usuario.getEmail())
					.claim("email", usuario.getEmail())
					.claim("scope", rol)
					.signWith(
						SignatureAlgorithm.HS256,
						Constants.TOKEN_KEY.getBytes("UTF-8")
					).compact();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return token;
    }

}

