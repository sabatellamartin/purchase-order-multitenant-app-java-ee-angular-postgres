import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions, Response } from '@angular/http';

import { global } from '../../shared/models/global';
import { PaginatorResponse } from '../../shared/models/paginator-response';

import { Unidad } from '../models/unidad';
import { Articulo } from '../models/articulo';

import { ArticuloFilter } from '../models/filters/articulo-filter';

@Injectable()
export class ArticuloService {

  private url: string; // URL to web api
  private headers: Headers = new Headers();

  constructor(private http : Http) {
    if (global.production) {
      this.url = `${global.productionEndpoint}/articulos`;
    } else {
      this.url = `${global.developEndpoint}/articulos`;
    }
  }

  private getHeaders(): Headers {
    // Obtengo el token de sesion
    const token = JSON.parse(localStorage.getItem('current_sesion')).token;
    // Armo los headers
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', 'Bearer ' + token);
    return headers;
  }

  private getHeadersFile(): Headers {
    // Obtengo el token de sesion
    const token = JSON.parse(localStorage.getItem('current_sesion')).token;
    // Armo los headers
    let headers = new Headers();
    headers.append('Content-Type', 'multipart/form-data');
    headers.append('Accept', 'application/json');
    headers.append('Authorization', 'Bearer ' + token);
    return headers;
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  getAll(): Promise<Articulo[]> {
    return this.http.get(this.url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as Articulo[])
               .catch(this.handleError);
  }

  getById(id: number): Promise<Articulo> {
    let options = new RequestOptions({headers: this.getHeaders()});
    const url = `${this.url}/${id}`;
    return this.http.get(url, options)
               .toPromise()
               .then(response => response.json() as Articulo)
               .catch(this.handleError);
  }

  searchByTerm(term: string): Promise<Articulo[]> {
    term = term ? term : 'void';
    const url = `${this.url}/search/${term}`;
    return this.http.get(url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as Articulo[])
               .catch(this.handleError);
  }

  delete(id: number): Promise<Boolean> {
    let options = new RequestOptions({headers: this.getHeaders()});
    const url = `${this.url}/${id}`;
    return this.http.delete(url, options)
               .toPromise()
               .then(result => result.json() as Boolean)
               .catch(this.handleError);
  }

  update(articulo: any): Promise<Boolean> {
    let url = `${this.url}/update/${articulo.id}`;
    return this.http
               .put(url, JSON.stringify(articulo), {headers: this.getHeaders()})
               .toPromise()
               .then(request => request.json() as Boolean)
               .catch(this.handleError);
  }

  create(articulo: any): Promise<Boolean> {
    let url = `${this.url}/add`;
    return this.http
               .post(url, JSON.stringify(articulo), {headers: this.getHeaders()})
               .toPromise()
               .then(request => request.json() as Boolean)
               .catch(this.handleError);
  }

  loadDatosArticulosFile(formData: FormData): Promise<Articulo[]> {
    const options = new RequestOptions({headers: this.getHeadersFile()});
    const url = `${this.url}/load/data/file`;
    return this.http
               .post(url, formData, options)
               .toPromise()
               .then(request => request.json() as Articulo[])
               .catch(this.handleError);
  }

  searchFilter(filter: ArticuloFilter): Promise<PaginatorResponse> {
    const url = `${this.url}/search/filter`;
    return this.http.post(url, JSON.stringify(filter), {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as PaginatorResponse)
               .catch(this.handleError);
  }

}
