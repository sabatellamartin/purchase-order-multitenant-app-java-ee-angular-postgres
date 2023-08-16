import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

import { Impuesto } from '../../models/impuesto';

import { ImpuestoService } from '../../providers/impuesto.service';

@Component({
  selector: 'producto-impuesto',
  templateUrl: './impuesto.component.html',
  styleUrls: ['./impuesto.component.css']
})
export class ImpuestoComponent implements OnInit {

  impuestos: Impuesto[];
  @Input() selectedImpuesto: Impuesto;

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
    private impuestoService: ImpuestoService) {
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

  getImpuestoes(): void {
    this.impuestoService.getAll().then(impuestos => this.impuestos = impuestos);
  }

  searchByTerm(): void {
    this.impuestoService.searchByTerm(this.term).then(impuestos => {
      this.impuestos = impuestos;
      this.total = impuestos.length;
    });
    this.searchTime = new Date();
  }

  removeAction(impuesto: Impuesto): void {
    this.delete(impuesto);
  }

  newAction(): void {
    this.selectedImpuesto = new Impuesto();
    this.modalTitle = "Nuevo";
    this.openModal();
  }

  editAction(impuesto: Impuesto): void {
    this.selectedImpuesto = impuesto;
    this.modalTitle = "Modificar";
    this.openModal();
  }

  save() {
    if (this.selectedImpuesto!=null || this.selectedImpuesto!=undefined) {
      if (this.selectedImpuesto.id) {
        this.impuestoService.update(this.selectedImpuesto).then(result => {
          if (result) { toast("Tipo de impuesto " + this.selectedImpuesto.nombre + " editado", 4000); }
          this.searchByTerm();
          this.selectedImpuesto = null;
        });
      } else {
        this.impuestoService.create(this.selectedImpuesto).then(result => {
          if (result) { toast("Tipo de impuesto " + this.selectedImpuesto.nombre + " creado", 4000); }
          this.searchByTerm();
          this.selectedImpuesto = null;
        });
      }
    }
  }

  private delete(impuesto: Impuesto): void {
    this.impuestoService.delete(impuesto.id)
      .then(() => {
        this.searchByTerm();
      });
  }

  pageChanged(currentPage: number) {
    this.p = currentPage>0 ? currentPage: 1;
  }

}
