import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';

import { global } from '../../shared/models/global';

import { Usuario } from '../models/usuario';
import { Operador } from '../models/operador';

import { AutenticacionService } from './autenticacion.service';

@Injectable()
export class UsuarioService {

  private url: string;

  constructor(private http : Http,
    private autenticacionService: AutenticacionService) {
    if (global.production) {
      this.url = `${global.productionEndpoint}/usuarios`;
    } else {
      this.url = `${global.developEndpoint}/usuarios`;
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

  getByUsername(username: string): Promise<Usuario> {
    const url = `${this.url}/username/${username}`;
    return this.autenticacionService.getSesion().then(sesion => {
      let options = new RequestOptions({headers: this.getHeaders(sesion)});
      return this.http
                 .get(url, options)
                 .toPromise()
                 .then(response => response.json() as Usuario)
               .catch(this.handleError);
    });
  }

  getByEmail(email: string): Promise<Usuario> {
      const url = `${this.url}/email/${email}`;
      return this.autenticacionService.getSesion().then(sesion => {
        let options = new RequestOptions({headers: this.getHeaders(sesion)});
        return this.http
                   .get(url, options)
                   .toPromise()
                   .then(response => response.json() as Usuario)
                   .catch(this.handleError);
                   });
  }

  getById(id: number): Promise<Usuario> {
    const url = `${this.url}/${id}`;
    return this.autenticacionService.getSesion().then(sesion => {
      let options = new RequestOptions({headers: this.getHeaders(sesion)});
      return this.http.get(url, options)
                 .toPromise()
                 .then(response => response.json() as Usuario)
                 .catch(this.handleError);
                 });
  }

  existByEmail(email: string): Promise<Boolean> {
      const url = `${this.url}/exist/email/${email}`;
      return this.autenticacionService.getSesion().then(sesion => {
        let options = new RequestOptions({headers: this.getHeaders(sesion)});
        return this.http
                   .get(url, options)
                   .toPromise()
                   .then(response => response.json() as Boolean)
                   .catch(this.handleError);
                   });
  }

  getOperadorByEmail(email: string): Promise<Operador> {
      const url = `${this.url}/email/operador/${email}`;
      return this.autenticacionService.getSesion().then(sesion => {
        let options = new RequestOptions({headers: this.getHeaders(sesion)});
        return this.http
                   .get(url, options)
                   .toPromise()
                   .then(response => response.json() as Operador)
                   .catch(this.handleError);
                   });
  }

  changePasswordOperador(clearOld: string, clearNew: string): Promise<Boolean> {
    const url = `${this.url}/change/pass/operador/${clearOld}/${clearNew}`;
    return this.autenticacionService.getSesion().then(sesion => {
      let options = new RequestOptions({headers: this.getHeaders(sesion)});
      return this.http
                 .get(url, options)
                 .toPromise()
                 .then(response => response.json() as Boolean)
                 .catch(this.handleError);
                 });
  }

}
