package com.app.distrity.util.security;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.app.distrity.model.auth.Usuario;
import com.app.distrity.service.iservice.IUsuarioService;

@RequestScoped
public class AuthenticatedUserProducer {

    @Produces
    @RequestScoped
    @AuthenticatedUser
    private Usuario authenticatedUser;

	@Inject
    private IUsuarioService usuarioService;
	
    public void handleAuthenticationEvent(@Observes @AuthenticatedUser String username) {
        this.authenticatedUser = this.findUser(username);
    }

    private Usuario findUser(String username) {
        // Hit the the database or a service to find a user by its username and return it
        // Return the User instance
    	return usuarioService.getByEmail(username);
    }
}