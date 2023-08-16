import { Component } from '@angular/core';
import { NavController, NavParams, AlertController, LoadingController } from 'ionic-angular';

import 'rxjs/add/operator/debounceTime';

import { Documento } from '../../models/documento';
import { Detalle } from '../../models/detalle';

import { DocumentoService } from '../../providers/documento.service';

import { File } from '@ionic-native/file';
import { FileOpener } from '@ionic-native/file-opener';

@Component({
  selector: 'page-ver-documento',
  templateUrl: 'ver-documento.html'
})
export class VerDocumentoPage {

  selectedDocumento: Documento = new Documento();
//  impuestos: Impuestos[];
  loader:any;

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public alertCtrl: AlertController,
    public loadingCtrl: LoadingController,
    private documentoService: DocumentoService,
    private file: File,
    private fileOpener: FileOpener){
  }

  ionViewDidEnter() {
    this.presentLoading();
    let id = this.navParams.get('documento_id');
    if (id) {
      this.documentoService.getById(id).then(documento => {
        this.selectedDocumento = documento;
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

  showPrompt(id: number) {
    const prompt = this.alertCtrl.create({
      title: 'Id documento',
      message: "El pedido es: " + JSON.stringify(this.selectedDocumento),
      inputs: [
        {
          name: 'title',
          placeholder: 'Title'
        },
      ],
      buttons: [
        {
          text: 'Cancel',
          handler: data => {
            console.log('Cancel clicked');
          }
        },
        {
          text: 'Save',
          handler: data => {
            console.log('Saved clicked');
          }
        }
      ]
    });
    prompt.present();
  }

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

  redondeoPrecio(precio:number) {
    return Math.round(precio * 100) / 100;
  }

  pdf(): void {
    if (this.selectedDocumento.id) {
      this.documentoService.documentoPdfById(this.selectedDocumento.id).then((blob: Blob) => {
        const fileName = "pedido_"+this.selectedDocumento.numeroDocumento+".pdf";
        this.file.writeFile(this.file.dataDirectory, fileName, blob, { replace: true }).then(response => {
          //alert(JSON.stringify(response));
          const filePath = response.nativeURL;//this.file.dataDirectory.concat("/").concat(fileName);
          this.fileOpener.open(filePath, 'application/pdf')
            .then(() => console.log('File is opened'))
            .catch(e => console.log('Error opening file', e));
        });

      });
    }
  }

}
