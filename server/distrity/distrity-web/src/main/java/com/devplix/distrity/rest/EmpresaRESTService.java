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

import com.app.distrity.model.Cliente;
import com.app.distrity.model.Distribuidor;
import com.app.distrity.model.Empresa;
import com.app.distrity.model.Proveedor;
import com.app.distrity.service.iservice.IEmpresaService;
import com.app.distrity.util.Constants;
import com.app.distrity.util.security.Role;
import com.app.distrity.util.security.Secured;
import com.app.distrity.util.tenant.TenantContext;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/empresas")
@RequestScoped
public class EmpresaRESTService {

    @Inject
    private IEmpresaService empresaService;
 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response getAll() {
        List<Empresa> empresas = this.empresaService.getAll();
        if (empresas != null) {
            return Response.ok(empresas).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response getById(@PathParam("id") Long id) {
    	Empresa empresa = this.empresaService.getById(id);
        if (empresa != null) {
            return Response.ok(empresa).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/search/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response search(@PathParam("term") String term) {
    	List<Empresa> empresas = this.empresaService.search(term);
        if (empresas != null) {
            return Response.ok(empresas).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response remove(@PathParam("id") Long id) {
    	Boolean result = this.empresaService.removeById(id, TenantContext.getCurrentTenant());
        if (result) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }  
    
	@GET
    @Path("/rut/{numero}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response getByRut(@PathParam("numero") String rut) {
    	Empresa empresa = this.empresaService.getByRut(rut);
        if (empresa != null) {
            return Response.ok(empresa).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

	@GET
    @Path("/search/{term}/tipo/{tipo}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response searchByTipo(@PathParam("term") String term, @PathParam("tipo") String tipo) {
    	List<Empresa> empresas = this.empresaService.searchByTipo(term, tipo);
        if (empresas != null) {
            return Response.ok(empresas).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
   
    @POST
    @Path("/add/proveedor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response addProveedor(Proveedor empresa) {
    	Boolean result = this.empresaService.add(empresa, TenantContext.getCurrentTenant());
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/add/cliente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response addCliente(Cliente empresa) {
    	Boolean result = empresaService.add(empresa, TenantContext.getCurrentTenant());
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @POST
    @Path("/add/distribuidor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response addDistribuidor(Distribuidor empresa) {
    	Boolean result = empresaService.add(empresa, TenantContext.getCurrentTenant());
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @PUT
    @Path("/update/proveedor/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response updateProveedor(@PathParam("id") Long id, Proveedor empresa) {
    	Boolean result = empresaService.update(empresa, TenantContext.getCurrentTenant());
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/update/cliente/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response updateCliente(@PathParam("id") Long id, Cliente empresa) {
    	Boolean result = empresaService.update(empresa, TenantContext.getCurrentTenant());
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @PUT
    @Path("/update/distribuidor/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response updateDistribuidor(@PathParam("id") Long id, Distribuidor empresa) {
    	Boolean result = empresaService.update(empresa, TenantContext.getCurrentTenant());
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
	
	@GET
    @Path("/search/cliente/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response searchClienteByTerm(@PathParam("term") String term) {
    	List<Empresa> empresas = this.empresaService.searchByTipo(term, Constants.CLIENTE);
        if (empresas != null) {
            return Response.ok(empresas).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
	
    @GET
    @Path("/distribuidor")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response getDistribuidor() {
    	Empresa empresa = this.empresaService.getDistribuidor();
        if (empresa != null) {
            return Response.ok(empresa).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
	
}
