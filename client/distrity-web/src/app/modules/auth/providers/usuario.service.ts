import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions, Response } from '@angular/http';

import { global } from '../../shared/models/global';
import { PaginatorResponse } from '../../shared/models/paginator-response';

import { Sesion } from '../models/sesion';
import { Usuario } from '../models/usuario';
import { Administrador } from '../models/administrador';
import { Operador } from '../models/operador';

@Injectable()
export class UsuarioService {

  private url: string; // URL to web api
  private headers: Headers = new Headers();

  constructor(private http : Http) {
    if (global.production) {
      this.url = `${global.productionEndpoint}/usuarios`;
    } else {
      this.url = `${global.developEndpoint}/usuarios`;
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

  search(term: string): Promise<Usuario[]> {
    term = term ? term : 'void';
    const url = `${this.url}/search/${term}`;
    return this.http.get(url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as Usuario[])
               .catch(this.handleError);
  }

  getAll(): Promise<Usuario[]> {
    return this.http.get(this.url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as Usuario[])
               .catch(this.handleError);
  }

  getByUsername(username: string): Promise<Usuario> {
      let options = new RequestOptions({headers: this.getHeaders()});
      const url = `${this.url}/username/${username}`;
      return this.http
                 .get(url, options)
                 .toPromise()
                 .then(response => response.json() as Usuario)
                 .catch(this.handleError);
  }

  getByEmail(email: string): Promise<Usuario> {
      let options = new RequestOptions({headers: this.getHeaders()});
      const url = `${this.url}/email/${email}`;
      return this.http
                 .get(url, options)
                 .toPromise()
                 .then(response => response.json() as Usuario)
                 .catch(this.handleError);
  }

  getById(id: number): Promise<Usuario> {
    let options = new RequestOptions({headers: this.getHeaders()});
    const url = `${this.url}/${id}`;
    return this.http.get(url, options)
               .toPromise()
               .then(response => response.json() as Usuario)
               .catch(this.handleError);
  }

  existByEmail(email: string): Promise<Boolean> {
      let options = new RequestOptions({headers: this.getHeaders()});
      const url = `${this.url}/exist/email/${email}`;
      return this.http
                 .get(url, options)
                 .toPromise()
                 .then(response => response.json() as Boolean)
                 .catch(this.handleError);
  }

  searchByRol(term: string, rol: string): Promise<Usuario[]> {
    term = term ? term : 'void';
    const url = `${this.url}/search/rol/${term}/${rol}`;
    return this.http.get(url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as Usuario[])
               .catch(this.handleError);
  }

  toggleLock(id: number): Promise<Boolean> {
      let options = new RequestOptions({headers: this.getHeaders()});
      const url = `${this.url}/toggle/lock/${id}`;
      return this.http
                 .get(url, options)
                 .toPromise()
                 .then(response => response.json() as Boolean)
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

  update(usuario: any, tipo:string): Promise<Boolean> {
    const url = tipo=="ADMINISTRADOR" ? `${this.url}/update/administrador/${usuario.id}` : `${this.url}/update/operador/${usuario.id}`;
    return this.http
               .put(url, JSON.stringify(usuario), {headers: this.getHeaders()})
               .toPromise()
               .then(request => request.json() as Boolean)
               .catch(this.handleError);
  }

  create(usuario: any, tipo: string): Promise<Boolean> {
    const url = tipo=="ADMINISTRADOR" ? `${this.url}/add/administrador` : `${this.url}/add/operador`;
    return this.http
               .post(url, JSON.stringify(usuario), {headers: this.getHeaders()})
               .toPromise()
               .then(request => request.json() as Boolean)
               .catch(this.handleError);
  }

  searchOperadoresByRol(term: string, rol: string): Promise<Operador[]> {
    term = term ? term : 'void';
    const url = `${this.url}/search/operadores/${rol}/${term}`;
    return this.http.get(url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as Operador[])
               .catch(this.handleError);
  }

  getOperadorByEmail(email: string): Promise<Operador> {
      let options = new RequestOptions({headers: this.getHeaders()});
      const url = `${this.url}/email/operador/${email}`;
      return this.http
                 .get(url, options)
                 .toPromise()
                 .then(response => response.json() as Operador)
                 .catch(this.handleError);
  }

  updateOperador(operador: Operador): Promise<Boolean> {
    const url = `${this.url}/update/operador/${operador.id}`;
    return this.http
               .put(url, JSON.stringify(operador), {headers: this.getHeaders()})
               .toPromise()
               .then(request => request.json() as Boolean)
               .catch(this.handleError);
  }

  createOperador(operador: Operador): Promise<Boolean> {
    const url = `${this.url}/add/operador`;
    return this.http
               .post(url, JSON.stringify(operador), {headers: this.getHeaders()})
               .toPromise()
               .then(request => request.json() as Boolean)
               .catch(this.handleError);
  }

  deleteOperador(id: number): Promise<Boolean> {
    let options = new RequestOptions({headers: this.getHeaders()});
    const url = `${this.url}/operador/${id}`;
    return this.http.delete(url, options)
               .toPromise()
               .then(result => result.json() as Boolean)
               .catch(this.handleError);
  }

  toggleLockOperador(id: number): Promise<Boolean> {
      let options = new RequestOptions({headers: this.getHeaders()});
      const url = `${this.url}/lock/operador/${id}`;
      return this.http
                 .get(url, options)
                 .toPromise()
                 .then(response => response.json() as Boolean)
                 .catch(this.handleError);
  }

  toggleBajaOperador(id: number): Promise<Boolean> {
      let options = new RequestOptions({headers: this.getHeaders()});
      const url = `${this.url}/toggle/baja/operador/${id}`;
      return this.http
                 .get(url, options)
                 .toPromise()
                 .then(response => response.json() as Boolean)
                 .catch(this.handleError);
  }

  restorePasswordOperador(id: number): Promise<string> {
      let options = new RequestOptions({headers: this.getHeaders()});
      const url = `${this.url}/restore/pass/operador/${id}`;
      return this.http
                 .get(url,options)
                 .toPromise()
                 .then(response => response.text() as string)
                 .catch(this.handleError);
  }

  changePasswordOperador(clearOld: string, clearNew: string): Promise<Boolean> {
      let options = new RequestOptions({headers: this.getHeaders()});
      const url = `${this.url}/change/pass/operador/${clearOld}/${clearNew}`;
      return this.http
                 .get(url, options)
                 .toPromise()
                 .then(response => response.json() as Boolean)
                 .catch(this.handleError);
  }

}
