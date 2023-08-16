package com.app.distrity.data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.MediaType;

import com.app.distrity.data.idata.IGisRepository;
import com.app.distrity.model.Feature;
import com.app.distrity.util.Constants;
import com.app.distrity.util.GeoServerClient;
import com.app.distrity.util.JerseyClient;
import com.app.distrity.util.dto.AddressResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

@Stateless
public class GisRepository implements IGisRepository {
	
	@PersistenceContext(unitName = Constants.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	@Inject
	private JerseyClient jerseyClient;
	
	@Inject
	private GeoServerClient geoserverClient;
	
	public GisRepository() {}

	@Override
	public String save(String wfs) {
		Client client = this.jerseyClient.getJerseyClient();
		String url = Constants.GEOSERVER_ENDPOINT + "/" + Constants.GEOSERVER_WORKSPACE + "/ows";
		WebResource webResource = client.resource(url);
		String response = webResource
		    .header("Content-Type", MediaType.TEXT_XML)
		    .post(String.class, wfs);	
		return response;
	}

	@Override
	public Boolean existDataStore(String workspace, String store) {
		return this.geoserverClient.getGeoserverReader().existsDatastore(workspace, store);
	}

	@Override
	public List<AddressResponse> searchAddress(String query) {
		try {
		    query = URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException ignored) {
		    // Can be safely ignored because UTF-8 is always supported
		}
		String url = Constants.OSM_NOMINATIM_ENDPOINT 
				+ "/search?q=" + query
				+ "&countrycodes=uy"
				+ "&format=json"
				+ "&polygon_text=1"
				+ "&limit=10"
				+ "&addressdetails=1";
		System.out.println(url);
		Client client = this.jerseyClient.getJerseyClient();
		WebResource webResource = client.resource(url);
		List<AddressResponse> addresses = webResource.get(new GenericType<List<AddressResponse>>(){});
		/*AddressResponse address = null;
	    if (!addresses.isEmpty() && addresses.size() > 0) {
	    	address = addresses.get(0);
	    }*/
		return addresses;
	}
	
	@Override
	public AddressResponse reverseAddress(String lon, String lat) {
		String url = Constants.OSM_NOMINATIM_ENDPOINT 
				+ "/reverse?format=json"
				+ "&lat=" + lat.trim() 
				+ "&lon=" + lon.trim()
				+ "&zoom=18"
				+ "&addressdetails=1"
				+ "&email=" + Constants.ACCOUNT_SEND_EMAIL;
				//+ "&polygon_text=1"
				//+ "&namedetails=1";
		System.out.println(url);
		Client client = this.jerseyClient.getJerseyClient();
		WebResource webResource = client.resource(url);
		AddressResponse address = webResource.get(AddressResponse.class);
		/*AddressResponse address = null;
	    if (!addresses.isEmpty() && addresses.size() > 0) {
	    	address = addresses.get(0);
	    }*/
		return address;
	}
	
	@Override
	public String getFeatureById(Long featureId) {
		/*Query q = em.createNativeQuery("SELECT f.geometry FROM features f WHERE f.id=:featureId");
		String wkbString = (String)q.setParameter("featureId", featureId).getSingleResult();
		*/
		String sql = "SELECT f.geometry FROM "+ Feature.class.getName() +" f WHERE f.id=:featureId";
		return (String)em.createQuery(sql).setParameter("featureId", featureId).getSingleResult();
	}
}
