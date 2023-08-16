import { Articulo } from '../../articulo/models/articulo';

export class Detalle {
  id: number;
  cantidad: number;
  descuento: number;
  precio: number;
  articulo: Articulo;
  constructor() {
    this.articulo = new Articulo();
  }
}
