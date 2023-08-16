import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

// Cryptojs
import * as crypto from 'crypto-js';

import { Operador } from '../../models/operador';
import { UsuarioService } from '../../providers/usuario.service';

@Component({
  selector: 'auth-operador',
  templateUrl: './operador.component.html',
  styleUrls: ['./operador.component.css']
})
export class OperadorComponent implements OnInit {

  usuarios: Operador[];
  @Input() selectedUsuario: Operador;

  roles: Array<string> = ['PROPIETARIO','VENTAS'];
  selectedRol: string = this.roles[0];

  term: string = "";
  searchTime: Date =  new Date();

  modalForm = new EventEmitter<string|MaterializeAction>();

  // Modal Title
  modalTitle: string = "";

  // Paginator
  p: number = 1;
  total: number = 1;

  constructor(
    private router: Router,
    private usuarioService: UsuarioService) {
  }

  ngOnInit() {
   this.syncSearch();
  }

  pageChanged(currentPage: number) {
    this.p = currentPage>0 ? currentPage: 1;
  }

  openModal() {
    this.modalForm.emit({action:"modal",params:['open']});
  }

  closeModal() {
    this.modalForm.emit({action:"modal",params:['close']});
  }

  setTerm(term: string) {
    this.term = term;
  }

  syncSearch() {
    this.usuarioService.searchOperadoresByRol(this.term, this.selectedRol).then(usuarios => {
      this.usuarios = usuarios;
      this.total = usuarios.length;
    });
    this.searchTime = new Date();
  }

  existByEmail(email:string) {
    if (email) {
      this.usuarioService.existByEmail(email.trim()).then(result => {
         if(result) { toast("Email " + email + " ya existe", 4000); }
       });
     }
  }

  onChangeRol(rol : string): void {
    this.selectedRol = rol;
    this.syncSearch();
  }

  removeAction(usuario: Operador): void {
    this.usuarioService.deleteOperador(usuario.id).then(() => {
        this.syncSearch();
    });
  }

  restorePasswordAction(usuario: Operador): void {
    if (usuario.id) {
      this.usuarioService.restorePasswordOperador(usuario.id).then(result => {
        if (result) {
          alert("El password del usuario " + usuario.username + " ahora es: " + result);
          toast("Se regenero el password del usuario " + usuario.username , 4000);
        }
        this.syncSearch();
      });
    }
  }

  toggleLockAction(usuario: Operador): void {
    if (usuario.id) {
      let status = usuario.bloqueado==null ? " bloqueado" : " desbloqueado";
      this.usuarioService.toggleLockOperador(usuario.id).then(result => {
        toast("Usuario " + usuario.email + status , 4000);
        this.syncSearch();
      });
    }
  }

  toggleBajaAction(usuario: Operador): void {
    if (usuario.id) {
      let status = usuario.baja==null ? " dado de baja" : " dado de alta";
      this.usuarioService.toggleBajaOperador(usuario.id).then(result => {
        toast("Usuario " + usuario.email + status , 4000);
        this.syncSearch();
      });
    }
  }

  newAction(): void {
    this.selectedUsuario = new Operador();
    this.modalTitle = this.selectedRol==this.roles[0] ? "Nuevo propietario":"Nuevo vendedor";
    this.openModal();
  }

  editAction(usuario: Operador): void {
    usuario.password = "";
    this.selectedUsuario = usuario;
    this.modalTitle = this.selectedRol==this.roles[0] ? "Editar propietario":"Editar vendedor";
    this.openModal();
  }

  save() {
    if (this.selectedUsuario!=null || this.selectedUsuario!=undefined) {
      this.selectedUsuario.email = this.selectedUsuario.email.trim();
      this.selectedUsuario.rol = this.selectedRol.trim().toUpperCase();
      //this.selectedUsuario.password = crypto.SHA512(this.selectedUsuario.password).toString(crypto.enc.Hex);
      if (this.selectedUsuario.id) {
        this.usuarioService.updateOperador(this.selectedUsuario).then(result => {
          if (result) { toast("Usuario nro. " + this.selectedUsuario.email + " editado", 4000); }
          this.syncSearch();
          this.selectedUsuario = null;
        });
      } else {
        this.usuarioService.createOperador(this.selectedUsuario).then(result => {
          if (result) { toast("Usuario nro. " + this.selectedUsuario.email + " creado", 4000); }
          this.syncSearch();
          this.selectedUsuario = null;
        });
      }
    }
  }

}
