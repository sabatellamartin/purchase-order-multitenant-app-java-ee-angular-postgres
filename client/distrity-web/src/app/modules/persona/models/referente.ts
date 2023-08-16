import { Persona } from './persona';
import { Empresa } from '../../empresa/models/empresa';

export class Referente extends Persona {
  descripcion: string;
  empresas: Empresa[];
}
