import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';

import { global } from '../../shared/models/global';
import { PaginatorResponse } from '../../shared/models/paginator-response';

// Models
import { Punto } from '../models/punto';

@Injectable()
export class GeoService {

  private headers = new Headers({'Content-Type': 'text/xml'}); // Set Headers to text/xml

  private workspace: string = "distrity";
  private locationLayer: string = "public";
  private zoneLayer: string = "zones";

  private url: string; // Endpoint
  private geoServerUrl: string; // URL to web api

  constructor(private http : Http) {
    this.geoServerUrl = `${global.geoServerEndpoint}`;
    if (global.production) {
      this.url = `${global.productionEndpoint}`;
    } else {
      this.url = `${global.developEndpoint}`;
    }
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  save(data: any): Promise<string> {
    const url = `${this.geoServerUrl}/${this.workspace}/ows`;
    return this.http
               .post(url, data, {headers: this.headers}).toPromise()
               .then(response => response.text() as string)
               .catch(this.handleError);
  }

  saveGeoserver(data: any): Promise<string> {
    const url = `${this.geoServerUrl}/${this.workspace}/ows`;
    return this.http
               .post(url, data, {headers: this.headers}).toPromise()
               .then(response => response.text() as string)
               .catch(this.handleError);
  }

  getLocationById(id: number): Promise<Punto> {
    const prefix: string = "locations";
    const url = `${this.geoServerUrl}/wfs?request=GetFeature&version=1.1.0&typeName=${this.workspace}:${this.locationLayer}&outputFormat=GML2&FEATUREID=${prefix}.${id}`;
    console.log(url);
    return this.http.get(url).toPromise().then(response => {
      console.log(response);
      let res = response.text().split('ts=" ">');
      res = res[1].split('</gml:coordinates>');
      res = res[0].split(',');
      let punto = new Punto();
      punto.longitud = Number(res[0]);
      punto.latitud = Number(res[1]);
      punto.id = id;
      return punto as Punto;
    }).catch(this.handleError);
  }

  getIdZonasByPunto(punto: Punto): Promise<Array<number>> {
    const propertyName: string = "geometry";
    const url = `${this.geoServerUrl}/${this.workspace}/wfs`;
    let data = `<wfs:GetFeature service="WFS" version="1.1.0"
                  xmlns:topp="http://www.openplans.org/topp"
                  xmlns:wfs="http://www.opengis.net/wfs"
                  xmlns="http://www.opengis.net/ogc"
                  xmlns:gml="http://www.opengis.net/gml"
                  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                  xsi:schemaLocation="http://www.opengis.net/wfs
                                      http://schemas.opengis.net/wfs/1.1.0/wfs.xsd">
                  <wfs:Query typeName="${this.workspace}:${this.zoneLayer}">
                    <Filter>
                      <Intersects>
                        <PropertyName>${propertyName}</PropertyName>
                          <gml:Point srsName="http://www.opengis.net/gml/srs/epsg.xml#900913">
                            <gml:coordinates>${punto.longitud},${punto.latitud}</gml:coordinates>
                          </gml:Point>
                        </Intersects>
                      </Filter>
                  </wfs:Query>
                </wfs:GetFeature>`;
    return this.http.post(url, data, {headers: this.headers}).toPromise().then(response => {
      let data = response.text();
      // Obtengo la cantidad de resutltados
      let res = data.split('numberOfFeatures="');
      res = res[1].split('" timeStamp="');
      let number = Number(res[0]);
      res = data.split(`<${this.workspace}:id>`);
      let id;
      let listId = [];
      for (let i = 1; i < (number+1); i++) {
        id = res[i].split(`</${this.workspace}:id>`);
        listId.push(Number(id[0]));
      }
      return listId as Array<number>;
    }).catch(this.handleError);
  }

  getAllLocations(): Promise<Punto[]> {
    const url = `${this.geoServerUrl}/wfs?request=GetFeature&version=1.1.0&typeName=${this.workspace}:${this.locationLayer}&outputFormat=GML2`;
    return this.http.get(url).toPromise().then(response => {
      let data = response.text();
      // Obtengo la cantidad de resutltados
      let res = data.split(`<${this.workspace}:id>`);
      let puntos = [];
      let id;
      let punto;
      for (let i = 1; i < res.length; i++) {
        punto = new Punto();
        id = res[i].split(`<${this.workspace}:id>`);
        let coords = id[1].split('ts=" ">');
        coords = coords[1].split('</gml:coordinates>');
        coords = coords[0].split(',');
        punto.longitud = Number(coords[0]);
        punto.latitud = Number(coords[1]);
        punto.id = Number(id[0]);
        puntos.push(punto);
      }
      return puntos as Punto[];
    }).catch(this.handleError);
  }

}
