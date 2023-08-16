import { Persona } from './persona';
import { Operador } from '../../auth/models/operador';
import { Zona } from '../../ubicacion/models/zona';
import { Local } from '../../empresa/models/local';

export class Empleado extends Persona {
  cargo: string;
  tarea: string;
  zona: Zona;
  local: Local;
  operador: Operador;
}
