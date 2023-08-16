import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

@Component({
  selector: 'pager-component',
  templateUrl: './pager.component.html',
  styleUrls: ['./pager.component.css']
})
export class PagerComponent implements OnInit {

  @Input() currentPage: number = 1; // Pagina actual
  @Input() totalItems: number = 100; // Limite de resutados
  @Input() itemsPerPage: number = 10; // Resultados por pagina

  @Output() pageChange = new EventEmitter();
  @Output() itemsPerPageChange = new EventEmitter();

  searchTime: Date =  new Date(); // Instante de consulta

  constructor() {}

  ngOnInit() {}

  pageChanged(currentPage: number) {
    this.searchTime = new Date();
    this.currentPage = currentPage;
    this.pageChange.emit(currentPage);
  }

  itemsPerPageChanged(items: number) {
    items = items>0?items:1;
    items = items<this.totalItems?items:this.totalItems;
    this.itemsPerPage = items;
    this.itemsPerPageChange.emit(items);
  }

}
