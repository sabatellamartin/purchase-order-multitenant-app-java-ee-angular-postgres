import { Documento } from './documento';
import { Cliente } from '../../empresa/models/cliente';
import { Sucursal } from '../../empresa/models/sucursal';

export class OrdenVenta extends Documento {
  estado: string;
  cliente: Cliente;
  sucursal: Sucursal;
}
