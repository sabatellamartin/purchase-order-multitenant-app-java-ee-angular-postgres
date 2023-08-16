import { Component } from '@angular/core';
import { NavController, NavParams, AlertController, LoadingController } from 'ionic-angular';

import 'rxjs/add/operator/debounceTime';

import { Articulo } from '../../models/articulo';
import { Moneda } from '../../../documento/models/moneda';

import { ArticuloService } from '../../providers/articulo.service';
import { MonedaService } from '../../../documento/providers/moneda.service';

@Component({
  selector: 'page-ver-articulo',
  templateUrl: 'ver-articulo.html'
})
export class VerArticuloPage {

  selectedArticulo: Articulo = new Articulo();

  selectedMoneda: Moneda = new Moneda();

  estados: string[] = ["activo","discontinuo"];
  selectedEstado: string = this.estados[0];

  cantidad: number = 1;
  descuento: number = 0;

  precioUnitario: number = 0;
  precioImpuesto: number = 0;
  precioParcial: number = 0;

  loader: any;

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public alertCtrl: AlertController,
    public loadingCtrl: LoadingController,
    private articuloService: ArticuloService,
    private monedaService: MonedaService){
    this.selectMonedas();
  }

  swipeEvent(e) {
    if (e.direction == '2') { // Derecha
      //alert("Derecha");
    } else if (e.direction == '4') { /* Izquierda*/
      this.navCtrl.pop();
    }
  }

  presentLoading() {
    this.loader = this.loadingCtrl.create({
      content: "Cargando...",
      duration: 3000
    });
    this.loader.present();
  }

  ionViewDidEnter() {
    this.presentLoading();
    let id = this.navParams.get('articulo_id');
    if (id) {
      this.articuloService.getById(id).then(articulo => {
        this.selectedArticulo = articulo;
        this.getEstado();

        this.updatePrecios();
        this.loader.dismiss();

      });
    }
  }

  private getEstado() : void {
    let articulo: Articulo = this.selectedArticulo;
    let today: Date = new Date();
    if (!articulo.fechaBaja && articulo.fechaAlta < today) {
      this.selectedEstado =  this.estados[0];
    }
    if (articulo.fechaBaja && articulo.fechaBaja < today) {
      this.selectedEstado =  this.estados[1];
    }
  }

  private selectMonedas() : void {
    this.monedaService.getAllMonedas().then(monedas => {
      if (monedas.length>0) {
        this.selectedMoneda = monedas[0];
      }
    });
  }


  /* CANTIDAD */
  modifyCantidad(e) {
    if (e.direction == 2) {
      //direction 2 = right to left swipe.
      this.decrement();
    } else if (e.direction == 4) {
      //direction 2 = left to right swipe.
      this.increment();
    }
  }
  increment () {
    this.cantidad++;
    this.updatePrecios();
  }
  decrement () {
    if (this.cantidad > 1) {
      this.cantidad--;
    }
    this.updatePrecios();
  }
  validateCantidad(): void {
    this.cantidad = this.cantidad<1 ? 1 : this.cantidad;
    this.cantidad = this.cantidad>999999 ? 999999 : this.cantidad;
  }

  descuentoChanged(descuento:number) {
    this.updatePrecios();
  }

  cantidadChanged(cantidad:number) {
    this.updatePrecios();
  }

  private updatePrecios() {
    this.precioUnitario = this.redondeoPrecio(this.selectedArticulo.precioVenta*(1-(this.descuento/100)));
    this.precioImpuesto = this.redondeoPrecio(this.precioUnitario*(1+(this.selectedArticulo.impuesto.porcentaje/100)));
    this.precioParcial = this.redondeoPrecio(this.precioImpuesto*this.cantidad);
  }

  redondeoPrecio(precio:number): number {
    return Math.round(precio * 100) / 100;
  }

}
