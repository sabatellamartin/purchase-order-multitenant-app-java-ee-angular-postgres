import { Component, ViewChild } from '@angular/core';
import { AlertController } from 'ionic-angular';

// Providers
import { AutenticacionService } from '../../providers/autenticacion.service';
import { UsuarioService } from '../../providers/usuario.service';

@Component({
  selector: 'page-change-password',
  templateUrl: 'change-password.html'
})
export class ChangePasswordPage {

  @ViewChild("password") password;
  @ViewChild("newPassword") newPassword;
  @ViewChild("repeatPassword") repeatPassword;

  constructor(
    public alertCtrl: AlertController,
    private autenticacionService: AutenticacionService,
    private usuarioService: UsuarioService) {
  }

  changePassword() {
    const pwd = this.password.value!="" ? this.password.value.trim() : false;
    const newpwd = this.newPassword.value!="" ? this.newPassword.value.trim() : false;
    const repeatpwd = this.repeatPassword.value!="" ? this.repeatPassword.value.trim() : false;
    if (this.validatePassword(pwd,newpwd,repeatpwd)) {
      this.usuarioService.changePasswordOperador(pwd, newpwd).then(result => {
        if(result) {
          this.showMessage(result, "Contraseña actualizada");
          this.autenticacionService.logout();
        } else {
          this.showMessage(result, "Contraseña no actualizada, verifique su contraseña actual");
        }
      });
    }
  }

  private validatePassword(pwd:string, newpwd:string, repeatpwd:string): boolean {
    let result: boolean = false;
    if (pwd) {
      if (newpwd==repeatpwd) {
        if (newpwd.length>5) {
          result = true;
        } else { this.showMessage(result, "El password nuevo debe tener al menos 6 caracteres"); }
      } else { this.showMessage(result, "Los passwords no coinciden"); }
    } else { this.showMessage(result, "Ingrese el password actual"); }
    return result;
  }

  private showMessage(result:Boolean, message:string) {
    this.alertCtrl.create({
      title: result ? 'Éxito' : 'Advertencia',
      subTitle: message,
      buttons: ['Aceptar']
    }).present();
  }

}
