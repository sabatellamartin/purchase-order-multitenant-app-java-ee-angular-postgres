package com.app.distrity.service.iservice;

import java.util.List;

import com.app.distrity.util.dto.AddressResponse;
import com.vividsolutions.jts.io.ParseException;

public interface IGisService {

	public String parseWkbToWkt(String wkbString) throws ParseException;

	public Long savePoint(Double longitud, Double latitud, String tenant);
	
	public Long updatePoint(Long featureId, Double longitud, Double latitud, String tenant);

	public Boolean deletePoint(Long featureId, String tenant);
	
	public List<AddressResponse> searchAddress(String query);

	public AddressResponse reverseAddress(String lon, String lat);

	public Double wkbStringToCoord(String eje, String wkbString);
	
	public String getFeatureById(Long featureId);
	
}
