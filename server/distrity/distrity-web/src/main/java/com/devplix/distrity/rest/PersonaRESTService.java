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

import com.app.distrity.model.Consumidor;
import com.app.distrity.model.Empleado;
import com.app.distrity.model.Persona;
import com.app.distrity.model.Referente;
import com.app.distrity.service.iservice.IPersonaService;
import com.app.distrity.util.Constants;
import com.app.distrity.util.security.Role;
import com.app.distrity.util.security.Secured;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/personas")
@RequestScoped
public class PersonaRESTService {

    @Inject
    private IPersonaService personaService;
 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response getAll() {
        List<Persona> personas = this.personaService.getAll();
        if (personas != null) {
            return Response.ok(personas).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response getById(@PathParam("id") Long id) {
    	Persona persona = this.personaService.getById(id);
        if (persona != null) {
            return Response.ok(persona).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/search/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response search(@PathParam("term") String term) {
    	List<Persona> personas = this.personaService.search(term);
        if (personas != null) {
            return Response.ok(personas).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response remove(@PathParam("id") Long id) {
    	Boolean result = this.personaService.removeById(id);
        if (result) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }  
    
	@GET
    @Path("/documento/{numero}/{tipoDocumentoId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response getByCodigo(@PathParam("numero") String numero, Long tipoDocumentoId) {
    	Persona persona = this.personaService.getByDocumento(numero, tipoDocumentoId);
        if (persona != null) {
            return Response.ok(persona).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

	@GET
    @Path("/search/{term}/tipo/{tipo}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response searchByTipo(@PathParam("term") String term, @PathParam("tipo") String tipo) {
    	List<Persona> personas = personaService.searchByTipo(term, tipo);
        if (personas != null) {
            return Response.ok(personas).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
	
    @POST
    @Path("/add/consumidor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response addConsumidor(Consumidor persona) {
    	Boolean result = personaService.add(persona);
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/update/consumidor/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response updateConsumidor(@PathParam("id") Long id, Consumidor persona) {
    	Boolean result = personaService.update(persona);
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
   
    @POST
    @Path("/add/empleado")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response addEmpleado(Empleado persona) {
    	Boolean result = personaService.add(persona);
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/update/empleado/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response updateEmpleado(@PathParam("id") Long id, Empleado persona) {
    	Boolean result = personaService.update(persona);
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/add/referente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response addReferente(Referente persona) {
    	Boolean result = personaService.add(persona);
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/update/referente/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response updateReferente(@PathParam("id") Long id, Referente persona) {
    	Boolean result = personaService.update(persona);
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }   
	
	@GET
    @Path("/search/referente/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response searchReferenteByTerm(@PathParam("term") String term) {
    	List<Persona> personas = personaService.searchByTipo(term, Constants.REFERENTE);
        if (personas != null) {
            return Response.ok(personas).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

	@GET
    @Path("/search/consumidor/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response searchConsumidorByTerm(@PathParam("term") String term) {
    	List<Persona> personas = personaService.searchByTipo(term, Constants.CONSUMIDOR);
        if (personas != null) {
            return Response.ok(personas).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

	@GET
    @Path("/search/empleado/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response searchEmpleadoByTerm(@PathParam("term") String term) {
    	List<Persona> personas = personaService.searchByTipo(term, Constants.EMPLEADO);
        if (personas != null) {
            return Response.ok(personas).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
	
}
