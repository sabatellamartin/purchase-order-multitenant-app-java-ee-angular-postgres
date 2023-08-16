import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';

import { Operador } from '../../models/operador';

import { AutenticacionService } from '../../providers/autenticacion.service';

import { ChangePasswordPage } from '../change-password/change-password';

@Component({
  selector: 'page-profile',
  templateUrl: 'profile.html'
})
export class ProfilePage {

  sesion: any = false;
  usuario: Operador = new Operador();

  picture: string = '../../../../../assets/avatars/avatar.png';

  constructor(
    public navCtrl: NavController,
  	private autenticacionService: AutenticacionService/*,
    private usuarioService: UsuarioService*/) {
    this.autenticacionService.getSesion().then(sesion => {
      this.sesion = sesion;
      if (sesion.email) {
        //this.usuarioService.getOperadorByEmail(sesion.email).then(usuario => this.usuario = usuario);
      }
    });
  }

  goToChangePassword() {
    this.navCtrl.push(ChangePasswordPage);
  }

}
