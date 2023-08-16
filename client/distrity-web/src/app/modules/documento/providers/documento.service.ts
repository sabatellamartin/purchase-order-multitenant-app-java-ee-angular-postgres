import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions, Response, ResponseContentType} from '@angular/http';

import { global } from '../../shared/models/global';
import { PaginatorResponse } from '../../shared/models/paginator-response';

import { Documento } from '../models/documento';
import { OrdenVenta } from '../models/ordenventa';
import { OrdenVentaFilter } from '../models/filters/ordenventa-filter';

@Injectable()
export class DocumentoService {

  private url: string; // URL to web api
  private headers: Headers = new Headers();

  constructor(private http : Http) {
    if (global.production) {
      this.url = `${global.productionEndpoint}/documentos`;
    } else {
      this.url = `${global.developEndpoint}/documentos`;
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

  private getHeadersPdf(): Headers {
    // Obtengo el token de sesion
    const token = JSON.parse(localStorage.getItem('current_sesion')).token;
    // Armo los headers
    let headers = new Headers();
    headers.append('Authorization', 'Bearer ' + token);
    headers.append('Content-Type', 'application/json');
    headers.append('Accept', 'application/octet-stream');
    return headers;
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  changeEstado(id: number): Promise<boolean> {
    let options = new RequestOptions({headers: this.getHeaders()});
    const url = `${this.url}/change/estado/${id}`;
    return this.http.get(url, options)
               .toPromise()
               .then(response => response.json() as boolean)
               .catch(this.handleError);
  }

  cancelarToggle(id: number): Promise<boolean> {
    let options = new RequestOptions({headers: this.getHeaders()});
    const url = `${this.url}/cancelar/${id}`;
    return this.http.get(url, options)
               .toPromise()
               .then(response => response.json() as boolean)
               .catch(this.handleError);
  }

  documentoPdfById(id: number): Promise<Blob> {
    const url = `${this.url}/pdf/${id}`;
    let options = new RequestOptions({headers: this.getHeadersPdf(), responseType: ResponseContentType.Blob});
    return this.http.get(url, options)
             .toPromise()
             .then(response => {
               return response.blob();//new Blob([response.blob()], { type: 'application/pdf' });
             }).catch(this.handleError);
  }

  getById(id: number): Promise<OrdenVenta> {
    const url = `${this.url}/${id}`;
    let options = new RequestOptions({headers: this.getHeaders()});
    return this.http.get(url, options)
               .toPromise()
               .then(response => response.json() as OrdenVenta)
               .catch(this.handleError);
  }

  create(documento: any, tipo: string): Promise<Boolean> {
    let url = `${this.url}/add/ordenventa`;
    if(tipo=="ORDENVENTA") {
      url = `${this.url}/add/ordenventa`;
    } else if (tipo=="ORDENCOMPRA") {
      url = `${this.url}/add/ordencompra`;
    }
    return this.http
               .post(url, JSON.stringify(documento), {headers: this.getHeaders()})
               .toPromise()
               .then(request => request.json() as Boolean)
               .catch(this.handleError);

  }

  update(documento: any, tipo: string): Promise<Boolean> {
    let url = `${this.url}/update/ordenventa/${documento.id}`;
    if(tipo=="ORDENVENTA") {
      url = `${this.url}/update/ordenventa/${documento.id}`;
    } else if (tipo=="ORDENCOMPRA") {
      url = `${this.url}/update/ordencompra/${documento.id}`;
    }
    return this.http
               .put(url, JSON.stringify(documento), {headers: this.getHeaders()})
               .toPromise()
               .then(request => request.json() as Boolean)
               .catch(this.handleError);
  }

  searchOrdenVentaFilter(filter: OrdenVentaFilter): Promise<PaginatorResponse> {
    const url = `${this.url}/search/ordenventa/filter`;
    return this.http.post(url, JSON.stringify(filter), {headers: this.getHeaders()})
               .toPromise()
               .then(response => response.json() as PaginatorResponse)
               .catch(this.handleError);
  }

}
