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

import com.app.distrity.model.Categoria;
import com.app.distrity.service.iservice.ICategoriaService;
import com.app.distrity.util.security.Role;
import com.app.distrity.util.security.Secured;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/categorias")
@RequestScoped
public class CategoriaRESTService {

    @Inject
    private ICategoriaService categoriaService;
 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response getAll() {
        List<Categoria> categorias = this.categoriaService.getAll();
        if (categorias != null) {
            return Response.ok(categorias).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response getById(@PathParam("id") Long id) {
    	Categoria categoria = this.categoriaService.getById(id);
        if (categoria != null) {
            return Response.ok(categoria).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/search/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response search(@PathParam("term") String term) {
    	List<Categoria> categorias = this.categoriaService.search(term);
        if (categorias != null) {
            return Response.ok(categorias).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response add(Categoria categoria) {
    	Boolean result = this.categoriaService.add(categoria);
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
    public Response update(@PathParam("id") Long id, Categoria categoria) {
    	Boolean result = this.categoriaService.update(categoria);
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
    	Boolean result = this.categoriaService.removeById(id);
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
    	Categoria categoria = this.categoriaService.getByNombre(nombre);
        if (categoria != null) {
            return Response.ok(categoria).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

}
