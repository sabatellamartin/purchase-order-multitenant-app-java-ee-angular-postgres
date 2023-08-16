import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

/* Datepicker */
import { IMyDpOptions, IMyDateModel } from 'mydatepicker';

/* Clases */
import { Documento } from '../../models/documento';
import { OrdenVenta } from '../../models/ordenventa';
import { Detalle } from '../../models/detalle';
import { Estado } from '../../models/estado';

/* Servicios */
import { DocumentoService } from '../../providers/documento.service';

@Component({
  selector: 'documento-form',
  templateUrl: './documento-form.component.html',
  styleUrls: ['./documento-form.component.css']
})
export class DocumentoFormComponent implements OnInit {

  title: string;

  @Input() selectedDocumento: OrdenVenta;

  totalDescuento: number = 0;
  totalImpuesto: number = 0;
  total: number = 0;

  // Paginator
  page: number = 1;
  totalItems: number = 1;

  // Initialized to specific date (09.10.2018).
  emision: any = { date: { year: 2018, month: 10, day: 9 } };
  vencimiento: any = { date: { year: 2018, month: 10, day: 9 } };
  emisionOptions: IMyDpOptions;
  vencimientoOptions: IMyDpOptions;

  estadoTooltip: string;

  selectedDetalle: Detalle = new Detalle();

