import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

import { TipoDocumento } from '../../models/tipo-documento';

import { TipoDocumentoService } from '../../providers/tipo-documento.service';

@Component({
  selector: 'app-tipo-documento',
  templateUrl: './tipo-documento.component.html',
  styleUrls: ['./tipo-documento.component.css']
})
export class TipoDocumentoComponent implements OnInit {

  tipoDocumentos: TipoDocumento[];
  @Input() selectedTipoDocumento: TipoDocumento;

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
    private tipoDocumentoService: TipoDocumentoService) {
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

  getTipoDocumentos(): void {
    this.tipoDocumentoService.getAll().then(tipoDocumentos => this.tipoDocumentos = tipoDocumentos);
  }

  searchByTerm(): void {
    this.tipoDocumentoService.searchByTerm(this.term).then(tipoDocumentos => {
      this.tipoDocumentos = tipoDocumentos;
      this.total = tipoDocumentos.length;
    });
    this.searchTime = new Date();
  }

  removeAction(tipoDocumento: TipoDocumento): void {
    this.delete(tipoDocumento);
  }

  newAction(): void {
    this.selectedTipoDocumento = new TipoDocumento();
    this.modalTitle = "Nuevo";
    this.openModal();
  }

  editAction(tipoDocumento: TipoDocumento): void {
    this.selectedTipoDocumento = tipoDocumento;
    this.modalTitle = "Modificar";
    this.openModal();
  }

  save() {
    if (this.selectedTipoDocumento!=null || this.selectedTipoDocumento!=undefined) {
      if (this.selectedTipoDocumento.id) {
        this.tipoDocumentoService.update(this.selectedTipoDocumento).then(result => {
          if (result) { toast("Tipo de documento " + this.selectedTipoDocumento.sigla + " editado", 4000); }
          this.searchByTerm();
          this.selectedTipoDocumento = null;
        });
      } else {
        this.tipoDocumentoService.create(this.selectedTipoDocumento).then(result => {
          if (result) { toast("Tipo de documento " + this.selectedTipoDocumento.sigla + " creado", 4000); }
          this.searchByTerm();
          this.selectedTipoDocumento = null;
        });
      }
    }
  }

  private delete(tipoDocumento: TipoDocumento): void {
    this.tipoDocumentoService.delete(tipoDocumento.id)
      .then(() => {
        this.searchByTerm();
      });
  }

  pageChanged(currentPage: number) {
    this.p = currentPage>0 ? currentPage: 1;
  }

}
