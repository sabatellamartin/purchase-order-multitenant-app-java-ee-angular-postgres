import { TipoDireccion } from './tipo-direccion';

export class Direccion {
  id: number;
  numero: string;
  calle: string;
  observacion: string;
  locacionId: string;
  tipoDireccion: TipoDireccion;
}
