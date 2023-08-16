import { Component, OnInit, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';

import { Articulo } from '../../models/articulo';

import { ArticuloService } from '../../providers/articulo.service';

@Component({
  selector: 'seleccion-articulo',
  templateUrl: './seleccion-articulo.component.html',
  styleUrls: ['./seleccion-articulo.component.css']
})
export class SeleccionArticuloComponent implements OnInit {

  articulos: Articulo[];
  term: string = "";

  // Paginator
  p: number = 1;
  total: number = 1;

  @Output('seleccion') seleccion: EventEmitter<any> = new EventEmitter();

  constructor(
    private router: Router,
    private articuloService: ArticuloService) {
  }

  ngOnInit() {
    this.searchByTerm();
  }

  pageChanged(currentPage: number) {
    this.p = currentPage>0 ? currentPage: 1;
  }

  setTerm(term: string) {
    this.term = term;
  }

  searchByTerm(): void {
    this.articuloService.searchByTerm(this.term).then(articulos => {
      this.articulos = articulos;
      this.total = articulos.length;
    });
  }

  selectAction(articulo: Articulo): void {
    this.seleccion.emit(articulo);
  }

}
