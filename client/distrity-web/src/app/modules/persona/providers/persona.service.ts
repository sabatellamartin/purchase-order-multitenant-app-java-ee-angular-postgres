import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions, Response } from '@angular/http';

import { global } from '../../shared/models/global';
import { PaginatorResponse } from '../../shared/models/paginator-response';

import { TipoDocumento } from '../models/tipo-documento';
import { Persona } from '../models/persona';

@Injectable()
export class PersonaService {

  private url: string; // URL to web api
  private headers: Headers = new Headers();

  constructor(private http : Http) {
    if (global.production) {
      this.url = `${global.productionEndpoint}/personas`;
    } else {
      this.url = `${global.developEndpoint}/personas`;
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

  getAll(): Promise<Persona[]> {
    return this.http.get(this.url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as Persona[])
               .catch(this.handleError);
  }

  getByDocumento(documento: string): Promise<Persona> {
      let options = new RequestOptions({headers: this.getHeaders()});
      const url = `${this.url}/documento/${documento}`;
      return this.http
                 .get(url, options)
                 .toPromise()
                 .then(response => response.json() as Persona)
                 .catch(this.handleError);
  }

  getById(id: number): Promise<Persona> {
    let options = new RequestOptions({headers: this.getHeaders()});
    const url = `${this.url}/${id}`;
    return this.http.get(url, options)
               .toPromise()
               .then(response => response.json() as Persona)
               .catch(this.handleError);
  }

  searchByTerm(term: string): Promise<Persona[]> {
    term = term ? term : 'void';
    const url = `${this.url}/search/${term}`;
    return this.http.get(url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as Persona[])
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

  update(persona: any, tipo:string): Promise<Boolean> {
    let url = `${this.url}/update/consumidor/${persona.id}`;
    if(tipo=="REFERENTE") {
      url = `${this.url}/update/referente/${persona.id}`;
    } else if (tipo=="EMPLEADO") {
      url = `${this.url}/update/empleado/${persona.id}`;
    }
    return this.http
               .put(url, JSON.stringify(persona), {headers: this.getHeaders()})
               .toPromise()
               .then(request => request.json() as Boolean)
               .catch(this.handleError);
  }

  create(persona: any, tipo: string): Promise<Boolean> {
    let url = `${this.url}/add/consumidor`;
    if(tipo=="REFERENTE") {
      url = `${this.url}/add/referente`;
    } else if (tipo=="EMPLEADO") {
      url = `${this.url}/add/empleado`;
    }
    return this.http
               .post(url, JSON.stringify(persona), {headers: this.getHeaders()})
               .toPromise()
               .then(request => request.json() as Boolean)
               .catch(this.handleError);
  }

  existByDocumento(numero: string, tipoDocumento: TipoDocumento): Promise<Boolean> {
      let options = new RequestOptions({headers: this.getHeaders()});
      const url = `${this.url}/exist/numero/${numero}/tipo/${tipoDocumento.id}`;
      return this.http
                 .get(url, options)
                 .toPromise()
                 .then(response => response.json() as Boolean)
                 .catch(this.handleError);
  }

  searchByTipo(term: string, tipo: string): Promise<Persona[]> {
    term = term ? term : 'void';
    const url = `${this.url}/search/${term}/tipo/${tipo}`;
    return this.http.get(url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as Persona[])
               .catch(this.handleError);
  }

}
