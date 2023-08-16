import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

import { Moneda } from '../../models/moneda';

import { MonedaService } from '../../providers/moneda.service';

@Component({
  selector: 'app-moneda',
  templateUrl: './moneda.component.html',
  styleUrls: ['./moneda.component.css']
})
export class MonedaComponent implements OnInit {

  monedas: Moneda[];
  @Input() selectedMoneda: Moneda;

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
    private monedaService: MonedaService) {
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

  getMonedas(): void {
    this.monedaService.getAll().then(monedas => this.monedas = monedas);
  }

  searchByTerm(): void {
    this.monedaService.searchByTerm(this.term).then(monedas => {
      this.monedas = monedas;
      this.total = monedas.length;
    });
    this.searchTime = new Date();
  }

  removeAction(moneda: Moneda): void {
    this.delete(moneda);
  }

  newAction(): void {
    this.selectedMoneda = new Moneda();
    this.modalTitle = "Nuevo";
    this.openModal();
  }

  editAction(moneda: Moneda): void {
    this.selectedMoneda = moneda;
    this.modalTitle = "Modificar";
    this.openModal();
  }

  save() {
    if (this.selectedMoneda!=null || this.selectedMoneda!=undefined) {
      if (this.selectedMoneda.id) {
        this.monedaService.update(this.selectedMoneda).then(result => {
          if (result) { toast("Tipo de moneda " + this.selectedMoneda.sigla + " editado", 4000); }
          this.searchByTerm();
          this.selectedMoneda = null;
        });
      } else {
        this.monedaService.create(this.selectedMoneda).then(result => {
          if (result) { toast("Tipo de moneda " + this.selectedMoneda.sigla + " creado", 4000); }
          this.searchByTerm();
          this.selectedMoneda = null;
        });
      }
    }
  }

  private delete(moneda: Moneda): void {
    this.monedaService.delete(moneda.id)
      .then(() => {
        this.searchByTerm();
      });
  }

  pageChanged(currentPage: number) {
    this.p = currentPage>0 ? currentPage: 1;
  }


}
