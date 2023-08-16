import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { FormControl } from '@angular/forms';

import 'rxjs/add/operator/debounceTime';

// Plugins
import { LoadingController } from 'ionic-angular';
import { Events } from 'ionic-angular'

// Pages
import { EditarDetallePage } from '../editar-detalle/editar-detalle';

// Providers
import { ArticuloService } from '../../../articulo/providers/articulo.service';
import { DocumentoService } from '../../providers/documento.service';

import { Articulo } from '../../../articulo/models/articulo';
import { Detalle } from '../../models/detalle';

@Component({
  selector: 'page-seleccion-articulo',
  templateUrl: 'seleccion-articulo.html'
})
export class SeleccionArticuloPage {

  articulos: Articulo[];
  selectedArticulo: Articulo;

  searchTerm: string = "";
  searchTime: Date =  new Date();
  searchControl: FormControl;
  searching: any = false;

  documentoLineas: Detalle[];

  constructor(
    public navCtrl: NavController,
    public loadingCtrl: LoadingController,
    public events:Events,
  	private articuloService: ArticuloService,
    private documentoService: DocumentoService) {
    this.searchControl = new FormControl();
  }

  ionViewDidEnter() {
    this.loadDetalles();
    this.syncSearch();
    this.searchControl.valueChanges.debounceTime(1000).subscribe(search  => {
      this.searching = false;
      this.syncSearch();
    });
  }

  syncSearch(): void {
    this.articuloService.searchByTerm(this.searchTerm).then(articulos => {
      this.articulos = articulos;
    });
    this.searchTime = new Date();
  }

  onSearchInput(){
   this.searching = true;
  }

  private loadDetalles() {
    this.documentoService.getOrden().then(orden => {
      if (orden) { this.documentoLineas = orden.detalles as Detalle[]; }
    });
  }

  selectArticulo(articulo:Articulo) {
    if (this.isSelected(articulo)) { this.removeArticulo(articulo); }
    let detalle = new Detalle();
    detalle.cantidad = 1;
    detalle.descuento = 0;
    detalle.precio = articulo.precioVenta;
    detalle.articulo = articulo;
    this.documentoService.getOrden().then(orden => {
      orden.detalles.push(detalle);
      this.documentoService.setOrden(orden).then(response => {
        if (response) {
          this.goBackAndReload();
          this.editDetalle(articulo.id);
        }
      });
    });
  }

  goBackAndReload() {
    this.events.publish('reloadDetails');
    this.navCtrl.pop();
  }

  editDetalle(id: number) {
    this.navCtrl.push(EditarDetallePage, {
      articulo_id: id
    });
  }

  removeArticulo(articulo: Articulo) {
    this.documentoService.getOrden().then(orden => {
      orden.detalles = orden.detalles.filter(detalle => detalle.articulo.id !== articulo.id);
      this.documentoLineas = orden.detalles;
      this.documentoService.setOrden(orden).then(result => {
        if (result) {
          this.syncSearch();
        }
      });
    });
  }

  isSelected(articulo: Articulo) {
    return this.documentoLineas.find(detalle => detalle.articulo.id === articulo.id);
  }

  swipeEvent(e) {
    if (e.direction == '2') { // Derecha
    } else if (e.direction == '4') { // Izquierda
      this.goBackAndReload();
    }
  }

}