  modalSeleccionArticulos = new EventEmitter<string|MaterializeAction>();
  modalEditDetalle = new EventEmitter<string|MaterializeAction>();

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private documentoService: DocumentoService) {
    this.loadDatepickerOptions();
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => { // Get Params
      const documentoId = params['id']; // Defaults to undefined if no query param provided.
      if (documentoId > 0) {
        this.title = "Modificar pedido";
        this.loadDocumentoById(documentoId);
      } else {
        this.title = "Crear pedido";
        this.selectedDocumento = new OrdenVenta();
      }
    });
  }

  private loadDocumentoById(id: number) {
    this.documentoService.getById(id).then(documento => {
      this.selectedDocumento = documento;
      this.initDatepicker();
      this.getEstadoTooltip(documento);
      this.getTotal(documento);
      this.totalItems = documento.detalles.length;
      console.log(documento);
    });
  }

  private loadDatepickerOptions(): void {
    const options: IMyDpOptions = {
      dateFormat: 'dd/mm/yyyy',
      width: '100%',
      height: '43px',
      selectionTxtFontSize: '14px',
      dayLabels: {su: 'Dom', mo: 'Lun', tu: 'Mar', we: 'Mie', th: 'Jue', fr: 'Vie', sa: 'Sab'},
      monthLabels: { 1: 'Ene', 2: 'Feb', 3: 'Mar', 4: 'Abr', 5: 'May', 6: 'Jun', 7: 'Jul', 8: 'Ago', 9: 'Sep', 10: 'Oct', 11: 'Nov', 12: 'Dic' },
      openSelectorOnInputClick: true,
      ariaLabelInputField: 'Ingreso de fecha',
      ariaLabelClearDate: 'Limpiar fecha',
      ariaLabelDecreaseDate: 'Disminuir fecha',
      ariaLabelIncreaseDate: 'Aumentar fecha',
      ariaLabelOpenCalendar: 'Abrir calendario',
      ariaLabelPrevMonth: 'Més anterior',
      ariaLabelNextMonth: 'Més siguiente',
      ariaLabelPrevYear: 'Año anterior',
      ariaLabelNextYear: 'Año siguiente',
      todayBtnTxt: 'Hoy',
      markCurrentDay: true,
      showClearDateBtn: false
    };
    this.emisionOptions = options;
    this.vencimientoOptions = options;
  }

  private initDatepicker(): void {
    let vencimiento: Date = new Date(this.selectedDocumento.vencimiento);
    let emision: Date = new Date(this.selectedDocumento.fecha);
    this.emision = {
      date: {
        year: emision.getFullYear(),
        month: emision.getMonth()+1,
        day: emision.getDate()
      }
    };
    this.vencimiento = {
      date: {
        year: vencimiento.getFullYear(),
        month: vencimiento.getMonth()+1,
        day: vencimiento.getDate()
      }
    };
    this.vencimientoOptions.disableUntil = {
        year: emision.getFullYear(),
        month: emision.getMonth()+1,
        day: emision.getDate()
    };
  }

  compareFn( optionOne, optionTwo ) : boolean {
    if (optionOne && optionTwo) {
      return optionOne.id === optionTwo.id;
    } else { return false; }
  }

  // dateChanged callback function called when the user select the date. This is mandatory callback
  // in this option. There are also optional inputFieldChanged and calendarViewChanged callbacks.
  onVencimientoChanged(event: IMyDateModel) {
    // event properties are: event.date, event.jsdate, event.formatted and event.epoc
    this.selectedDocumento.vencimiento = event.jsdate as Date;
  }

  pageChanged(currentPage: number) {
    this.page = currentPage>0 ? currentPage: 1;
  }

  getEstadoTooltip(documento: OrdenVenta) {
    if (documento.estado) {
      //let estado: Estado = this.getNextEstado(documento.estado.toString());
      this.estadoTooltip = "?";
      if (documento.estado.toString() == "CREADO") {
        this.estadoTooltip = "Validar"; // Estado.VALIDADO;
      } else if (documento.estado.toString()  == "VALIDADO") {
        this.estadoTooltip = "Procesar"; // Estado.PROCESADO;
      } else if (documento.estado.toString()  == "PROCESADO") {
        this.estadoTooltip = "Enviar"; // Estado.ENVIADO;
      } else if (documento.estado.toString()  == "ENVIADO") {
        this.estadoTooltip = "Entregar"; // Estado.ENTREGADO;
      } else if (documento.estado.toString()  == "ENTREGADO") {
        this.estadoTooltip = "Cancelar"; // Estado.CANCELADO;
      } else if (documento.estado.toString()  == "CANCELADO") {
        this.estadoTooltip = "Restaurar"; // Estado.CREADO;
      }
    }
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
    } else if (estado === Estado[4]) {
      nextEstado = Estado.CANCELADO;
    } else if (estado === Estado[5]) {
      nextEstado = Estado.CREADO;
    }
    return nextEstado;
  }

  changeEstadoAction(documento: OrdenVenta): void {
    if (documento.id) {
      this.documentoService.changeEstado(documento.id).then(response => {
        if (response) {
          this.loadDocumentoById(documento.id);
        }
      });
    }
  }

  cancelarAction(documento: OrdenVenta): void {
    if (documento.id) {
      this.documentoService.cancelarToggle(documento.id).then(response => {
        if (response) {
          this.loadDocumentoById(documento.id);
        }
      });
    }
  }

  saveOrdenVenta() {
    if (this.selectedDocumento.id) {
      this.documentoService.update(this.selectedDocumento, "ORDENVENTA").then(response => {
        if (response) {
          let mensaje = "Orden de venta nro. '"+this.selectedDocumento.numeroDocumento+"' ha sido modificada.";
          toast(mensaje, 4000);
          this.loadDocumentoById(this.selectedDocumento.id);
        }
      });
    }
  }

  goToEditCliente(cliente: any) {
    if (cliente.id) {
      this.router.navigate(['empresa/form'], { queryParams: { id: cliente.id, type: "CLIENTE" } });
    }
  }

  // Calculos precios
  precioParcial(detalle:Detalle): number {
    const cantidad = detalle.cantidad;
    return this.redondeoPrecio(this.precioImpuesto(detalle)*cantidad);
  }

  precioUnitario(detalle:Detalle): number {
    const precio = detalle.precio;
    const descuento = detalle.descuento;
    return this.redondeoPrecio(precio*(1-(descuento/100)));
  }

  precioImpuesto(detalle:Detalle): number {
    const impuesto = detalle.articulo.impuesto.porcentaje;
    const unitario = this.precioUnitario(detalle);
    return this.redondeoPrecio(unitario*(1+(impuesto/100)));
  }

  private getTotal(documento: OrdenVenta) {
    let total:number = 0;
    let totalDescuento:number = 0;
    let totalImpuesto:number = 0;
    if(documento.detalles.length>0) {
      for (let detalle of documento.detalles) {
        total += this.precioParcial(detalle);
        totalDescuento += this.parcialDescuento(detalle);
        totalImpuesto += this.parcialImpuesto(detalle);
      }
    }
    this.total = Math.round(total * 100) / 100;
    this.totalDescuento = Math.round(totalDescuento * 100) / 100;
    this.totalImpuesto = Math.round(totalImpuesto * 100) / 100;
    this.setTotal();
  }

  private parcialDescuento(detalle:Detalle): number {
    const precio = detalle.precio;
    const descuento = detalle.descuento;
    const cantidad = detalle.cantidad;
    const parcialDescuento = precio*(descuento/100)
    return this.redondeoPrecio(parcialDescuento*cantidad);
  }

  private parcialImpuesto(detalle:Detalle): number {
    const impuesto = detalle.articulo.impuesto.porcentaje;
    const cantidad = detalle.cantidad;
    const parcialImpuesto = this.precioUnitario(detalle)*(impuesto/100);
    return this.redondeoPrecio(parcialImpuesto*cantidad);
  }

  private redondeoPrecio(precio:number): number {
    return Math.round(precio * 100) / 100;
  }

  private setTotal() {
    this.selectedDocumento.total = this.total;
  }

  pdf(documento: Documento): void {
    console.log(this.selectedDocumento.id+" "+documento.id);
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

  openModalSeleccionArticulos() {
    this.modalSeleccionArticulos.emit({action:"modal",params:['open']});
  }

  closeModalSeleccionArticulos() {
    this.modalSeleccionArticulos.emit({action:"modal",params:['close']});
  }

  openModalEditDetalle() {
    this.modalEditDetalle.emit({action:"modal",params:['open']});
  }

  closeModalEditDetalle() {
    this.modalEditDetalle.emit({action:"modal",params:['close']});
    this.getTotal(this.selectedDocumento);
  }

  selectedArticuloEvent(articulo: any) {
    let existe : boolean = false;
    for (let detalle of this.selectedDocumento.detalles) {
      if (detalle.articulo.id == articulo.id) {
        let msj = "Articulo '"+articulo.nombre+"' ya existe en la lista";
        toast(msj, 4000);
        existe = true;
      }
    }
    if (!existe) {
      let detalle = new Detalle();
      detalle.cantidad = 1;
      detalle.descuento = 0;
      detalle.precio = articulo.precioVenta;
      detalle.articulo = articulo;
      this.selectedDocumento.detalles.push(detalle);
    }
  }

  editDetalleAction(detalle: Detalle) {
    this.openModalEditDetalle();
    this.selectedDetalle = detalle;
  }

  removeDetalleAction(detalle: Detalle) {
    let msj = "Articulo '"+detalle.articulo.nombre+"' removido";
    toast(msj, 4000);
    this.selectedDocumento.detalles = this.selectedDocumento.detalles.filter(d => d.articulo.id !== detalle.articulo.id);
  }

}
