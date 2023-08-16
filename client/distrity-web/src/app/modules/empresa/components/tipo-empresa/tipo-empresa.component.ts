import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

import { TipoEmpresa } from '../../models/tipo-empresa';

import { TipoEmpresaService } from '../../providers/tipo-empresa.service';

@Component({
  selector: 'app-tipo-empresa',
  templateUrl: './tipo-empresa.component.html',
  styleUrls: ['./tipo-empresa.component.css']
})
export class TipoEmpresaComponent implements OnInit {

  tipoEmpresas: TipoEmpresa[];
  @Input() selectedTipoEmpresa: TipoEmpresa;

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
    private tipoEmpresaService: TipoEmpresaService) {
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

  getTipoEmpresas(): void {
    this.tipoEmpresaService.getAll().then(tipoEmpresas => this.tipoEmpresas = tipoEmpresas);
  }

  searchByTerm(): void {
    this.tipoEmpresaService.searchByTerm(this.term).then(tipoEmpresas => {
      this.tipoEmpresas = tipoEmpresas;
      this.total = tipoEmpresas.length;
    });
    this.searchTime = new Date();
  }

  removeAction(tipoEmpresa: TipoEmpresa): void {
    this.delete(tipoEmpresa);
  }

  newAction(): void {
    this.selectedTipoEmpresa = new TipoEmpresa();
    this.modalTitle = "Nuevo";
    this.openModal();
  }

  editAction(tipoEmpresa: TipoEmpresa): void {
    this.selectedTipoEmpresa = tipoEmpresa;
    this.modalTitle = "Modificar";
    this.openModal();
  }

  save() {
    if (this.selectedTipoEmpresa!=null || this.selectedTipoEmpresa!=undefined) {
      if (this.selectedTipoEmpresa.id) {
        this.tipoEmpresaService.update(this.selectedTipoEmpresa).then(result => {
          if (result) { toast("Tipo de empresa " + this.selectedTipoEmpresa.sigla + " editado", 4000); }
          this.searchByTerm();
          this.selectedTipoEmpresa = null;
        });
      } else {
        this.tipoEmpresaService.create(this.selectedTipoEmpresa).then(result => {
          if (result) { toast("Tipo de empresa " + this.selectedTipoEmpresa.sigla + " creado", 4000); }
          this.searchByTerm();
          this.selectedTipoEmpresa = null;
        });
      }
    }
  }

  private delete(tipoEmpresa: TipoEmpresa): void {
    this.tipoEmpresaService.delete(tipoEmpresa.id)
      .then(() => {
        this.searchByTerm();
      });
  }

  pageChanged(currentPage: number) {
    this.p = currentPage>0 ? currentPage: 1;
  }

}
