import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

// Plugins
import { Events } from 'ionic-angular'

import { AlertController } from 'ionic-angular';

// Providers
import { DocumentoService } from '../../providers/documento.service';
import { MonedaService } from '../../providers/moneda.service';

import { Detalle } from '../../models/detalle';
import { Moneda } from '../../models/moneda';

@Component({
  selector: 'page-editar-detalle',
  templateUrl: 'editar-detalle.html'
})
export class EditarDetallePage {

  selectedDetalle: Detalle;

  selectedMoneda: Moneda;

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public events:Events,
    public alertCtrl: AlertController,
    private monedaService: MonedaService,
    private documentoService: DocumentoService) {
    this.selectMonedas();
    this.selectedDetalle = new Detalle();
  }

  ionViewDidEnter() {
    let id = this.navParams.get('articulo_id');
    if (id) {
      this.loadDetalleByArticuloId(id);
    }
  }

  private selectMonedas() : void {
    this.monedaService.getAllMonedas().then(monedas => {
      if (monedas.length>0) {
        this.selectedMoneda = monedas[0];
      }
    });
  }

  private loadDetalleByArticuloId(id: number) {
    this.documentoService.getOrden().then(orden => {
      //this.selectedDetalle = orden.detalles.filter(detalle => detalle.articulo.id === id)[0] as Detalle;
      this.selectedDetalle = orden.detalles.find(detalle => detalle.articulo.id === id) as Detalle;
    });
  }


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
    this.selectedDetalle.cantidad++;
  }

  decrement () {
    if (this.selectedDetalle.cantidad > 1) {
      this.selectedDetalle.cantidad--;
    }
  }

  goBackAndReload() {
    this.events.publish('reloadDetails');
    this.navCtrl.pop();
  }

  updateDetalle() {
    this.documentoService.getOrden().then(orden => {
      orden.detalles = orden.detalles.filter(detalle => detalle.articulo.id !== this.selectedDetalle.articulo.id);
      this.validateCantidad();
      orden.detalles.push(this.selectedDetalle);
      this.documentoService.setOrden(orden).then(response => {
        if (response) {
          this.goBackAndReload();
        }
      });
    });
  }

  removeDetalle() {
    this.documentoService.getOrden().then(orden => {
      orden.detalles = orden.detalles.filter(detalle => detalle.articulo.id !== this.selectedDetalle.articulo.id);
      this.documentoService.setOrden(orden).then(response => {
        if (response) {
          this.goBackAndReload();
        }
      });
    });
  }

  precioParcial(detalle:Detalle): number {
    const cantidad = detalle.cantidad;
    return this.redondeoPrecio(this.precioImpuesto(detalle)*cantidad);
  }

  precioUnitario(detalle:Detalle): number {
    const precio = detalle.precio;
    const descuento = detalle.descuento;
    return this.redondeoPrecio(precio*(1-(descuento/100)));
  }

  precioImpuesto(detalle:Detalle): number {
    const impuesto = detalle.articulo.impuesto.porcentaje;
    const unitario = this.precioUnitario(detalle);
    return this.redondeoPrecio(unitario*(1+(impuesto/100)));
  }

  redondeoPrecio(precio:number): number {
    return Math.round(precio * 100) / 100;
  }

  parcialDescuento(detalle:Detalle): number {
    const precio = detalle.precio;
    const descuento = detalle.descuento;
    const cantidad = detalle.cantidad;
    const parcialDescuento = precio*(descuento/100)
    return this.redondeoPrecio(parcialDescuento*cantidad);
  }

  parcialImpuesto(detalle:Detalle): number {
    const impuesto = detalle.articulo.impuesto.porcentaje;
    const cantidad = detalle.cantidad;
    const parcialImpuesto = this.precioUnitario(detalle)*(impuesto/100);
    return this.redondeoPrecio(parcialImpuesto*cantidad);
  }

  showParcialFull() {
    const moneda = this.selectedMoneda.sigla;
    let message = moneda +" "+ this.parcialDescuento(this.selectedDetalle) + " Dto. total<br/>";
    message+= moneda +" "+ this.parcialImpuesto(this.selectedDetalle) + " Imp. total<br/>";
    message+= moneda +" "+ this.precioParcial(this.selectedDetalle) + " Total Imp. Inc.<br/>";
    const alert = this.alertCtrl.create({
      title: 'Total item',
      subTitle: message,
      buttons: ['Aceptar']
    });
    alert.present();
  }

  swipeEvent(e) {
    /*if (e.direction == '2') { // Derecha
    } else if (e.direction == '4') { // Izquierda
      this.goBackAndReload();
    }*/
  }

  validateCantidad(): void {
    this.selectedDetalle.cantidad = this.selectedDetalle.cantidad<1 ? 1 : this.selectedDetalle.cantidad;
    this.selectedDetalle.cantidad = this.selectedDetalle.cantidad>999999 ? 999999 : this.selectedDetalle.cantidad;
  }

}
