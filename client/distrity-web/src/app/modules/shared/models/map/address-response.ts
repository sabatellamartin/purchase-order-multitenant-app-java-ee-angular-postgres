
export class AddressResponse {
  place_id: number;
  licence: string;
	osm_type: string;
	osm_id: number;
	boundingbox: Array<string>;
	lat: string;
	lon: string;
	display_name: string;
  class: string;
	type: string;
	importance: number;
  geotext: string;
  address: Address;
  constructor() {
    this.boundingbox = new Array<string>();
  }
}

class Address {
  house_number: string;
	road: string;
	suburb: string;
  town: string;
	city: string;
	state: string;
  postcode: string;
	country: string;
  country_code: string;
}
