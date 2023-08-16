
export class PaginatorRequest {
	pageSize: number;
	firstResult: number;
  constructor() {
		this.pageSize = 20;
		this.firstResult = 0;
	}
}
