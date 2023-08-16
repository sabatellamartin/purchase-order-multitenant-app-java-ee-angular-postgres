import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { FormControl } from '@angular/forms';

import 'rxjs/add/operator/debounceTime';

// Plugins
import { LoadingController } from 'ionic-angular';

// Providers
import { EmpresaService } from '../../providers/empresa.service';

import { Empresa } from '../../models/empresa';

@Component({
  selector: 'page-buscar-cliente',
  templateUrl: 'buscar-cliente.html'
})
export class BuscarClientePage {

  empresas: Empresa[];
  selectedEmpresa: Empresa;

  searchTerm: string = "";
  searchTime: Date =  new Date();
  searchControl: FormControl;
  searching: any = false;

  constructor(
    public navCtrl: NavController,
    public loadingCtrl: LoadingController,
  	private empresaService: EmpresaService) {
    this.searchControl = new FormControl();
  }

  ionViewDidEnter() {
    this.syncSearch();
    this.searchControl.valueChanges.debounceTime(1000).subscribe(search  => {
      this.searching = false;
      this.syncSearch();
    });
  }

  syncSearch(): void {
    this.empresaService.searchClienteByTerm(this.searchTerm).then(empresas => {
      this.empresas = empresas;
    });
    this.searchTime = new Date();
  }

  onSearchInput(){
   this.searching = true;
  }

}
