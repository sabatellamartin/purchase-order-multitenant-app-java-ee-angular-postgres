import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';

import { Detalle } from '../../models/detalle';
import { Moneda } from '../../models/moneda';

@Component({
  selector: 'edit-detalle',
  templateUrl: './edit-detalle.component.html',
  styleUrls: ['./edit-detalle.component.css']
})
export class EditDetalleComponent implements OnInit {

  @Input() detalle: Detalle;
  @Input() moneda: Moneda;

  constructor(
    private router: Router,
    private route: ActivatedRoute) {
  }

  ngOnInit() {}

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

  validateCantidad(): void {
    this.detalle.cantidad = this.detalle.cantidad<1 ? 1 : this.detalle.cantidad;
    this.detalle.cantidad = this.detalle.cantidad>999999 ? 999999 : this.detalle.cantidad;
  }

  increment () {
    this.detalle.cantidad++;
    this.validateCantidad();
  }

  decrement () {
    if (this.detalle.cantidad > 1) {
      this.detalle.cantidad--;
    }
    this.validateCantidad();
  }

}
