import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';

import { FormControl } from '@angular/forms';

import 'rxjs/add/operator/debounceTime';

import { DocumentoService } from '../../../documento/providers/documento.service';
import { ArticuloService } from '../../../articulo/providers/articulo.service';
import { EmpresaService } from '../../../empresa/providers/empresa.service';
import { MonedaService } from '../../../documento/providers/moneda.service';

import { Documento } from '../../../documento/models/documento';
import { OrdenVenta } from '../../../documento/models/orden-venta';
import { Articulo } from '../../../articulo/models/articulo';
import { Cliente } from '../../../empresa/models/cliente';
import { OrdenVentaFilter } from '../../../documento/models/filter/ordenventafilter';
import { Moneda } from '../../../documento/models/moneda';

import { VerDocumentoPage } from '../../../documento/pages/ver-documento/ver-documento';
import { VerArticuloPage } from '../../../articulo/pages/ver-articulo/ver-articulo';
import { VerClientePage } from '../../../empresa/pages/ver-cliente/ver-cliente';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  title: string = "Distribution";

  searchTerm: string = "";
  searchControl: FormControl;
  searching: any = false;

  selectedFrom: string;
  selectedTo: string;

  tipoDocumento: string = 'OrdenVenta';

  documentos: Documento[] = new Array<Documento>();
  articulos: Articulo[] = new Array<Articulo>();
  clientes: Cliente[] = new Array<Cliente>();

  segments:string [] = ["PEDIDOS","ARTICULOS","CLIENTES"];
  segmentBusqueda: string = this.segments[0];

  selectedMoneda: Moneda = new Moneda();

  constructor(
    public navCtrl: NavController,
    private documentoService: DocumentoService,
    private articuloService: ArticuloService,
    private empresaService: EmpresaService,
    private monedaService: MonedaService) {
    this.searchControl = new FormControl();
    this.selectMonedas();
  }

  ionViewDidEnter() {
    this.searchPedidos();
    this.searchArticulos();
    this.searchClientes();
    this.searchControl.valueChanges.debounceTime(1000).subscribe(search  => {
      this.searching = false;
      this.syncSearch();
    });
  }

  private selectMonedas() : void {
    this.monedaService.getAllMonedas().then(monedas => {
      if (monedas.length>0) {
        this.selectedMoneda = monedas[0];
      }
    });
  }

  onSearchInput(){
   this.searching = true;
  }

  syncSearch () {
    if (this.segmentBusqueda == "PEDIDOS") {
      this.searchPedidos();
    } else if (this.segmentBusqueda == "ARTICULOS") {
      this.searchArticulos();
    } else if (this.segmentBusqueda == "CLIENTES") {
      this.searchClientes();
    }
  }

  searchPedidos(): void {
    const filter: OrdenVentaFilter = this.getCurrentFilter();
    this.documentoService.searchOrdenVentaFilter(filter).then(response => {
      if (response) {
        this.documentos = response.resultList as OrdenVenta[];
        //let cantidad = response.totalItems;
      }
    });
  }

  searchArticulos() {
    this.articuloService.getArticulos(this.searchTerm).then(articulos => {
      this.articulos = articulos;
    });
  }

  searchClientes() {
    this.empresaService.searchClienteByTerm(this.searchTerm).then(clientes => {
      this.clientes = clientes;
    });
  }

  private getCurrentFilter() {
    let filter: OrdenVentaFilter = new OrdenVentaFilter();
    filter.operadorId = null;
    filter.clienteId = null;
    filter.term = this.searchTerm ? this.searchTerm : "";
    filter.from = this.selectedFrom ? new Date(this.selectedFrom) : filter.from;
    filter.to = this.selectedTo ? new Date(this.selectedTo) : filter.to;
    filter.limit = filter.limit < 5 ? 5 : filter.limit;
    filter.paginatorRequest.pageSize = 20;
    filter.paginatorRequest.firstResult = 0;
    return filter;
  }

  verDocumento(documento: Documento) {
    this.navCtrl.push(VerDocumentoPage, {
      documento_id: documento.id
    });
  }

  verArticulo(articulo: Articulo) {
    this.navCtrl.push(VerArticuloPage, {
      articulo_id: articulo.id
    });
  }

  verCliente(cliente: Cliente) {
    this.navCtrl.push(VerClientePage, {
      cliente_id: cliente.id
    });
  }

  onSegmentChange() {
    this.searchTerm = "";
    this.syncSearch();
  }

  swipeEvent(e) {
    if (e.direction == '2') { // Derecha
      if (this.segmentBusqueda == this.segments[0]) {
        this.segmentBusqueda = this.segments[1];
      } else if (this.segmentBusqueda == this.segments[1]) {
        this.segmentBusqueda = this.segments[2];
      } else if (this.segmentBusqueda == this.segments[2]) {
        this.segmentBusqueda = this.segments[0];
      }
    } else if (e.direction == '4') { // Izquierda
      if (this.segmentBusqueda == this.segments[2]) {
        this.segmentBusqueda = this.segments[1];
      } else if (this.segmentBusqueda == this.segments[1]) {
        this.segmentBusqueda = this.segments[0];
      } else if (this.segmentBusqueda == this.segments[0]) {
        this.segmentBusqueda = this.segments[2];
      }
    }
    this.onSegmentChange();
  }

}
