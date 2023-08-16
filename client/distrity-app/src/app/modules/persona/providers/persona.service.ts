import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import { global } from '../../shared/models/global';

import { Persona } from '../models/persona';

import { AutenticacionService } from '../../auth/providers/autenticacion.service';

@Injectable()
export class PersonaService {

  private url: string; // URL to web api
  private sesion: any;

  constructor(
    private http : Http,
    private autenticacionService: AutenticacionService) {
    if (global.production) {
      this.url = `${global.productionEndpoint}/personas`;
    } else {
      this.url = `${global.developEndpoint}/personas`;
    }
    this.getSesion();
  }

  private async getSesion() {
    this.sesion = await this.autenticacionService.getSesion();
  }

  private getHeaders(): Headers {
    let headers = new Headers();
    if (this.sesion) {
      headers.append('Authorization', 'Bearer ' + this.sesion.token);
      headers.append('Content-Type', 'application/json');
    }
    return headers;
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  searchByTerm(term: string): Promise<Persona[]> {
    term = term ? term : 'void';
    const url = `${this.url}/search/${term}`;
    return this.http.get(url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as Persona[])
               .catch(this.handleError);
  }

  searchByTipo(term: string, tipo: string): Promise<Persona[]> {
    term = term ? term : 'void';
    let url = `${this.url}/search/${term}`;
    if (tipo=="E") {
      url = `${this.url}/search/empleado/${term}`;
    } else if (tipo=="C") {
      url = `${this.url}/search/consumidor/${term}`;
    } else if (tipo=="R") {
      url = `${this.url}/search/referente/${term}`;
    }
    return this.http.get(url, {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as Persona[])
               .catch(this.handleError);
  }

}
