import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

import { IMyDrpOptions } from 'mydaterangepicker';

import { Documento } from '../../models/documento';
import { Operador } from '../../../auth/models/operador';
import { OrdenVentaFilter } from '../../models/filters/ordenventa-filter';
import { Estado } from '../../models/estado';
import { OrdenVenta } from '../../models/ordenventa';

import { DocumentoService } from '../../providers/documento.service';

@Component({
  selector: 'documento-lista',
  templateUrl: './documento.component.html',
  styleUrls: ['./documento.component.css']
})
export class DocumentoComponent implements OnInit {

  documentos: Documento[];
  @Input() selectedDocumento: Documento;

  // Modal Title
  modalTitle: string = "";
  modalForm = new EventEmitter<string|MaterializeAction>();

  term: string = "";

  // PAGINACION
  currentPage: number = 1;
  totalItems: number = 100;
  itemsPerPage: number = 5;

  estados: string[] = [
    Estado[0].toString(),
    Estado[1].toString(),
    Estado[2].toString(),
    Estado[3].toString(),
    Estado[4].toString(),
    Estado[5].toString()];
  filterEstado: string = null;

  // For example initialize to specific date (09.10.2018 - 19.10.2018). It is also possible
  // to set initial date range value using the selDateRange attribute.
  dateRange: any = {beginDate: {year: 2018, month: 10, day: 9}, endDate: {year: 2018, month: 10, day: 19}};
  myDateRangePickerOptions: IMyDrpOptions = {
    dateFormat: 'dd/mm/yyyy',
    width: '100%',
    height: '43px',
    selectionTxtFontSize: '14px',
    dayLabels: {su: 'Dom', mo: 'Lun', tu: 'Mar', we: 'Mie', th: 'Jue', fr: 'Vie', sa: 'Sab'},
    monthLabels: { 1: 'Ene', 2: 'Feb', 3: 'Mar', 4: 'Abr', 5: 'May', 6: 'Jun', 7: 'Jul', 8: 'Ago', 9: 'Sep', 10: 'Oct', 11: 'Nov', 12: 'Dic' },
    openSelectorOnInputClick: true,
    ariaLabelInputField: 'Ingreso de rango de fechas',
    ariaLabelClearDateRange: 'Limpiar rango de fechas',
    ariaLabelOpenCalendar: 'Abrir calendario',
    ariaLabelPrevMonth: 'Més anterior',
    ariaLabelNextMonth: 'Més siguiente',
    ariaLabelPrevYear: 'Año anterior',
    ariaLabelNextYear: 'Año siguiente'
  };

  constructor(
    private router: Router,
    private documentoService: DocumentoService) {
  }

  ngOnInit() {
    this.initDatepicker();
    this.searchByTerm();
  }

  private initDatepicker(): void {
    let to: Date = new Date();
    let from: Date = new Date();
    from.setDate(to.getDate() - 30);
    this.dateRange = {
      beginDate: {
        year: from.getFullYear(),
        month: from.getMonth()+1,
        day: String(from.getDate()+1).padStart(1, '0')},
      endDate: {
        year: to.getFullYear(),
        month: to.getMonth()+1,
        day:  String(to.getDate()+1).padStart(1, '0')}
    };
  }

  openModal() {
    this.modalForm.emit({action:"modal",params:['open']});
  }

  closeModal() {
    this.modalForm.emit({action:"modal",params:['close']});
  }

  setTerm(term: string) {
    this.term = term;
  }

  syncSearch() {
    this.searchByTerm();
  }

  // Pager
  currentPageChanged(currentPage: number) {
    this.currentPage = currentPage>0 ? currentPage: 1;
    this.searchByTerm();
  }
  // Pager
  itemsPerPageChanged(items: number) {
    this.itemsPerPage = items<50?items:50;
    this.searchByTerm();
  }

