import { Direccion } from '../../ubicacion/models/direccion';
import { TipoDocumento } from './tipo-documento';

export class Persona {
  id: number;
  numeroDocumento: string;
  primerNombre: string;
  segundoNombre: string;
  primerApellido: string;
  segundoApellido: string;
  nombreBusqueda: string;
  nombreCompleto: string;
  direccionEmail: string;
  telefono: string;
  fechaNacimiento: Date;
  fechaFallecimiento: Date;
  direccion: Direccion;
  tipoDocumento: TipoDocumento;
}
