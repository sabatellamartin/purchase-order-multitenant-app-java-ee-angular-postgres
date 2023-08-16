import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions, Response } from '@angular/http';

import { global } from '../../shared/models/global';
import { PaginatorResponse } from '../../shared/models/paginator-response';

import { TipoDocumento } from '../models/tipo-documento';

@Injectable()
export class TipoDocumentoService {

  private url: string; // URL to web api
  private headers: Headers = new Headers();

  constructor(private http : Http) {
    if (global.production) {
      this.url = `${global.productionEndpoint}/tipo/documentos`;
    } else {
      this.url = `${global.developEndpoint}/tipo/documentos`;
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

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  search(term: string): Promise<TipoDocumento[]> {
    term = term ? term : 'void';
    const url = `${this.url}/search/${term}`;
    return this.http.get(url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as TipoDocumento[])
               .catch(this.handleError);
  }

  getAll(): Promise<TipoDocumento[]> {
    return this.http.get(this.url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as TipoDocumento[])
               .catch(this.handleError);
  }

  getBySigla(sigla: string): Promise<TipoDocumento> {
      let options = new RequestOptions({headers: this.getHeaders()});
      const url = `${this.url}/sigla/${sigla}`;
      return this.http
                 .get(url, options)
                 .toPromise()
                 .then(response => response.json() as TipoDocumento)
                 .catch(this.handleError);
  }

  getById(id: number): Promise<TipoDocumento> {
    let options = new RequestOptions({headers: this.getHeaders()});
    const url = `${this.url}/${id}`;
    return this.http.get(url, options)
               .toPromise()
               .then(response => response.json() as TipoDocumento)
               .catch(this.handleError);
  }

  searchByTerm(term: string): Promise<TipoDocumento[]> {
    term = term ? term : 'void';
    const url = `${this.url}/search/${term}`;
    return this.http.get(url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as TipoDocumento[])
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

  update(tipoDocumento: any): Promise<Boolean> {
    const url = `${this.url}/update/${tipoDocumento.id}`;
    return this.http
               .put(url, JSON.stringify(tipoDocumento), {headers: this.getHeaders()})
               .toPromise()
               .then(request => request.json() as Boolean)
               .catch(this.handleError);
  }

  create(tipoDocumento: any): Promise<Boolean> {
    const url = `${this.url}/add`;
    return this.http
               .post(url, JSON.stringify(tipoDocumento), {headers: this.getHeaders()})
               .toPromise()
               .then(request => request.json() as Boolean)
               .catch(this.handleError);
  }

}
