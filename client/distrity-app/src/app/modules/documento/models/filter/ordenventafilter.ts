import { PaginatorRequest } from '../../../shared/models/paginator-request';

export class OrdenVentaFilter {
  operadorId: number;
  clienteId: number;
  from: Date;
  to: Date;
  term: string;
  limit: number;
  estado: string;
  paginatorRequest: PaginatorRequest;
  constructor() {
    this.clienteId = null;
    this.operadorId = null;
    this.term = "";
    this.from = new Date();
    this.from.setDate(this.from.getDate()-30);
    this.to = new Date();
    this.limit = 50;
    this.paginatorRequest = new PaginatorRequest();
    this.paginatorRequest.pageSize = 20;
    this.paginatorRequest.firstResult = 0;
  }
}
