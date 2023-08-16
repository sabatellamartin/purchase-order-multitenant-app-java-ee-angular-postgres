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

import com.app.distrity.model.TipoEmpresa;
import com.app.distrity.service.iservice.ITipoEmpresaService;
import com.app.distrity.util.security.Role;
import com.app.distrity.util.security.Secured;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/tipo/empresas")
@RequestScoped
public class TipoEmpresaRESTService {

    @Inject
    private ITipoEmpresaService tipoEmpresaService;
 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response getAll() {
        List<TipoEmpresa> tipoEmpresas = this.tipoEmpresaService.getAll();
        if (tipoEmpresas != null) {
            return Response.ok(tipoEmpresas).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response getById(@PathParam("id") Long id) {
    	TipoEmpresa tipoEmpresa = this.tipoEmpresaService.getById(id);
        if (tipoEmpresa != null) {
            return Response.ok(tipoEmpresa).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/search/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response search(@PathParam("term") String term) {
    	List<TipoEmpresa> tipoEmpresas = this.tipoEmpresaService.search(term);
        if (tipoEmpresas != null) {
            return Response.ok(tipoEmpresas).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response add(TipoEmpresa tipoEmpresa) {
    	Boolean result = this.tipoEmpresaService.add(tipoEmpresa);
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response update(@PathParam("id") Long id, TipoEmpresa tipoEmpresa) {
    	Boolean result = this.tipoEmpresaService.update(tipoEmpresa);
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }  
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response remove(@PathParam("id") Long id) {
    	Boolean result = this.tipoEmpresaService.removeById(id);
        if (result) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
	@GET
    @Path("/sigla/{sigla}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR,Role.PROPIETARIO})
    public Response getBySigla(@PathParam("sigla") String sigla) {
    	TipoEmpresa tipoEmpresa = this.tipoEmpresaService.getBySigla(sigla);
        if (tipoEmpresa != null) {
            return Response.ok(tipoEmpresa).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

}
