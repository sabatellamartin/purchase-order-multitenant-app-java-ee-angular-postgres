import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import { NativeStorage } from '@ionic-native/native-storage';
import { Events } from 'ionic-angular';

import { global } from '../../shared/models/global';

import { Sesion } from '../models/sesion';

@Injectable()
export class AutenticacionService {

  private url: string;

  constructor(private http : Http,
              private nativeStorage: NativeStorage,
              public events: Events) {
    if (global.production) {
      this.url = `${global.productionEndpoint}/authentication`;
    } else {
      this.url = `${global.developEndpoint}/authentication`;
    }
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

  saveSesion(sesion: Sesion) {
    this.nativeStorage.setItem('sesion', sesion).then(() => {
      this.publishSesionStatus(true);
    }).catch(e => { return false });
  }

  getSesion(): Promise<any> {
    return this.nativeStorage.getItem('sesion').then(sesion => {
        return sesion;
    }).catch(e => { return false });
  }

  logout(): void {
    this.nativeStorage.remove('sesion');
    this.publishSesionStatus(false);
  }

  private publishSesionStatus(status: any) {
    this.events.publish('sesion:stored', status);
  }

}
