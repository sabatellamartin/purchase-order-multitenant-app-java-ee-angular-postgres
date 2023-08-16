package com.app.distrity.rest;

import java.io.InputStream;
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

import com.app.distrity.model.Articulo;
import com.app.distrity.service.iservice.IArticuloService;
import com.app.distrity.util.filter.ArticuloFilter;
import com.app.distrity.util.paginator.PaginatorResponse;
import com.app.distrity.util.security.Role;
import com.app.distrity.util.security.Secured;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/articulos")
@RequestScoped
public class ArticuloRESTService {

    @Inject
    private IArticuloService articuloService;
 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response getAll() {
        List<Articulo> articulos = this.articuloService.getAll();
        if (articulos != null) {
            return Response.ok(articulos).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response getById(@PathParam("id") Long id) {
    	Articulo articulo = this.articuloService.getById(id);
        if (articulo != null) {
            return Response.ok(articulo).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/search/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response search(@PathParam("term") String term) {
    	List<Articulo> articulos = this.articuloService.search(term);
        if (articulos != null) {
            return Response.ok(articulos).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response add(Articulo articulo) {
    	Boolean result = this.articuloService.add(articulo);
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
    public Response update(@PathParam("id") Long id, Articulo articulo) {
    	Boolean result = this.articuloService.update(articulo);
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
    	Boolean result = this.articuloService.removeById(id);
        if (result) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
	@GET
    @Path("/codigo/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR,Role.PROPIETARIO, Role.VENTAS})
    public Response getByCodigo(@PathParam("codigo") String codigo) {
    	Articulo articulo = this.articuloService.getByCodigo(codigo);
        if (articulo != null) {
            return Response.ok(articulo).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

	@POST
	@Path("/load/data/file")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
	public Response loadDataFile(InputStream incomingData) {
		List<Articulo> result = articuloService.loadFullData(incomingData);
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
	}
	
    @POST
    @Path("/search/filter")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response searchFilter(ArticuloFilter articuloFilter) {
    	PaginatorResponse<Articulo> articulos = this.articuloService.searchFilter(articuloFilter);
        if (articulos != null) {
            return Response.ok(articulos).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }	

}
