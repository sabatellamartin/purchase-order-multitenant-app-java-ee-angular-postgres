import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

// Cryptojs
import * as crypto from 'crypto-js';

import { Usuario } from '../../models/usuario';
import { Administrador } from '../../models/administrador';
import { Operador } from '../../models/operador';
import { UsuarioService } from '../../providers/usuario.service';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {

  usuarios: Usuario[];
  @Input() selectedUsuario: Usuario;

  roles: Array<string> = [];
  selectedRol: string = 'OPERADOR';

  term: string = "";
  searchTime: Date =  new Date();

  datePickerParams: any;

  modalForm = new EventEmitter<string|MaterializeAction>();

  // Modal Title
  modalTitle: string = "";

  constructor(
    private router: Router,
    private usuarioService: UsuarioService) {
      // Load datepicker params internacionalization
      this.loadPickerParams();
  }

  ngOnInit() {
   this.cargarRoles();
   this.searchByRol();
  }

  private cargarRoles(): void {
     this.roles.push('ADMINISTRADOR');
     this.roles.push('OPERADOR');
  }

  private loadPickerParams(): void {
     this.datePickerParams = [{
        // The title label to use for the month nav buttons
        labelMonthNext: 'Mes siguiente',
        labelMonthPrev: 'Mes anterior',
        // The title label to use for the dropdown selectors
        labelMonthSelect: 'Selecciona un mes',
        labelYearSelect: 'Selecciona un año',
        // Months and weekdays
        monthsFull: [ 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre' ],
        monthsShort: [ 'Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic' ],
        weekdaysFull: [ 'Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado' ],
        weekdaysShort: [ 'Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab' ],
        // Materialize modified
        weekdaysLetter: [ 'D', 'L', 'M', 'X', 'J', 'V', 'S' ],
        // Today and clear
        today: 'Hoy',
        clear: 'Limpiar',
        close: 'Cerrar',
        // Format date
        format: 'dd/mm/yyyy'
     }];
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
    this.searchByRol();
  }

  getUsuarios(): void {
    this.usuarioService.getAll().then(usuarios => this.usuarios = usuarios);
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
    this.searchByRol();
  }

  searchByRol(): void {
    this.usuarioService.searchByRol(this.term, this.selectedRol).then(usuarios => {
      this.usuarios = usuarios;
    });
    this.searchTime = new Date();
  }

  removeAction(usuario: Usuario): void {
    this.delete(usuario);
  }

  newPasswordAction(usuario: Usuario): void {
    if (usuario.id) {
      /*this.usuarioService.recoverAccountById(usuario.id).then(result => {
        if (result) {
          toast("Se ha enviado un correo de recuperación de cuenta a " + usuario.email , 4000);
        }
        this.searchByRol();
      });*/
    }
  }

  toggleLockAction(usuario: Usuario): void {
    if (usuario.id) {
      let status = usuario.baja==null ? " bloqueado" : " desbloqueado";
      this.usuarioService.toggleLock(usuario.id).then(result => {
        toast("Usuario " + usuario.email + status , 4000);
        this.searchByRol();
      });
    }
  }

  newAction(): void {
    this.selectedUsuario = new Usuario();
    this.modalTitle = "Nuevo";
    this.openModal();
  }

  editAction(usuario: Usuario): void {
    usuario.password = "";
    this.selectedUsuario = usuario;
    this.modalTitle = "Modificar";
    this.openModal();
  }

  save() {
    if (this.selectedUsuario!=null || this.selectedUsuario!=undefined) {
      this.selectedUsuario.email = this.selectedUsuario.email.trim();
      //this.selectedUsuario.password = crypto.SHA512(this.selectedUsuario.password).toString(crypto.enc.Hex);
      if (this.selectedUsuario.id) {
        this.usuarioService.update(this.selectedUsuario, this.selectedRol).then(result => {
          if (result) { toast("Usuario nro. " + this.selectedUsuario.email + " editado", 4000); }
          this.searchByRol();
          this.selectedUsuario = null;
        });
      } else {
        this.usuarioService.create(this.selectedUsuario, this.selectedRol).then(result => {
          if (result) { toast("Usuario nro. " + this.selectedUsuario.email + " creado", 4000); }
          this.searchByRol();
          this.selectedUsuario = null;
        });
      }
    }
  }

  private delete(usuario: Usuario): void {
    this.usuarioService.delete(usuario.id)
      .then(() => {
        this.searchByRol();
      });
  }

}
