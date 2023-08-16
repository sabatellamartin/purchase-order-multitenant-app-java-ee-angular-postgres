package com.app.distrity.util;

import java.net.MalformedURLException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;

/**
 * Reference 
 * 
 * https://github.com/geosolutions-it/geoserver-manager/wiki/Various-Examples
 * 
 * geosolutions-it/geoserver-manager
 * 
 */

@Startup
@Singleton
public class GeoServerClient {

    private GeoServerRESTReader geoReader = null;
    private GeoServerRESTPublisher geoPublisher = null;

	@PostConstruct
	void init() {
		if (geoReader == null) {
			try {
				geoReader = new GeoServerRESTReader(
						Constants.GEOSERVER_ENDPOINT, 
						Constants.GEOSERVER_USER, 
						Constants.GEOSERVER_PASS);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		if (geoPublisher == null) {
			geoPublisher = new GeoServerRESTPublisher(
					Constants.GEOSERVER_ENDPOINT, 
					Constants.GEOSERVER_USER, 
					Constants.GEOSERVER_PASS);
		}
	}
	
	public GeoServerRESTReader getGeoserverReader() {
		return geoReader;
	}
	
	public GeoServerRESTPublisher getGeoserverPublisher() {
		return geoPublisher;
	}
	
}