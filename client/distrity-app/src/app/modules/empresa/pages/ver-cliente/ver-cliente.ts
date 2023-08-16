import { Component } from '@angular/core';
import { NavController, NavParams, AlertController, LoadingController } from 'ionic-angular';

import 'rxjs/add/operator/debounceTime';

import { Cliente } from '../../models/cliente';

import { EmpresaService } from '../../providers/empresa.service';

@Component({
  selector: 'page-ver-cliente',
  templateUrl: 'ver-cliente.html'
})
export class VerClientePage {

  selectedCliente: Cliente = new Cliente();

  loader: any;

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public alertCtrl: AlertController,
    public loadingCtrl: LoadingController,
    private empresaService: EmpresaService){
  }

  ionViewDidEnter() {
    this.presentLoading();
    let id = this.navParams.get('cliente_id');
    if (id) {
      this.empresaService.getById(id).then(cliente => {
        this.selectedCliente = cliente;
        this.loader.dismiss();
      });
    }
  }

  swipeEvent(e) {
    if (e.direction == '2') { // Derecha
      //alert("Derecha");
    } else if (e.direction == '4') { /* Izquierda*/
      this.navCtrl.pop();
    }
  }

  presentLoading() {
    this.loader = this.loadingCtrl.create({
      content: "Cargando...",
      duration: 3000
    });
    this.loader.present();
  }

}
