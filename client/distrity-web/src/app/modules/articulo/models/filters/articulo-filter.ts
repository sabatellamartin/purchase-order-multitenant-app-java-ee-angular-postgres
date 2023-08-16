import { PaginatorRequest } from '../../../shared/models/paginator-request';

export class ArticuloFilter {
  categoriaId: number;
  impuestoId: number;
  unidadId: number;
  minPrecio: number;
  maxPrecio: number;
  from: Date;
  to: Date;
  term: string;
  paginatorRequest: PaginatorRequest;
  constructor() {
    this.categoriaId = null;
    this.impuestoId = null;
    this.unidadId = null;
    this.minPrecio = 0;
    this.maxPrecio = null;
    this.term = "";
    this.from = null;
    this.to = null;
    this.paginatorRequest = new PaginatorRequest();
  }
}
