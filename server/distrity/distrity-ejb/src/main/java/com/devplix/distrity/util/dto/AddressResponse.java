package com.app.distrity.util.dto;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/** FORMATO DE RESPUESTA
[
 {
     "place_id": 9189880,
     "licence": "Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright",
     "osm_type": "node",
     "osm_id": 944543985,
     "boundingbox": [
         "-34.9084742",
         "-34.9083742",
         "-56.186102",
         "-56.186002"
     ],
     "lat": "-34.9084242",
     "lon": "-56.186052",
     "display_name": "1366,1368,1370,1372, Canelones, Barrio Sur, Montevideo, 11110, Uruguay",
     "class": "place",
     "type": "house",
     "importance": 0.22100000000000003,
     "address": {
         "house_number": "1366,1368,1370,1372",
         "road": "Canelones",
         "suburb": "Barrio Sur",
         "city": "Montevideo",
         "state": "Montevideo",
         "postcode": "11110",
         "country": "Uruguay",
         "country_code": "uy",
         "village": ""
         
     },
     "geotext": "POINT(-56.186052 -34.9084242)"
 }
]
*/

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressResponse {
	
	@JsonProperty("place_id")
	private Long placeId;
	
	@JsonProperty("licence") 
	private String licence;
	
	@JsonProperty("osm_type") 
	private String osmType;
	
	@JsonProperty("osm_id") 
	private Long osmId;
	
	@JsonProperty("boundingbox") 
	private List<String> boundingbox = new ArrayList<String>();
	
	@JsonProperty("lat") 
	private String lat;
	
	@JsonProperty("lon") 
	private String lon;
	
	@JsonProperty("display_name")
	private String displayName;
	
	@JsonProperty("class") 
	private String classType;
	
	@JsonProperty("type") 
	private String type;
	
	@JsonProperty("importance") 
	private Double importance;
	
	@JsonProperty("geotext") 
	private String geotext;
	
	@JsonProperty("address") 
	private Address address;
	
	public AddressResponse() {}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getOsmType() {
		return osmType;
	}

	public void setOsmType(String osmType) {
		this.osmType = osmType;
	}

	public Long getOsmId() {
		return osmId;
	}

	public void setOsmId(Long osmId) {
		this.osmId = osmId;
	}

	public List<String> getBoundingbox() {
		return boundingbox;
	}

	public void setBoundingbox(List<String> boundingbox) {
		this.boundingbox = boundingbox;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getImportance() {
		return importance;
	}

	public void setImportance(Double importance) {
		this.importance = importance;
	}

	public String getGeotext() {
		return geotext;
	}

	public void setGeotext(String geotext) {
		this.geotext = geotext;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Address {
	
	@JsonProperty("house_number")
	private String houseNumber;
	
	@JsonProperty("road")
	private String road;
	
	@JsonProperty("neighbourhood")
	private String neighbourhood;
	
	@JsonProperty("suburb")
	private String suburb;
	
	@JsonProperty("city")
	private String city;
	
	@JsonProperty("state")
	private String state;
	
	@JsonProperty("postcode")
	private String postcode;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("country_code")
	private String countryCode;
	
	@JsonProperty("town")
	private String town;
	
	@JsonProperty("village")
	private String village;
	
	public Address() {}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getNeighbourhood() {
		return neighbourhood;
	}

	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}
	
}
