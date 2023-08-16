package com.app.distrity.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.app.distrity.service.iservice.IGisService;
import com.app.distrity.util.dto.AddressResponse;
import com.app.distrity.util.security.Role;
import com.app.distrity.util.security.Secured;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/shared")
@RequestScoped
public class SharedRESTService {

    @Inject
    private IGisService gisService;
	
	@GET
    @Path("/search/address/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response searchAddress(@PathParam("query") String query) {
		List<AddressResponse> response = this.gisService.searchAddress(query);
        if (response != null) {
            return Response.ok(response).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
	
	@GET
    @Path("/reverse/address/{lon}/{lat}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.ADMINISTRADOR, Role.PROPIETARIO, Role.VENTAS})
    public Response reverseAddress(@PathParam("lon") String lon, @PathParam("lat") String lat) {
		AddressResponse response = this.gisService.reverseAddress(lon, lat);
        if (response != null) {
            return Response.ok(response).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
	
}
