import { Persona } from './persona';
import { Operador } from '../../auth/models/operador';
import { Zona } from '../../ubicacion/models/zona';

export class Empleado extends Persona {
  cargo: string;
  tarea: string;
  zona: Zona;
  operador: Operador;
}
