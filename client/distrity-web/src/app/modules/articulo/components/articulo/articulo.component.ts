import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

import { Articulo } from '../../models/articulo';
import { Unidad } from '../../models/unidad';
import { Categoria } from '../../models/categoria';
import { Impuesto } from '../../models/impuesto';

import { ArticuloFilter } from '../../models/filters/articulo-filter';

import { ArticuloService } from '../../providers/articulo.service';
import { UnidadService } from '../../providers/unidad.service';
import { CategoriaService } from '../../providers/categoria.service';
import { ImpuestoService } from '../../providers/impuesto.service';

@Component({
  selector: 'app-articulo',
  templateUrl: './articulo.component.html',
  styleUrls: ['./articulo.component.css']
})
export class ArticuloComponent implements OnInit {

  articulos: Articulo[];
  @Input() selectedArticulo: Articulo;

  unidad: Unidad;
  unidades: Unidad[];

  categoria: Categoria;
  categorias: Categoria[];

  impuesto: Impuesto;
  impuestos: Impuesto[];

  modalForm = new EventEmitter<string|MaterializeAction>();
  modalTitle: string = "";

  term: string = "";
  // PAGINACION
  currentPage: number = 1;
  totalItems: number = 100;
  itemsPerPage: number = 5;

  constructor(
    private router: Router,
    private articuloService: ArticuloService,
    private unidadService: UnidadService,
    private categoriaService: CategoriaService,
    private impuestoService: ImpuestoService) {
  }

  ngOnInit() {
    this.getUnidades();
    this.getCategorias();
    this.getImpuestos();
    this.searchByTerm();
  }

  // Pager
  currentPageChanged(currentPage: number) {
    this.currentPage = currentPage>0 ? currentPage: 1;
    this.searchByTerm();
  }
  // Pager
  itemsPerPageChanged(items: number) {
    this.itemsPerPage = items<50?items:50;
    this.searchByTerm();
  }

  private getUnidades(): void {
     this.unidadService.getAll().then(response => {
       this.unidades = response;
     });
  }

  private getCategorias(): void {
     this.categoriaService.getAll().then(response => {
       this.categorias = response;
     });
  }

  private getImpuestos(): void {
     this.impuestoService.getAll().then(response => {
       this.impuestos = response;
     });
  }

  compareFn( optionOne, optionTwo ) : boolean {
    if (optionOne && optionTwo) {
      return optionOne.id === optionTwo.id;
    } else { return false; }
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

  getArticulos(): void {
    this.articuloService.getAll().then(articulos => this.articulos = articulos);
  }

  searchByTerm(): void {
    let filter = new ArticuloFilter();
    filter.categoriaId = null;
    filter.impuestoId = null;
    filter.unidadId = null;
    filter.minPrecio = 0;
    filter.maxPrecio = null;
    filter.term = this.term;
    filter.from = null;
    filter.to = new Date();
    // Pager Filter
    filter.paginatorRequest.firstResult = (this.currentPage-1) * this.itemsPerPage;
    filter.paginatorRequest.pageSize = this.itemsPerPage;
    this.articuloService.searchFilter(filter).then(response => {
      if (response) {
        this.articulos = response.resultList as Articulo[];
        this.totalItems = response.totalItems;
      }
    });
  }

  removeAction(articulo: Articulo): void {
    this.delete(articulo);
  }

  newAction(): void {
    this.selectedArticulo = new Articulo();
    this.selectedArticulo.unidad = this.unidades[0];
    this.selectedArticulo.categoria = this.categorias[0];
    this.selectedArticulo.impuesto = this.impuestos[0];
    this.modalTitle = "Nuevo";
    this.openModal();
  }

  editAction(articulo: Articulo): void {
    this.selectedArticulo = articulo;
    this.modalTitle = "Modificar";
    this.openModal();
  }

  save() {
    if (this.selectedArticulo!=null || this.selectedArticulo!=undefined) {
      if (this.selectedArticulo.id) {
        this.articuloService.update(this.selectedArticulo).then(result => {
          if (result) { toast("Tipo de articulo " + this.selectedArticulo.codigo + " editado", 4000); }
          this.searchByTerm();
          this.selectedArticulo = null;
        });
      } else {
        this.articuloService.create(this.selectedArticulo).then(result => {
          if (result) { toast("Tipo de articulo " + this.selectedArticulo.codigo + " creado", 4000); }
          this.searchByTerm();
          this.selectedArticulo = null;
        });
      }
    }
  }

  private delete(articulo: Articulo): void {
    this.articuloService.delete(articulo.id)
      .then(() => {
        this.searchByTerm();
      });
  }

}
