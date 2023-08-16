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

import com.app.distrity.model.Impuesto;
import com.app.distrity.service.iservice.IImpuestoService;
import com.app.distrity.util.security.Role;
import com.app.distrity.util.security.Secured;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/impuestos")
@RequestScoped
public class ImpuestoRESTService {

    @Inject
    private IImpuestoService impuestoService;
 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response getAll() {
        List<Impuesto> impuestos = this.impuestoService.getAll();
        if (impuestos != null) {
            return Response.ok(impuestos).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response getById(@PathParam("id") Long id) {
    	Impuesto impuesto = this.impuestoService.getById(id);
        if (impuesto != null) {
            return Response.ok(impuesto).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/search/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response search(@PathParam("term") String term) {
    	List<Impuesto> impuestos = this.impuestoService.search(term);
        if (impuestos != null) {
            return Response.ok(impuestos).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response add(Impuesto impuesto) {
    	Boolean result = this.impuestoService.add(impuesto);
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
    public Response update(@PathParam("id") Long id, Impuesto impuesto) {
    	Boolean result = this.impuestoService.update(impuesto);
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
    	Boolean result = this.impuestoService.removeById(id);
        if (result) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
	@GET
    @Path("/nombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR,Role.PROPIETARIO})
    public Response getByNombre(@PathParam("nombre") String nombre) {
    	Impuesto impuesto = this.impuestoService.getByNombre(nombre);
        if (impuesto != null) {
            return Response.ok(impuesto).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

}
