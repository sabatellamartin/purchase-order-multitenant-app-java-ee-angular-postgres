import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

import { TipoDireccion } from '../../models/tipo-direccion';

import { TipoDireccionService } from '../../providers/tipo-direccion.service';

@Component({
  selector: 'app-tipo-direccion',
  templateUrl: './tipo-direccion.component.html',
  styleUrls: ['./tipo-direccion.component.css']
})
export class TipoDireccionComponent implements OnInit {

  tipoDirecciones: TipoDireccion[];
  @Input() selectedTipoDireccion: TipoDireccion;

  term: string = "";
  searchTime: Date =  new Date();

  modalForm = new EventEmitter<string|MaterializeAction>();

  // Modal Title
  modalTitle: string = "";

  constructor(
    private router: Router,
    private tipoDireccionService: TipoDireccionService) {
  }

  ngOnInit() {
    this.searchByTerm();
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
    this.searchByTerm();
  }

  getTipoDirecciones(): void {
    this.tipoDireccionService.getAll().then(tipoDirecciones => this.tipoDirecciones = tipoDirecciones);
  }

  searchByTerm(): void {
    this.tipoDireccionService.searchByTerm(this.term).then(tipoDirecciones => {
      this.tipoDirecciones = tipoDirecciones;
    });
    this.searchTime = new Date();
  }

  removeAction(tipoDireccion: TipoDireccion): void {
    this.delete(tipoDireccion);
  }

  newAction(): void {
    this.selectedTipoDireccion = new TipoDireccion();
    this.modalTitle = "Nuevo";
    this.openModal();
  }

  editAction(tipoDireccion: TipoDireccion): void {
    this.selectedTipoDireccion = tipoDireccion;
    this.modalTitle = "Modificar";
    this.openModal();
  }

  save() {
    if (this.selectedTipoDireccion!=null || this.selectedTipoDireccion!=undefined) {
      if (this.selectedTipoDireccion.id) {
        this.tipoDireccionService.update(this.selectedTipoDireccion).then(result => {
          if (result) { toast("Tipo de direcci&oacute;n " + this.selectedTipoDireccion.codigo + " editado", 4000); }
          this.searchByTerm();
          this.selectedTipoDireccion = null;
        });
      } else {
        this.tipoDireccionService.create(this.selectedTipoDireccion).then(result => {
          if (result) { toast("Tipo de direcci&oacute;n " + this.selectedTipoDireccion.codigo + " creado", 4000); }
          this.searchByTerm();
          this.selectedTipoDireccion = null;
        });
      }
    }
  }

  private delete(tipoDireccion: TipoDireccion): void {
    this.tipoDireccionService.delete(tipoDireccion.id)
      .then(() => {
        this.searchByTerm();
      });
  }

}
