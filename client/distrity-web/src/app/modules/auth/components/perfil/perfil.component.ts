import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

import { Usuario } from '../../models/usuario';
import { Sesion } from '../../models/sesion';
import { Operador } from '../../models/operador';

import { AutenticacionService } from '../../providers/autenticacion.service';
import { UsuarioService } from '../../providers/usuario.service';

class Password {
  clearOld: string;
  clearNew: string;
  repeatNew: string;
}

@Component({
  selector: 'auth-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {

  @Input() usuario: Usuario = new Usuario();
  sesion: Sesion;

  password: Password = new Password();

  modalForm = new EventEmitter<string|MaterializeAction>();
  modalTitle: string = "Cambiar password";

  constructor(
    private router: Router,
    private autenticacionService: AutenticacionService,
    private usuarioService: UsuarioService) {
  }

  ngOnInit() {
    this.getCurrent();
  }

  getCurrent(): void {
    this.sesion = this.autenticacionService.getSesion();
  }

  openModal() {
    this.modalForm.emit({action:"modal",params:['open']});
  }

  closeModal() {
    this.modalForm.emit({action:"modal",params:['close']});
  }

  changePasswordAction(): void {
    if (this.validatePassword()) {
      this.usuarioService.changePasswordOperador(this.password.clearOld, this.password.clearNew).then(result => {
        if(result) {
          toast("Password del usuario " + this.sesion.username + " actualizado" , 4000);
          this.autenticacionService.logout();
        } else {
          toast("Password del usuario " + this.sesion.username + " no se actualizó" , 4000);
        }
      });
    }
  }

  private validatePassword(): boolean {
    let result: boolean = false;
    if (this.password.clearOld) {
      if (this.password.clearNew==this.password.repeatNew) {
        if (this.password.clearNew.length>5) {
          result = true;
        } else { toast("El password nuevo debe tener al menos 6 caracteres" , 4000); }
      } else { toast("Los passwords no coinciden" , 4000); }
    } else { toast("Ingrese el password actual" , 4000); }
    return result;
  }

}
