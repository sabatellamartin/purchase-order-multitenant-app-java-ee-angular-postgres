import { Injectable } from '@angular/core';

@Injectable()
export class SharedService {

  public appEndpoint: string;
  public geoServerEndpoint: string;
  public defaultUserImg: string;

  constructor() {
    const ip = 'localhost'; // localhost 192.168.0.121 192.168.1.7 AWS 18.218.78.110
    // From remote device to any server
    this.appEndpoint = 'http://' + ip + '/distrity-web/rest';
    this.geoServerEndpoint = 'http://' + ip + ':8600/geoserver';
    this.defaultUserImg = "./assets/avatars/avatar.png";
  }

}
