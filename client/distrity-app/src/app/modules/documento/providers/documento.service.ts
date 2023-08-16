import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions, ResponseContentType } from '@angular/http';

// Plugins
import { NativeStorage } from '@ionic-native/native-storage';

import { global } from '../../shared/models/global';
import { PaginatorResponse } from '../../shared/models/paginator-response';

import { OrdenVenta } from '../models/orden-venta';
import { OrdenVentaFilter } from '../models/filter/ordenventafilter';

import { AutenticacionService } from '../../auth/providers/autenticacion.service';

@Injectable()
export class DocumentoService {

  private url: string; // URL to web api

  private storageName: string = 'orden';

  constructor(
    private http : Http,
    private nativeStorage: NativeStorage,
    private autenticacionService: AutenticacionService) {
    if (global.production) {
      this.url = `${global.productionEndpoint}/documentos`;
    } else {
      this.url = `${global.developEndpoint}/documentos`;
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

  setOrden(orden: OrdenVenta): Promise<boolean> {
    return this.nativeStorage.setItem(this.storageName, orden).then(() => {
      return true;//this.publishOrden(true, orden);
    }).catch(e => { return false });
  }

  getOrden(): Promise<OrdenVenta> {
    return this.nativeStorage.getItem(this.storageName).then(orden => {
        return orden as OrdenVenta;
    }).catch(e => { return null });
  }

  removeOrden(): void {
    this.nativeStorage.remove(this.storageName);
  }

  create(documento: any, tipo: string): Promise<Boolean> {
    let url = `${this.url}/add/ordenventa`;
    if(tipo=="ORDENVENTA") {
      url = `${this.url}/add/ordenventa`;
    } else if (tipo=="ORDENCOMPRA") {
      url = `${this.url}/add/ordencompra`;
    }
    return this.autenticacionService.getSesion().then(sesion => {
      return this.http
                 .post(url, JSON.stringify(documento), {headers: this.getHeaders(sesion)})
                 .toPromise()
                 .then(request => request.json() as Boolean)
                 .catch(this.handleError);
    });
  }

  getById(id: number): Promise<OrdenVenta> {
    const url = `${this.url}/${id}`;
    return this.autenticacionService.getSesion().then(sesion => {
      let options = new RequestOptions({headers: this.getHeaders(sesion)});
      return this.http.get(url, options)
                 .toPromise()
                 .then(response => response.json() as OrdenVenta)
                 .catch(this.handleError);
                 });
  }

  documentoPdfById(id: number): Promise<Blob> {
    const url = `${this.url}/pdf/${id}`;
    return this.autenticacionService.getSesion().then(sesion => {
      let options = new RequestOptions({headers: this.getHeadersPdf(sesion), responseType: ResponseContentType.Blob});
      return this.http.get(url, options)
               .toPromise()
               .then(response => {
                 return response.blob();//new Blob([response.blob()], { type: 'application/pdf' });
               }).catch(this.handleError);
    });
  }

  private getHeadersPdf(sesion): Headers {
    let headers = new Headers();
    if (sesion) {
      headers.append('Authorization', 'Bearer ' + sesion.token);
      headers.append('Content-Type', 'application/pdf');
      headers.append('Accept', 'application/octet-stream');
    }
    return headers;
  }

  update(documento: any, tipo: string): Promise<Boolean> {
    let url = `${this.url}/update/ordenventa/${documento.id}`;
    if(tipo=="ORDENVENTA") {
      url = `${this.url}/update/ordenventa/${documento.id}`;
    } else if (tipo=="ORDENCOMPRA") {
      url = `${this.url}/update/ordencompra/${documento.id}`;
    }
    return this.autenticacionService.getSesion().then(sesion => {
      return this.http
                 .put(url, JSON.stringify(documento), {headers: this.getHeaders(sesion)})
                 .toPromise()
                 .then(request => request.json() as Boolean)
                 .catch(this.handleError);
    });
  }

  searchOrdenVentaFilter(filter: OrdenVentaFilter): Promise<PaginatorResponse> {
    const url = `${this.url}/search/ordenventa/filter/owner`;
    return this.autenticacionService.getSesion().then(sesion => {
      return this.http.post(url, JSON.stringify(filter), {headers: this.getHeaders(sesion)})
                 .toPromise()
                 .then(response => response.json() as PaginatorResponse)
                 .catch(this.handleError);
    });
  }

}
