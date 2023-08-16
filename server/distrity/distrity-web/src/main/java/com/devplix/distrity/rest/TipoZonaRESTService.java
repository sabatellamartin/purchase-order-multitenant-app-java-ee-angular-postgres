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

import com.app.distrity.model.TipoZona;
import com.app.distrity.service.iservice.ITipoZonaService;
import com.app.distrity.util.security.Role;
import com.app.distrity.util.security.Secured;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/tipo/zonas")
@RequestScoped
public class TipoZonaRESTService {

    @Inject
    private ITipoZonaService tipoZonaService;
 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response getAll() {
        List<TipoZona> tipoZonas = this.tipoZonaService.getAll();
        if (tipoZonas != null) {
            return Response.ok(tipoZonas).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response getById(@PathParam("id") Long id) {
    	TipoZona tipoZona = this.tipoZonaService.getById(id);
        if (tipoZona != null) {
            return Response.ok(tipoZona).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/search/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response search(@PathParam("term") String term) {
    	List<TipoZona> tipoZonas = this.tipoZonaService.search(term);
        if (tipoZonas != null) {
            return Response.ok(tipoZonas).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response add(TipoZona tipoZona) {
    	Boolean result = this.tipoZonaService.add(tipoZona);
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
    public Response update(@PathParam("id") Long id, TipoZona tipoZona) {
    	Boolean result = this.tipoZonaService.update(tipoZona);
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
    	Boolean result = this.tipoZonaService.removeById(id);
        if (result) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
	@GET
    @Path("/codigo/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR,Role.PROPIETARIO})
    public Response getByCodigo(@PathParam("codigo") String codigo) {
    	TipoZona tipoZona = this.tipoZonaService.getByCodigo(codigo);
        if (tipoZona != null) {
            return Response.ok(tipoZona).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

}
