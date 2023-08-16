
export class PaginatorResponse {
	totalItems: number;
	resultList: Array<any>;
  constructor() {
    this.resultList = new Array<any>();
  }
}
