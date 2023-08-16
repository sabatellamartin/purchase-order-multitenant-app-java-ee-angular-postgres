/*
Longitud entre 0° y 180°: Al este del meridiano de Greenwich,
Longitud entre 0° y -180°: Al oeste del meridiano de Greenwich.
Latitud entre 0° y 90 °: Hemisferio Norte,
Latitud entre 0° y -90°: Hemisferio Sur.
*/

export class Punto {
  id: number;
  longitud: number;
  latitud: number;
  constructor(id: number, longitud: number, latitud: number) {
    this.id = id;
    this.longitud = longitud;
    this.latitud = latitud;
  }
}
