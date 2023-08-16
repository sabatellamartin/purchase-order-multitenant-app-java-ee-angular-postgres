import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';

import { global } from '../../shared/models/global';

import { Moneda } from '../models/moneda';

import { AutenticacionService } from '../../auth/providers/autenticacion.service';

@Injectable()
export class MonedaService {

  private url: string; // URL to web api

  constructor(
    private http : Http,
    private autenticacionService: AutenticacionService) {
    if (global.production) {
      this.url = `${global.productionEndpoint}/monedas`;
    } else {
      this.url = `${global.developEndpoint}/monedas`;
    }
  }

  private getHeaders(sesion): Headers {
    let headers = new Headers();
    if (sesion) {
      headers.append('Authorization', 'Bearer ' + sesion.token);
      headers.append('Content-Type', 'application/json');
    }
    return headers;
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  getAllMonedas(): Promise<Moneda[]> {
    const term = 'void';
    const url = `${this.url}/search/${term}`;
    return this.autenticacionService.getSesion().then(sesion => {
      return this.http.get(url, {headers: this.getHeaders(sesion)})
                 .toPromise()
                 .then(response => response.json() as Moneda[])
                 .catch(this.handleError);
    });
  }

  searchByTerm(term: string): Promise<Moneda[]> {
    term = term ? term : 'void';
    const url = `${this.url}/search/${term}`;
    return this.autenticacionService.getSesion().then(sesion => {
      return this.http.get(url, {headers: this.getHeaders(sesion)})
                 .toPromise()
                 .then(response => response.json() as Moneda[])
                 .catch(this.handleError);
    });
  }

  getById(id: number): Promise<Moneda> {
    const url = `${this.url}/${id}`;
    return this.autenticacionService.getSesion().then(sesion => {
      let options = new RequestOptions({headers: this.getHeaders(sesion)});
      return this.http.get(url, options)
                 .toPromise()
                 .then(response => response.json() as Moneda)
                 .catch(this.handleError);
                 });
  }

}
