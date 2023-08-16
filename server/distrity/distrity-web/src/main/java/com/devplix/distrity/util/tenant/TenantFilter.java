package com.app.distrity.util.tenant;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import com.app.distrity.model.auth.Operador;
import com.app.distrity.model.auth.Usuario;
import com.app.distrity.model.tenant.Tenant;
import com.app.distrity.service.iservice.IUsuarioService;
import com.app.distrity.util.Constants;
import com.app.distrity.util.security.AuthenticatedUser;
import com.app.distrity.util.security.Secured;

@Secured
@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class TenantFilter implements ContainerRequestFilter {
	
    @Inject
    @AuthenticatedUser
    private Usuario authenticatedUser;
    
	@Inject
    private IUsuarioService usuarioService;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
    	if (TenantContext.getCurrentTenant()== null || TenantContext.getCurrentTenant().isEmpty()) {
    		String tenantHeader = requestContext.getHeaderString(Constants.TENANT_HEADER);
    		TenantContext.connectTenant(tenantHeader);
        } else {
        	Usuario usuario = usuarioService.getByEmail(authenticatedUser.getEmail());
			if (usuario instanceof Operador) {
				Tenant tenant = ((Operador) usuario).getTenant();
				TenantContext.connectTenant(tenant.getNombre());
				System.out.println("TENANT " + TenantContext.getCurrentTenant());
			}
        }
    }
	
}

