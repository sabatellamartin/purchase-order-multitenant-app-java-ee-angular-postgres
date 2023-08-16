import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions, Response } from '@angular/http';

import { AddressResponse } from '../models/map/address-response';

@Injectable()
export class MapService {

  private url: string;

  public appEndpoint: string;
  public geoServerEndpoint: string;
  public imgPath: string;
  public iconPath: string;

  constructor(private http : Http) {
    this.imgPath = "../../../../assets/images/";
    this.iconPath = "../../../../assets/icons/";
    this.appEndpoint = 'http://localhost/distrity-web/rest';
    this.geoServerEndpoint = 'http://www.localhost:8600/geoserver';

    this.url = `${this.appEndpoint}/shared`;
  }

  private getHeaders(): Headers {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('current_sesion')).token);
    return headers;
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  public searchAddress(query: string): Promise<AddressResponse[]> {
    const url = `${this.url}/search/address/${query}`;
    return this.http.get(url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as AddressResponse[])
               .catch(this.handleError);
  }

  public reverseAddress(lon: string, lat: string): Promise<AddressResponse> {
    const url = `${this.url}/reverse/address/${lon}/${lat}`;
    return this.http.get(url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as AddressResponse)
               .catch(this.handleError);
  }

}
