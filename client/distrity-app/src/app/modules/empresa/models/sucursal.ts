import { Direccion } from './direccion';

export class Sucursal {
  id: number;
  nombre: string;
  casaCentral: boolean;
  direccion: Direccion;
  constructor() {
    this.casaCentral = false;
  }
}
