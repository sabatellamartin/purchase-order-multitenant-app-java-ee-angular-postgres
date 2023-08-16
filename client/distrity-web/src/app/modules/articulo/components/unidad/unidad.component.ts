import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

import { Unidad } from '../../models/unidad';

import { UnidadService } from '../../providers/unidad.service';

@Component({
  selector: 'app-unidad',
  templateUrl: './unidad.component.html',
  styleUrls: ['./unidad.component.css']
})
export class UnidadComponent implements OnInit {

  unidades: Unidad[];
  @Input() selectedUnidad: Unidad;

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
    private unidadService: UnidadService) {
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

  getUnidads(): void {
    this.unidadService.getAll().then(unidades => this.unidades = unidades);
  }

  searchByTerm(): void {
    this.unidadService.searchByTerm(this.term).then(unidades => {
      this.unidades = unidades;
      this.total = unidades.length;
    });
    this.searchTime = new Date();
  }

  removeAction(unidad: Unidad): void {
    this.delete(unidad);
  }

  newAction(): void {
    this.selectedUnidad = new Unidad();
    this.modalTitle = "Nuevo";
    this.openModal();
  }

  editAction(unidad: Unidad): void {
    this.selectedUnidad = unidad;
    this.modalTitle = "Modificar";
    this.openModal();
  }

  save() {
    if (this.selectedUnidad!=null || this.selectedUnidad!=undefined) {
      if (this.selectedUnidad.id) {
        this.unidadService.update(this.selectedUnidad).then(result => {
          if (result) { toast("Tipo de unidad " + this.selectedUnidad.codigo + " editado", 4000); }
          this.searchByTerm();
          this.selectedUnidad = null;
        });
      } else {
        this.unidadService.create(this.selectedUnidad).then(result => {
          if (result) { toast("Tipo de unidad " + this.selectedUnidad.codigo + " creado", 4000); }
          this.searchByTerm();
          this.selectedUnidad = null;
        });
      }
    }
  }

  private delete(unidad: Unidad): void {
    this.unidadService.delete(unidad.id)
      .then(() => {
        this.searchByTerm();
      });
  }

  pageChanged(currentPage: number) {
    this.p = currentPage>0 ? currentPage: 1;
  }

}
