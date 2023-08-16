package com.app.distrity.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.app.distrity.model.auth.Operador;
import com.app.distrity.model.auth.Usuario;
import com.app.distrity.service.iservice.IUsuarioService;
import com.app.distrity.util.security.AuthenticatedUser;
import com.app.distrity.util.security.Role;
import com.app.distrity.util.security.Secured;
import com.app.distrity.util.tenant.TenantContext;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/usuarios")
@RequestScoped
public class UsuarioRESTService {

    @Inject
    private IUsuarioService usuarioService;
 
    @Inject
    @AuthenticatedUser
    private Usuario authenticatedUser;
    
    /* ADMINISTRADOR */
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR})
    public Response getAll() {
        List<Usuario> usuarios = usuarioService.getAll();
        if (usuarios != null) {
            return Response.ok(usuarios).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/ordered")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR})
    public Response getAllOrderedByUsername() {
        List<Usuario> usuarios = usuarioService.getAllOrderedByUsername();
        if (usuarios != null) {
            return Response.ok(usuarios).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR})
    public Response getById(@PathParam("id") Long id) {
    	Usuario usuario = null;
    	usuario = usuarioService.getById(id);
        if (usuario != null) {
            return Response.ok(usuario).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR})
    public Response getByEmail(@PathParam("email") String email) {
    	Usuario usuario = usuarioService.getByEmail(email);
		if (usuario != null) {
            return Response.ok(usuario).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
   	
    @GET
    @Path("/search/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR})
    public Response search(@PathParam("term") String term) {
    	List<Usuario> usuarios = usuarioService.search(term);
        if (usuarios != null) {
            return Response.ok(usuarios).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
   
   	@GET
    @Path("/search/rol/{term}/{rol}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR})
    public Response searchByRol(@PathParam("term") String term, @PathParam("rol") String rol) {
    	List<Usuario> usuarios = usuarioService.searchByRol(term, rol);
        if (usuarios != null) {
            return Response.ok(usuarios).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
   	
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR})
    public Response remove(@PathParam("id") Long id) {
    	Boolean result = usuarioService.removeById(id);
        if (result) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    /* ALL */
    
	@GET
	@Path("/exist/email/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured({ Role.ADMINISTRADOR, Role.PROPIETARIO})
	public Response existByEmail(@PathParam("email") String email) {
		Boolean result = usuarioService.existByEmail(email);
		if (result != null) {
			return Response.ok(result).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
    /* PROPIETARIO */
    
   	@GET
    @Path("/search/operadores/{rol}/{term}")
    @Produces(MediaType.APPLICATION_JSON)
   	@Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response searchOperadoresByRol(@PathParam("rol") String rol, @PathParam("term") String term) {
    	List<Operador> usuarios = usuarioService.searchOperadoresByRol(term, rol, TenantContext.getCurrentTenant());
        if (usuarios != null) {
            return Response.ok(usuarios).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
   	
    @POST
    @Path("/add/operador")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response addOperador(Operador usuario) {
    	Boolean result = usuarioService.addOperador(usuario, TenantContext.getCurrentTenant());
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/update/operador/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response updateOperador(@PathParam("id") Long id, Operador usuario) {
    	Boolean result = usuarioService.updateOperador(usuario, TenantContext.getCurrentTenant());
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
   	
    @DELETE
    @Path("/operador/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response removeOperador(@PathParam("id") Long id) {
    	Boolean result = usuarioService.removeOperador(id, TenantContext.getCurrentTenant());
        if (result) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
	
	@GET
	@Path("/lock/operador/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured({ Role.ADMINISTRADOR, Role.PROPIETARIO})
	public Response toggleLockOperador(@PathParam("id") Long id) {
		Boolean result = usuarioService.toggleLockOperadorById(id, TenantContext.getCurrentTenant());
		if (result != null) {
			return Response.ok(result).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
    @GET
    @Path("/operador/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response getOperadorById(@PathParam("id") Long id) {
    	Operador usuario = usuarioService.getOperadorById(id, TenantContext.getCurrentTenant());
        if (usuario != null) {
            return Response.ok(usuario).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/email/operador/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response getClientByEmail(@PathParam("email") String email) {
    	Operador usuario = (Operador) usuarioService.getOperadorByEmail(email, TenantContext.getCurrentTenant());
		if (usuario != null) {
            return Response.ok(usuario).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
	@GET
	@Path("/toggle/baja/operador/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured({ Role.ADMINISTRADOR, Role.PROPIETARIO})
	public Response toggleBajaOperador(@PathParam("id") Long id) {
		Boolean result = usuarioService.toggleBajaOperadorById(id, TenantContext.getCurrentTenant());
		if (result != null) {
			return Response.ok(result).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("/restore/pass/operador/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured({ Role.ADMINISTRADOR, Role.PROPIETARIO})
	public Response restorePasswordOperador(@PathParam("id") Long id) {
		String result = usuarioService.restorePasswordOperador(id, TenantContext.getCurrentTenant());
		if (result != null) {
			return Response.ok(result).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("/change/pass/operador/{clearOld}/{clearNew}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured({ Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
	public Response changePasswordOperador(@PathParam("clearOld") String clearOld, @PathParam("clearNew") String clearNew) {
		Boolean result = usuarioService.changePasswordOperador(
				this.authenticatedUser.getId(), 
				TenantContext.getCurrentTenant(),
				clearOld,
				clearNew);
		if (result != null) {
			return Response.ok(result).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
}
