import { Operador } from '../../auth/models/operador';
import { Moneda } from './moneda';
import { Detalle } from './detalle';

export class Documento {
  id: number;
  fecha: Date;
  vencimiento: Date;
  numeroDocumento: string;
  moneda: Moneda;
  detalles: Detalle[];
  usuario: Operador;
  total: number;
  observaciones: string;
  constructor() {
    this.detalles = new Array<Detalle>();
    this.usuario = new Operador();
  }
}
