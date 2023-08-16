import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { FormControl } from '@angular/forms';

import 'rxjs/add/operator/debounceTime';

// Plugins
import { LoadingController } from 'ionic-angular';

// Providers
import { ArticuloService } from '../../providers/articulo.service';

import { Articulo } from '../../models/articulo';

@Component({
  selector: 'page-buscar-articulo',
  templateUrl: 'buscar-articulo.html'
})
export class BuscarArticuloPage {

  articulos: Articulo[];
  selectedArticulo: Articulo;

  searchTerm: string = "";
  searchTime: Date =  new Date();
  searchControl: FormControl;
  searching: any = false;

  constructor(
    public navCtrl: NavController,
    public loadingCtrl: LoadingController,
  	private articuloService: ArticuloService) {
    this.searchControl = new FormControl();
  }

  ionViewDidEnter() {
    this.syncSearch();
    this.searchArticulos();
    this.searchControl.valueChanges.debounceTime(1000).subscribe(search  => {
      this.searching = false;
      this.syncSearch();
    });
  }

  syncSearch(): void {
    this.articuloService.searchByTerm(this.searchTerm).then(articulos => {
      this.articulos = articulos;
    });
    this.searchTime = new Date();
  }

  onSearchInput(){
   this.searching = true;
  }

  searchArticulos() {
    this.articuloService.getArticulos(this.searchTerm).then(articulos => {
      this.articulos = articulos;
    });
  }

}
