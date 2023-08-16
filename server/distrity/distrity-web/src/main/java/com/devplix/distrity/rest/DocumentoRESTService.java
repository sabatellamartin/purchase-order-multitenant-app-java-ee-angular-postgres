package com.app.distrity.rest;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.app.distrity.model.Documento;
import com.app.distrity.model.OrdenVenta;
import com.app.distrity.model.auth.Operador;
import com.app.distrity.model.auth.Usuario;
import com.app.distrity.service.iservice.IDocumentoService;
import com.app.distrity.service.iservice.IUsuarioService;
import com.app.distrity.util.filter.OrdenVentaFilter;
import com.app.distrity.util.paginator.PaginatorResponse;
import com.app.distrity.util.security.AuthenticatedUser;
import com.app.distrity.util.security.Role;
import com.app.distrity.util.security.Secured;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/documentos")
@RequestScoped
public class DocumentoRESTService {

    @Inject
    private IDocumentoService documentoService;
 
    @Inject
    @AuthenticatedUser
    private Usuario authenticatedUser;
    
	@Inject
	private IUsuarioService usuarioService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response getAll() {
        List<Documento> documentos = this.documentoService.getAll();
        if (documentos != null) {
            return Response.ok(documentos).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response getById(@PathParam("id") Long id) {
    	Documento documento = this.documentoService.getById(id);
        if (documento != null) {
            return Response.ok(documento).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
	
    @POST
    @Path("/add/ordenventa")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response addOrdenVenta(OrdenVenta ordenVenta) {
    	Operador usuario = (Operador) this.usuarioService.getById(this.authenticatedUser.getId());
    	Boolean result = this.documentoService.addOrdenVenta(ordenVenta, usuario);
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    
    @PUT
    @Path("/update/ordenventa/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.PROPIETARIO})
    public Response updateOrdenVenta(@PathParam("id") Long id, OrdenVenta ordenVenta) {
    	Boolean result = this.documentoService.updateOrdenVenta(ordenVenta);
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }    
    
    @GET
    @Path("/change/estado/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response changeEstadoById(@PathParam("id") Long id) {
    	Boolean result = this.documentoService.changeEstadoById(id);
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/cancelar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response cancelarToggle(@PathParam("id") Long id) {
    	Boolean result = this.documentoService.cancelarToggle(id);
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/pdf/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response ordenVentaToPdfById(@PathParam("id") Long id) {
    	ByteArrayOutputStream output = this.documentoService.ordenVentaToPdfById(id);
        if (output != null) {
            return Response.ok(output.toByteArray(), MediaType.APPLICATION_OCTET_STREAM)
            			   .header("Content-Disposition", "attachment; filename=documento.pdf" )
            			   .build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @POST
    @Path("/search/ordenventa/filter")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO})
    public Response searchOrdenVentaFilter(OrdenVentaFilter ordenVentaFilter) {
    	PaginatorResponse<OrdenVenta> documentos = this.documentoService.searchOrdenVenta(ordenVentaFilter);
        if (documentos != null) {
            return Response.ok(documentos).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @POST
    @Path("/search/ordenventa/filter/owner")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response searchOrdenVentaFilterOwner(OrdenVentaFilter ordenVentaFilter) {
    	Operador usuario = (Operador) this.usuarioService.getById(this.authenticatedUser.getId());
    	ordenVentaFilter.setOperadorId(usuario.getId());
    	PaginatorResponse<OrdenVenta> documentos = this.documentoService.searchOrdenVenta(ordenVentaFilter);
        if (documentos != null) {
            return Response.ok(documentos).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
}
