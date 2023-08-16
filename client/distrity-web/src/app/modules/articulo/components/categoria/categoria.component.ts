import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

import { Categoria } from '../../models/categoria';

import { CategoriaService } from '../../providers/categoria.service';

@Component({
  selector: 'producto-categoria',
  templateUrl: './categoria.component.html',
  styleUrls: ['./categoria.component.css']
})
export class CategoriaComponent implements OnInit {

  categorias: Categoria[];
  @Input() selectedCategoria: Categoria;

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
    private categoriaService: CategoriaService) {
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

  getCategoriaes(): void {
    this.categoriaService.getAll().then(categorias => this.categorias = categorias);
  }

  searchByTerm(): void {
    this.categoriaService.searchByTerm(this.term).then(categorias => {
      this.categorias = categorias;
      this.total = categorias.length;
    });
    this.searchTime = new Date();
  }

  removeAction(categoria: Categoria): void {
    this.delete(categoria);
  }

  newAction(): void {
    this.selectedCategoria = new Categoria();
    this.modalTitle = "Nuevo";
    this.openModal();
  }

  editAction(categoria: Categoria): void {
    this.selectedCategoria = categoria;
    this.modalTitle = "Modificar";
    this.openModal();
  }

  save() {
    if (this.selectedCategoria!=null || this.selectedCategoria!=undefined) {
      if (this.selectedCategoria.id) {
        this.categoriaService.update(this.selectedCategoria).then(result => {
          if (result) { toast("Tipo de categoria " + this.selectedCategoria.nombre + " editado", 4000); }
          this.searchByTerm();
          this.selectedCategoria = null;
        });
      } else {
        this.categoriaService.create(this.selectedCategoria).then(result => {
          if (result) { toast("Tipo de categoria " + this.selectedCategoria.nombre + " creado", 4000); }
          this.searchByTerm();
          this.selectedCategoria = null;
        });
      }
    }
  }

  private delete(categoria: Categoria): void {
    this.categoriaService.delete(categoria.id)
      .then(() => {
        this.searchByTerm();
      });
  }

  pageChanged(currentPage: number) {
    this.p = currentPage>0 ? currentPage: 1;
  }

}
