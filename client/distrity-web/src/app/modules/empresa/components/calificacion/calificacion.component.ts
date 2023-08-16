import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

import { Calificacion } from '../../models/calificacion';

import { CalificacionService } from '../../providers/calificacion.service';

@Component({
  selector: 'app-calificacion',
  templateUrl: './calificacion.component.html',
  styleUrls: ['./calificacion.component.css']
})
export class CalificacionComponent implements OnInit {

  calificaciones: Calificacion[];
  @Input() selectedCalificacion: Calificacion;

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
    private calificacionService: CalificacionService) {
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

  getCalificaciones(): void {
    this.calificacionService.getAll().then(calificaciones => this.calificaciones = calificaciones);
  }

  searchByTerm(): void {
    this.calificacionService.searchByTerm(this.term).then(calificaciones => {
      this.calificaciones = calificaciones;
      this.total = calificaciones.length;
    });
    this.searchTime = new Date();
  }

  removeAction(calificacion: Calificacion): void {
    this.delete(calificacion);
  }

  newAction(): void {
    this.selectedCalificacion = new Calificacion();
    this.modalTitle = "Nuevo";
    this.openModal();
  }

  editAction(calificacion: Calificacion): void {
    this.selectedCalificacion = calificacion;
    this.modalTitle = "Modificar";
    this.openModal();
  }

  save() {
    if (this.selectedCalificacion!=null || this.selectedCalificacion!=undefined) {
      if (this.selectedCalificacion.id) {
        this.calificacionService.update(this.selectedCalificacion).then(result => {
          if (result) { toast("Tipo de calificacion " + this.selectedCalificacion.codigo + " editado", 4000); }
          this.searchByTerm();
          this.selectedCalificacion = null;
        });
      } else {
        this.calificacionService.create(this.selectedCalificacion).then(result => {
          if (result) { toast("Tipo de calificacion " + this.selectedCalificacion.codigo + " creado", 4000); }
          this.searchByTerm();
          this.selectedCalificacion = null;
        });
      }
    }
  }

  private delete(calificacion: Calificacion): void {
    this.calificacionService.delete(calificacion.id)
      .then(() => {
        this.searchByTerm();
      });
  }

  pageChanged(currentPage: number) {
    this.p = currentPage>0 ? currentPage: 1;
  }
  
}
