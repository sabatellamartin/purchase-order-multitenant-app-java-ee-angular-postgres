package com.app.distrity.data.idata;

import java.util.List;

import com.app.distrity.util.dto.AddressResponse;

public interface IGisRepository {

	public String save(String wfs);
	
	public Boolean existDataStore(String workspace, String store);

	public List<AddressResponse> searchAddress(String query);

	public AddressResponse reverseAddress(String lon, String lat);

	public String getFeatureById(Long featureId);

}
