package com.app.distrity.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.app.distrity.data.idata.IGisRepository;
import com.app.distrity.service.iservice.IGisService;
import com.app.distrity.util.Constants;
import com.app.distrity.util.dto.AddressResponse;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

@Stateless
public class GisService implements IGisService {

	@Inject
	private IGisRepository gisRepository;

	@Override
	public String parseWkbToWkt(String wkbString) throws ParseException {
		byte[] aux = WKBReader.hexToBytes(wkbString); // geometry in WKB format to be translated to WKT
		Geometry geom = new WKBReader().read(aux);
		String wktString = geom.toText();
		return wktString;
	}
	
	@Override
	public Long savePoint(Double longitud, Double latitud, String tenant) {
		String response = this.gisRepository.save(
				this.wfsInsertPointXML(
						String.valueOf(longitud), 
						String.valueOf(latitud), 
						tenant));
		return this.getFeatureIdByResponse(response);
	}
	
	@Override
	public Long updatePoint(Long featureId, Double longitud, Double latitud, String tenant) {
		String response = this.gisRepository.save(
				this.wfsUpdatePointXML(
						featureId, 
						String.valueOf(longitud), 
						String.valueOf(latitud),
						tenant));
		System.out.println(response);
		/*
		<?xml version="1.0" encoding="UTF-8"?>
		<wfs:TransactionResponse xmlns:xs="http://www.w3.org/2001/XMLSchema" 
		xmlns:wfs="http://www.opengis.net/wfs" 
		xmlns:gml="http://www.opengis.net/gml" xmlns:ogc="http://www.opengis.net/ogc" 
		xmlns:ows="http://www.opengis.net/ows" 
		xmlns:xlink="http://www.w3.org/1999/xlink" 
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="1.1.0" 
		xsi:schemaLocation="http://www.opengis.net/wfs 
		http://172.27.1.5:8600/geoserver/schemas/wfs/1.1.0/wfs.xsd">
		<wfs:TransactionSummary><wfs:totalInserted>0</wfs:totalInserted>
		<wfs:totalUpdated>1</wfs:totalUpdated>
		<wfs:totalDeleted>0</wfs:totalDeleted>
		</wfs:TransactionSummary><wfs:TransactionResults/><wfs:InsertResults>
		<wfs:Feature><ogc:FeatureId fid="none"/>
		</wfs:Feature></wfs:InsertResults>
		</wfs:TransactionResponse>
 		
 		<?xml version="1.0" encoding="UTF-8"?>
 		<wfs:TransactionResponse xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:wfs="http://www.opengis.net/wfs" xmlns:gml="http://www.opengis.net/gml" xmlns:ogc="http://www.opengis.net/ogc" xmlns:ows="http://www.opengis.net/ows" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="1.1.0" 
 		xsi:schemaLocation="http://www.opengis.net/wfs http://172.27.1.5:8600/geoserver/schemas/wfs/1.1.0/wfs.xsd">
 		<wfs:TransactionSummary>
 		<wfs:totalInserted>0</wfs:totalInserted><wfs:totalUpdated>1</wfs:totalUpdated>
 		<wfs:totalDeleted>0</wfs:totalDeleted></wfs:TransactionSummary><wfs:TransactionResults/>
 		<wfs:InsertResults><wfs:Feature><ogc:FeatureId fid="none"/>
 		</wfs:Feature></wfs:InsertResults></wfs:TransactionResponse>
		 */
		return 1L;//this.getFeatureIdByResponse(response);
	}
	
	@Override
	public Boolean deletePoint(Long featureId, String tenant) {
		String response = this.gisRepository.save(this.wfsDeletePointXML(featureId, tenant));
		return (this.getFeatureIdByResponse(response)>0);
	}	
	
	@Override
	public List<AddressResponse> searchAddress(String query) {
		return this.gisRepository.searchAddress(query);
	}

	@Override
	public AddressResponse reverseAddress(String longitud, String latitud) {
		return this.gisRepository.reverseAddress(longitud, latitud);
	}
	
	@Override
	public Double wkbStringToCoord(String eje, String wkbString) {
		byte[] aux = WKBReader.hexToBytes(wkbString);
		Geometry geom = null;
		Double result = null;
		try {
			geom = new WKBReader().read(aux);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			if(eje.compareToIgnoreCase("LON")==0) {
				result = (Double)geom.getCoordinate().x;
			} else {
				result = (Double)geom.getCoordinate().y;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
        return result;
	}
	
	private String wfsInsertPointXML(String longitud, String latitud, String tenant) {
		String wfst = "<Transaction xmlns='http://www.opengis.net/wfs' service='WFS' version='1.1.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd'>"
				+ "<Insert>"
				+ "<"+tenant+" xmlns='"+Constants.GEOSERVER_WORKSPACE+"'>"
				+ "<geometry>"
				+ "<Point xmlns='http://www.opengis.net/gml' srsName='"+Constants.GEOSERVER_SRS+"'>"
				+ "<pos srsDimension='2'>"+longitud+" "+latitud+"</pos>"
				+ "</Point>"
				+ "</geometry>"
				+ "</"+tenant+">"
				+ "</Insert>"
				+ "</Transaction>";
		return wfst;
	}
	
	private String wfsUpdatePointXML(Long id, String longitud, String latitud, String tenant) {
		String wfst = "<Transaction xmlns='http://www.opengis.net/wfs' service='WFS' version='1.1.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd'>"
				+ "<Update typeName='feature:"+tenant+"' xmlns:feature='"+Constants.GEOSERVER_WORKSPACE+"'>"
				+ "<Property>"
				+ "<Name>geometry</Name>"
				+ "<Value>"
				+ "<Point xmlns='http://www.opengis.net/gml' srsName='"+Constants.GEOSERVER_SRS+"'>"
				+ "<pos srsDimension='2'>"+longitud+" "+latitud+"</pos>"
				+ "</Point>"
				+ "</Value>"
				+ "</Property>"
				+ "<Filter xmlns='http://www.opengis.net/ogc'><FeatureId fid='"+id+"'/></Filter>"
				+ "</Update>"
				+ "</Transaction>";
		return wfst;
	}

	private String wfsDeletePointXML(Long id, String tenant) {
		String wfst = "<Transaction xmlns='http://www.opengis.net/wfs' service='WFS' version='1.1.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd'>"
				+ "<Delete typeName='feature:"+tenant+"' xmlns:feature='"+Constants.GEOSERVER_WORKSPACE+"'>"
				+ "<Filter xmlns='http://www.opengis.net/ogc'><FeatureId fid='"+id+"'/></Filter>"
				+ "</Delete>"
				+ "</Transaction>";
		return wfst;
	}
	
	private Long getFeatureIdByResponse(String response) {
		response = response.split("fid=\"")[1];
		response = response.split("\"/>")[0];
		response = response.split("\\.")[1];
		return Long.parseLong(response);
	}

	@Override
	public String getFeatureById(Long featureId) {
		return this.gisRepository.getFeatureById(featureId);
	}

}
