import { Usuario } from './usuario';
import { Tenant } from './tenant';

export class Operador extends Usuario {
  rol: string;
  tenant: Tenant;
}
