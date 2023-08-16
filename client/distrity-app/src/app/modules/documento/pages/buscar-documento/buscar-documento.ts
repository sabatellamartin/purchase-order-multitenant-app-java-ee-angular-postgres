import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';

import { FormControl } from '@angular/forms';

import 'rxjs/add/operator/debounceTime';

// Plugins
import { LoadingController } from 'ionic-angular';

// Providers
import { DocumentoService } from '../../providers/documento.service';
import { EmpresaService } from '../../../empresa/providers/empresa.service';

import { Documento } from '../../models/documento';
import { OrdenVenta } from '../../models/orden-venta';
import { Empresa } from '../../../empresa/models/empresa';
import { Cliente } from '../../../empresa/models/cliente';
import { OrdenVentaFilter } from '../../models/filter/ordenventafilter';

import { CrearOrdenVentaPage } from '../crear-orden-venta/crear-orden-venta';
import { VerDocumentoPage } from '../ver-documento/ver-documento';


@Component({
  selector: 'page-buscar-documento',
  templateUrl: 'buscar-documento.html'
})
export class BuscarDocumentoPage {

  showFilter: boolean = false;

  tipoDocumento: string = 'OrdenVenta';

  clientes: Empresa[];

  selectedCliente: Cliente;
  selectedFrom: string;
  selectedTo: string;


  documentos: Documento[] = new Array<Documento>();
  selectedDocumento: Documento = new Documento();

  searchTerm: string = "";
  searchControl: FormControl;
  searching: any = false;

  monthNames: string ="enero, febrero, marzo, abril, mayo, junio, julio, agosto, septiembre, octubre, noviembre, diciembre";
  monthShortNames: string ="ene, feb, mar, abr, may, jun, jul, ago, sep, oct, nov, dic";
  dayNames: string ="domingo, lunes, martes, miercoles, jueves, viernes , sabado";
  dayShortNames: string ="dom, lun, mar, mie, jue, vie, sab";
  cancelText: string = "Cancelar";
  doneText: string = "Listo";

  constructor(
    public navCtrl: NavController,
    public loadingCtrl: LoadingController,
    private empresaService: EmpresaService,
  	private documentoService: DocumentoService) {
    this.searchControl = new FormControl();
  }

  ionViewDidEnter() {
    this.init();
    // Search
    this.syncSearch();
    this.searchControl.valueChanges.debounceTime(1000).subscribe(search  => {
      this.searching = false;
      this.syncSearch();
    });
  }

  private async init() {
    // Load Clientes
    this.clientes = await this.loadClientes();
    //this.clearFilter();
    /*let date = new Date();
    this.selectedTo = date.toISOString();
    date.setDate(date.getDate()-15);
    this.selectedFrom = date.toISOString();*/
  }


  private loadClientes(): Promise<any> {
    return this.empresaService.getAllClientes().then(clientes => { return clientes });
  }

  onChangeCliente(cliente: Empresa) {
    this.syncSearch();
  }


  private getCurrentFilter() {
    let filter: OrdenVentaFilter = new OrdenVentaFilter();
  /*  filter.operadorId = null;
    filter.clienteId = this.selectedCliente ? this.selectedCliente.id : null;
    filter.term = this.searchTerm ? this.searchTerm : "";
    filter.from = this.selectedFrom ? new Date(this.selectedFrom) : filter.from;
    filter.to = this.selectedTo ? new Date(this.selectedTo) : filter.to;
    filter.limit = filter.limit < 5 ? 5 : filter.limit;*/
    return filter;
  }
/*
  clearFilter() {
    this.selectedCliente = null;
    this.searchTerm = null;
    this.selectedFrom = null;
    this.selectedTo = null;
  }
*/

  syncSearch(): void {
    const filter: OrdenVentaFilter = this.getCurrentFilter();
    this.documentoService.searchOrdenVentaFilter(filter).then(response => {
      if (response) {
        this.documentos = response.resultList as OrdenVenta[];
        //this.total = response.totalItems; //documentos.length;
      }
    });
  }

  onSearchInput(){
   this.searching = true;
  }

  stringFilter() {
    return JSON.stringify(this.getCurrentFilter());
  }

  onSegmentChange() {
    this.syncSearch();
  }


  goToCrearDocumento(){
    this.navCtrl.push(CrearOrdenVentaPage);
  }

  verDocumento(documento: Documento) {
    this.navCtrl.push(VerDocumentoPage, {
      documento_id: documento.id
    });
  }

}