  searchByTerm(): void {
    let filter = new OrdenVentaFilter();
    filter.operadorId = null;
    filter.clienteId =  null;
    filter.term = this.term;
    filter.from = this.dateRange ? new Date(
      this.dateRange.beginDate.year,
      this.dateRange.beginDate.month-1,
      this.dateRange.beginDate.day) : new Date();
    filter.to = this.dateRange ? new Date(
      this.dateRange.endDate.year,
      this.dateRange.endDate.month-1,
      this.dateRange.endDate.day) : new Date();
    filter.limit = 50;
    filter.estado = this.filterEstado;
    // Pager Filter
    filter.paginatorRequest.firstResult = (this.currentPage-1) * this.itemsPerPage;
    filter.paginatorRequest.pageSize = this.itemsPerPage;
    this.documentoService.searchOrdenVentaFilter(filter).then(response => {
      if (response) {
        this.documentos = response.resultList as OrdenVenta[];
        this.totalItems = response.totalItems;
      }
    });
  }

  detailAction(documento: Documento): void {
    this.selectedDocumento = documento;
    this.modalTitle = "Detalle del documento";
    this.openModal();
  }

  changeEstadoAction(documento: Documento): void {
    if (documento.id) {
      this.documentoService.changeEstado(documento.id).then(response => {
        if (response) {
          this.searchByTerm();
        }
      });
    }
  }

  cancelarAction(documento: Documento): void {
    if (documento.id) {
      this.documentoService.cancelarToggle(documento.id).then(response => {
        if (response) {
          this.searchByTerm();
        }
      });
    }
  }

  getEstadoTooltip(documento: OrdenVenta): string {
    let message = "?";
    if (documento.estado) {
      let estado: string = Estado[this.getNextEstado(documento.estado.toString())].toString();
      if (documento.estado.toString() == "CREADO") {
        message = "Validar"; // Estado.VALIDADO;
      } else if (documento.estado.toString()  == "VALIDADO") {
        message = "Procesar"; // Estado.PROCESADO;
      } else if (documento.estado.toString()  == "PROCESADO") {
        message = "Enviar"; // Estado.ENVIADO;
      } else if (documento.estado.toString()  == "ENVIADO") {
        message = "Entregar"; // Estado.ENTREGADO;
      } else if (documento.estado.toString()  == "ENTREGADO") {
        message = "Cancelar"; // Estado.CANCELADO;
      } else if (documento.estado.toString()  == "CANCELADO") {
        message = "Restaurar"; // Estado.CREADO;
      }
    }
    return message;
  }

  getEstadoClassColorCode(documento: OrdenVenta): string {
    let classEstado: string = "";
    if (documento.estado) {
      let estado: string = documento.estado.toString();
      if (estado == Estado[0]) {
        classEstado = "";
      } else if (estado == Estado[1]) {
        classEstado = "";
      } else if (estado === Estado[2]) {
        classEstado = "";
      } else if (estado === Estado[3]) {
        classEstado = "";
      }
    }
    return classEstado;
  }

  private getNextEstado(estado: string): Estado {
    let nextEstado: Estado = Estado.CANCELADO;
    if (estado === Estado[0]) {
      nextEstado = Estado.VALIDADO;
    } else if (estado === Estado[1]) {
      nextEstado = Estado.PROCESADO;
    } else if (estado === Estado[2]) {
      nextEstado = Estado.ENVIADO;
    } else if (estado === Estado[3]) {
      nextEstado = Estado.ENTREGADO;
    }
    return nextEstado;
  }

  isCancelado(documento: OrdenVenta): boolean {
    if (documento.estado) {
      if (documento.estado.toString() === Estado[5]) {
        return true;
      }
    }
    return false;
  }

  pdf(documento: Documento): void {
    if (documento.id && documento.id==this.selectedDocumento.id) {
      this.documentoService.documentoPdfById(documento.id).then((blob: Blob) => {
        let downloadURL = window.URL.createObjectURL(blob);
        let link = document.createElement('a');
        link.href = downloadURL;
        link.download = "pedido_"+documento.numeroDocumento+".pdf";
        link.click();
      });
    }
  }

  editAction(documento: Documento): void {
    if (documento.id) {
      this.router.navigate(['documento/form'], { queryParams: { id: documento.id } });
    }
  }

  newAction(): void {
    this.router.navigateByUrl('documento/form');
  }

}
