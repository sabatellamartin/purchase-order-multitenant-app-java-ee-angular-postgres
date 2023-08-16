import { Injectable } from '@angular/core';
import { Headers, Http, Response, RequestOptions } from '@angular/http';

import { global } from '../../shared/models/global';
import { PaginatorResponse } from '../../shared/models/paginator-response';

import { Sesion } from '../models/sesion';
import { Usuario } from '../models/usuario';

@Injectable()
export class AutenticacionService {

  private url: string; // Endpoint

  constructor(private http : Http) {
    if (global.production) {
      this.url = `${global.productionEndpoint}/authentication`;
    } else {
      this.url = `${global.developEndpoint}/authentication`;
    }
  }

  private getHeaders(): Headers {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', 'Bearer ' + this.getSesion().token);
    return headers;
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  login(username: string, password: string): Promise<Boolean> {
    const headers = new Headers({'Content-Type': 'application/json'});
    return this.http
      .post(this.url, JSON.stringify({ username: username, password: password }), {headers : headers})
      .toPromise()
      .then(response => {
        let sesion = response.json() as Sesion;
        if (sesion.token) {
          this.saveSesion(sesion);
          return true;
        }
        return false;
     }).catch(e => {
        if (e.status === 401) { return false; }
     });
  }

  logout() {
    localStorage.removeItem('current_sesion');
  }

  saveSesion(sesion: Sesion) {
    localStorage.setItem('current_sesion', JSON.stringify(sesion));
  }

  getSesion(): any {
    return JSON.parse(localStorage.getItem('current_sesion'));
  }

}
