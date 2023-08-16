import { Documento } from './documento';
import { Estado } from './estado';
import { Cliente } from '../../empresa/models/cliente';
import { Sucursal } from '../../empresa/models/sucursal';

export class OrdenVenta extends Documento {
  estado: Estado;
  cliente: Cliente;
  sucursal: Sucursal;
}
