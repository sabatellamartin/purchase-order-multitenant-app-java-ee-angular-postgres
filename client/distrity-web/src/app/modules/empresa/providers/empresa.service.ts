import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions, Response } from '@angular/http';

import { global } from '../../shared/models/global';
import { PaginatorResponse } from '../../shared/models/paginator-response';

import { TipoEmpresa } from '../models/tipo-empresa';
import { Empresa } from '../models/empresa';

@Injectable()
export class EmpresaService {

  private url: string; // URL to web api
  private headers: Headers = new Headers();

  constructor(private http : Http) {
    if (global.production) {
      this.url = `${global.productionEndpoint}/empresas`;
    } else {
      this.url = `${global.developEndpoint}/empresas`;
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

  private getXMLHeaders(): Headers {
    // Obtengo el token de sesion
    const token = JSON.parse(localStorage.getItem('current_sesion')).token;
    // Armo los headers
    let headers = new Headers();
    headers.append('Content-Type', 'text/xml');
    headers.append('Authorization', 'Bearer ' + token);
    return headers;
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  getAll(): Promise<Empresa[]> {
    return this.http.get(this.url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as Empresa[])
               .catch(this.handleError);
  }

  getByRut(rut: string): Promise<Empresa> {
      let options = new RequestOptions({headers: this.getHeaders()});
      const url = `${this.url}/rut/${rut}`;
      return this.http
                 .get(url, options)
                 .toPromise()
                 .then(response => response.json() as Empresa)
                 .catch(this.handleError);
  }

  getById(id: number): Promise<Empresa> {
    let options = new RequestOptions({headers: this.getHeaders()});
    const url = `${this.url}/${id}`;
    console.log(url);
    return this.http.get(url, options)
               .toPromise()
               .then(response => response.json() as Empresa)
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

  update(empresa: any, tipo:string): Promise<Boolean> {
    let url = `${this.url}/update/cliente/${empresa.id}`;
    if(tipo=="CLIENTE") {
      url = `${this.url}/update/cliente/${empresa.id}`;
    } else if (tipo=="PROVEEDOR") {
      url = `${this.url}/update/proveedor/${empresa.id}`;
    } else if (tipo=="DISTRIBUIDOR") {
      url = `${this.url}/update/distribuidor/${empresa.id}`;
    }
    return this.http
               .put(url, JSON.stringify(empresa), {headers: this.getHeaders()})
               .toPromise()
               .then(request => request.json() as Boolean)
               .catch(this.handleError);
  }

  create(empresa: any, tipo: string): Promise<Boolean> {
    let url = `${this.url}/add/cliente`;
    if(tipo=="CLIENTE") {
      url = `${this.url}/add/cliente`;
    } else if (tipo=="PROVEEDOR") {
      url = `${this.url}/add/proveedor`;
    } else if (tipo=="DISTRIBUIDOR") {
      url = `${this.url}/add/distribuidor`;
    }
    return this.http
               .post(url, JSON.stringify(empresa), {headers: this.getHeaders()})
               .toPromise()
               .then(request => request.json() as Boolean)
               .catch(this.handleError);
  }

  searchByTerm(term: string): Promise<Empresa[]> {
    term = term ? term : 'void';
    const url = `${this.url}/search/${term}`;
    return this.http.get(url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as Empresa[])
               .catch(this.handleError);
  }

  searchByTipo(term: string, tipo: string): Promise<Empresa[]> {
    term = term ? term : 'void';
    const url = `${this.url}/search/${term}/tipo/${tipo}`;
    return this.http.get(url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as Empresa[])
               .catch(this.handleError);
  }

}
