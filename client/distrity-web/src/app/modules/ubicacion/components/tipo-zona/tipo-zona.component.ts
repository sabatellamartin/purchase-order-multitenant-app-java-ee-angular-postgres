import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

import { TipoZona } from '../../models/tipo-zona';

import { TipoZonaService } from '../../providers/tipo-zona.service';

@Component({
  selector: 'app-tipo-zona',
  templateUrl: './tipo-zona.component.html',
  styleUrls: ['./tipo-zona.component.css']
})
export class TipoZonaComponent implements OnInit {

  tipoZonas: TipoZona[];
  @Input() selectedTipoZona: TipoZona;

  term: string = "";
  searchTime: Date =  new Date();

  modalForm = new EventEmitter<string|MaterializeAction>();

  // Modal Title
  modalTitle: string = "";

  constructor(
    private router: Router,
    private tipoZonaService: TipoZonaService) {
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

  getTipoZonas(): void {
    this.tipoZonaService.getAll().then(tipoZonas => this.tipoZonas = tipoZonas);
  }

  searchByTerm(): void {
    this.tipoZonaService.searchByTerm(this.term).then(tipoZonas => {
      this.tipoZonas = tipoZonas;
    });
    this.searchTime = new Date();
  }

  removeAction(tipoZona: TipoZona): void {
    this.delete(tipoZona);
  }

  newAction(): void {
    this.selectedTipoZona = new TipoZona();
    this.modalTitle = "Nuevo";
    this.openModal();
  }

  editAction(tipoZona: TipoZona): void {
    this.selectedTipoZona = tipoZona;
    this.modalTitle = "Modificar";
    this.openModal();
  }

  save() {
    if (this.selectedTipoZona!=null || this.selectedTipoZona!=undefined) {
      if (this.selectedTipoZona.id) {
        this.tipoZonaService.update(this.selectedTipoZona).then(result => {
          if (result) { toast("Tipo de zona " + this.selectedTipoZona.codigo + " editado", 4000); }
          this.searchByTerm();
          this.selectedTipoZona = null;
        });
      } else {
        this.tipoZonaService.create(this.selectedTipoZona).then(result => {
          if (result) { toast("Tipo de zona " + this.selectedTipoZona.codigo + " creado", 4000); }
          this.searchByTerm();
          this.selectedTipoZona = null;
        });
      }
    }
  }

  private delete(tipoZona: TipoZona): void {
    this.tipoZonaService.delete(tipoZona.id)
      .then(() => {
        this.searchByTerm();
      });
  }

}
