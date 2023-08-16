import { Proveedor } from '../../empresa/models/proveedor';
import { Unidad } from './unidad';
import { Categoria } from './categoria';
import { Impuesto } from './impuesto';

export class Articulo {
  id: number;
  codigo: string;
  codigoBarra: string;
  nombre: string;
  descripcion: string;
  precioCompra: number;
  precioVenta: number;
  precioBase: number;
  porcentajeDescuento: number;
  observaciones: string;
  fechaAlta: Date;
  fechaBaja: Date;
  unidad: Unidad;
  proveedores: Proveedor[];
  categoria: Categoria;
  impuesto: Impuesto;
}
