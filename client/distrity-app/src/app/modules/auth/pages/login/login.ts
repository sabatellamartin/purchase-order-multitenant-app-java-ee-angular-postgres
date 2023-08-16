import { Component, ViewChild } from '@angular/core';
import { AlertController } from 'ionic-angular';

// Cryptojs
import * as crypto from 'crypto-js';

// Providers
import { AutenticacionService } from '../../providers/autenticacion.service';

@Component({
  selector: 'page-login',
  templateUrl: 'login.html'
})
export class LoginPage {

  logo: string = "../../../../../../assets/imgs/logo.svg";

  @ViewChild("username") username;
  @ViewChild("password") password;

  constructor(
    public alertCtrl: AlertController,
    private autenticacionService: AutenticacionService) {
  }

  /* Local */
  signIn() {
    const username = this.username.value!="" ? this.username.value.toLowerCase().trim() : false;
    const password = this.password.value!="" ? crypto.SHA512(this.password.value).toString(crypto.enc.Hex) : false;
    if (username && password) {
      this.autenticacionService.login(username, password).then(logged => {
        if (!logged) {
          this.showMessage(false);
        }
      });
    } else {
      this.showMessage(false);
    }
  }

  private showMessage(logged: boolean) {
    if (!logged) {
      this.alertCtrl.create({
        title: 'Inicio incorrecto',
        subTitle: 'Verifica tu información de acceso',
        buttons: ['Aceptar']
      }).present();
    }
  }

}
