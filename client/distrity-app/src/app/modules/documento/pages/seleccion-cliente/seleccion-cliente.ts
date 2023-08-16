import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { FormControl } from '@angular/forms';

import 'rxjs/add/operator/debounceTime';

// Plugins
import { LoadingController } from 'ionic-angular';
import { Events } from 'ionic-angular'

// Providers
import { EmpresaService } from '../../../empresa/providers/empresa.service';
import { DocumentoService } from '../../providers/documento.service';


//import { Empresa } from '../../../empresa/models/empresa';
import { Cliente } from '../../../empresa/models/cliente';

@Component({
  selector: 'page-seleccion-cliente',
  templateUrl: 'seleccion-cliente.html'
})
export class SeleccionClientePage {

  clientes: Cliente[];
  selectedCliente: Cliente = new Cliente();

  searchTerm: string = "";
  searchTime: Date =  new Date();
  searchControl: FormControl;
  searching: any = false;

  constructor(
    public navCtrl: NavController,
    public loadingCtrl: LoadingController,
    public events:Events,
  	private empresaService: EmpresaService,
    private documentoService: DocumentoService) {
    this.searchControl = new FormControl();
  }

  ionViewDidEnter() {
    this.loadCliente();
    this.syncSearch();
    this.searchControl.valueChanges.debounceTime(1000).subscribe(search  => {
      this.searching = false;
      this.syncSearch();
    });
  }

  syncSearch(): void {
    this.empresaService.searchClienteByTerm(this.searchTerm).then(clientes => {
      this.clientes = clientes;
    });
    this.searchTime = new Date();
  }

  onSearchInput() {
    this.searching = true;
  }

  private loadCliente() {
    this.documentoService.getOrden().then(orden => {
      if (orden) { this.selectedCliente = orden.cliente as Cliente; }
    });
  }

  selectCliente(cliente:Cliente) {
    if (this.isSelected(cliente)) {
      cliente = null;
    }
    this.documentoService.getOrden().then(orden => {
      orden.cliente = cliente;
      if (!cliente) {
        orden.sucursal = null;
      }
      this.documentoService.setOrden(orden).then(()=>{
        this.goBackAndReload();
      });
    });
  }

  goBackAndReload() {
    this.events.publish('reloadDetails');
    this.navCtrl.pop();
  }

  isSelected(cliente: Cliente) {
    if (this.selectedCliente) {
      return this.selectedCliente.id === cliente.id;
    } else {
      return false;
    }
  }

  swipeEvent(e) {
    if (e.direction == '4') { // Izquierda
      this.goBackAndReload();
    }
  }

  /*
  editDetalle(id: number) {
    this.navCtrl.push(EditarDetallePage, {
      cliente_id: id
    });
  }*/

}
