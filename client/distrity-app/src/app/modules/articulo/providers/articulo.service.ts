import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';

import { global } from '../../shared/models/global';

import { Articulo } from '../models/articulo';

import { AutenticacionService } from '../../auth/providers/autenticacion.service';

@Injectable()
export class ArticuloService {

  private url: string;

  constructor(
    private http : Http,
    private autenticacionService: AutenticacionService) {
    if (global.production) {
      this.url = `${global.productionEndpoint}/articulos`;
    } else {
      this.url = `${global.developEndpoint}/articulos`;
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

  getById(id: number): Promise<Articulo> {
    const url = `${this.url}/${id}`;
    return this.autenticacionService.getSesion().then(sesion => {
      let options = new RequestOptions({headers: this.getHeaders(sesion)});
      return this.http.get(url, options)
                 .toPromise()
                 .then(response => response.json() as Articulo)
                 .catch(this.handleError);
    });
  }

  searchByTerm(term: string): Promise<Articulo[]> {
    term = term ? term : 'void';
    const url = `${this.url}/search/${term}`;
    return this.autenticacionService.getSesion().then(sesion => {
      return this.http.get(url, {headers: this.getHeaders(sesion)})
                 .toPromise()
                 .then(response => response.json() as Articulo[])
                 .catch(this.handleError);
    });
  }

  getArticulos(term: string): Promise<Articulo[]> {
    term = term ? term : 'void';
    const url = `${this.url}/search/${term}`;
    return this.autenticacionService.getSesion().then(sesion => {
      return this.http.get(url, {headers: this.getHeaders(sesion)})
                 .toPromise()
                 .then(response => response.json() as Articulo[])
                 .catch(this.handleError);
    });
  }

}
