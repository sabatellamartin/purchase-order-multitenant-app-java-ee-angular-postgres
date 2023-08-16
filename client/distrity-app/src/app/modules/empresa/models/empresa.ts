import { TipoEmpresa } from './tipo-empresa';
import { Calificacion } from './calificacion';
import { Sucursal } from './sucursal';

export class Empresa {
  id: number;
  rut: string;
  razonSocial: string;
  nombreComercial: string;
  rubro: string;
  telefono: string;
  web: string;
  email: string;
  alta: Date;
  baja: Date;
  referente: String;
  observacion: String;
  horario: String;
  tipoEmpresa: TipoEmpresa;
  calificacion: Calificacion;
  sucursales: Sucursal[];
  constructor() {
    this.tipoEmpresa = new TipoEmpresa();
    this.calificacion = new Calificacion();
    this.sucursales = new Array<Sucursal>();
  }
}
