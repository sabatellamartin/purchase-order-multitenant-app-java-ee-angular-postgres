import { Component, ViewChild } from '@angular/core';
import { NavController, Select } from 'ionic-angular';

// Plugins
import { LoadingController } from 'ionic-angular';
import { Events } from 'ionic-angular';
import { AlertController } from 'ionic-angular';
import { ToastController } from 'ionic-angular';

//Pages
import { SeleccionArticuloPage } from '../seleccion-articulo/seleccion-articulo';
import { SeleccionClientePage } from '../seleccion-cliente/seleccion-cliente';
import { EditarDetallePage } from '../editar-detalle/editar-detalle';

// Providers
import { DocumentoService } from '../../providers/documento.service';
import { MonedaService } from '../../providers/moneda.service';

import { Cliente } from '../../../empresa/models/cliente';
import { Sucursal } from '../../../empresa/models/sucursal';
import { Moneda } from '../../models/moneda';
import { OrdenVenta } from '../../models/orden-venta';
import { Detalle } from '../../models/detalle';


@Component({
  selector: 'page-crear-orden-venta',
  templateUrl: 'crear-orden-venta.html'
})
export class CrearOrdenVentaPage {

  tipoDocumento: string = 'OrdenVenta';

  selectedCliente: Cliente;

  selectedSucursal: Sucursal;//Sucursal;
  //sucursales: Sucursal[];
  @ViewChild('sucursalSelect') sucursalSelect: Select;

  selectedMoneda: Moneda;

  documentoLineas: Detalle[];

  total: number = 0;
  totalDescuento: number = 0;
  totalImpuesto: number = 0;

  picture: string = '../../../../../assets/imgs/touch-gesture-swipe-left-512.png';

  constructor(
    public navCtrl: NavController,
    public loadingCtrl: LoadingController,
    public events:Events,
    public alertCtrl: AlertController,
    public toastCtrl: ToastController,
  	private monedaService: MonedaService,
    private documentoService: DocumentoService) {
    this.selectMonedas();
    this.loadOrden();
    this.events.subscribe('reloadDetails',() => { //call methods to refresh content
     this.loadOrden();
    });
  }

  ionViewDidEnter() {}

  openSelectSucursal() {
    this.sucursalSelect.open();
  }

  closeSelectSucursal() {
    this.sucursalSelect.close();
  }

  private selectMonedas() : void {
    this.monedaService.getAllMonedas().then(monedas => {
      if (monedas.length>0) {
        this.selectedMoneda = monedas[0];
        this.onChangeMoneda();
      }
    });
  }

  onChangeMoneda() {
    this.documentoService.getOrden().then(orden => {
      if (this.selectedMoneda) {
        orden.moneda = this.selectedMoneda;
        this.documentoService.setOrden(orden).then(()=>{});
      }
    });
  }

  private loadOrden() {
    this.documentoService.getOrden().then(orden => {
      if (orden) {
        this.selectedCliente = orden.cliente;
        if (this.selectedCliente && this.selectedCliente.sucursales.length>0) {
          this.selectedSucursal = this.selectedCliente.sucursales[0];
        } else {
          this.selectedSucursal = null;
        }
        this.selectedMoneda = orden.moneda;
        this.documentoLineas = orden.detalles as Detalle[];
        this.getTotal();
      } else {
        this.documentoService.setOrden(new OrdenVenta()).then(result =>{
          if (result) {
            this.clearOptions();
            this.documentoLineas = new Array<Detalle>();
            this.total = 0;
          }
        });
      }
    });
  }

  private clearOptions() {
    this.selectedCliente = null;
    this.selectedSucursal = null;
    this.selectedMoneda = null;
  }

  goToSelectArticulo() {
    this.navCtrl.push(SeleccionArticuloPage);
  }

  removeDetalle(detalle: Detalle) {
    const lineas = this.documentoLineas.filter(linea => linea.articulo.id !== detalle.articulo.id);
    this.documentoService.getOrden().then(orden => {
      orden.detalles = lineas;
      this.documentoService.setOrden(orden).then(result => {
        if (result) {
          this.loadOrden();
        }
      });
    });
  }

  editDetalle(detalle: Detalle) {
    this.navCtrl.push(EditarDetallePage, {
      articulo_id: detalle.articulo.id
    });
  }

  removeOrden() {
    const confirm = this.alertCtrl.create({
      title: 'Descartar',
      message: '¿Desea descartar el pedido?',
      buttons: [
        {
          text: 'Cancelar',
          handler: () => {}
        },
        {
          text: 'Descartar',
          handler: () => {
            this.documentoService.removeOrden();
            this.loadOrden();
            this.selectMonedas();
          }
        }
      ]
    });
    confirm.present();
  }

  saveOrden() {
    if (this.verificaEnvio()) {
      const confirm = this.alertCtrl.create({
        title: 'Enviar',
        message: '¿Desea enviar el pedido?',
        buttons: [
          {
            text: 'Cancelar',
            handler: () => {}
          },
          {
            text: 'Enviar',
            handler: () => {
              this.documentoService.getOrden().then(orden => {
                if (orden) {
                  this.documentoService.create(orden, "ORDENVENTA").then(result => {
                    this.saveMessage(result);
                    if (result) {
                      this.documentoService.removeOrden();
                      this.loadOrden();
                      this.selectMonedas();
                    }
                  });
                }
              });
            }
          }
        ]
      });
      confirm.present();
    }
  }

  private saveMessage(result: Boolean) {
    const toast = this.toastCtrl.create({
      message: result ? 'Envío exitoso' : 'Envío fallido',
      duration: 5000,
      position: 'top'
    });
    toast.present();
  }

  private verificaEnvio(): boolean {
    let message: string = null;
    if (!this.selectedCliente) {
      message = "Seleccione un cliente antes de continuar";
    } else if (!this.selectedMoneda) {
      message = "Seleccione una divisa antes de continuar";
    } else if (!this.selectedSucursal) {
      message = "Seleccione una sucursal antes de continuar";
    } else if (!(this.documentoLineas.length>0)) {
      message = "Agregue al menos un item antes de continuar";
    }
    if (message) {
      const alert = this.alertCtrl.create({
        title: 'Advertencia',
        subTitle: message,
        buttons: ['Aceptar']
      });
      alert.present();
      return false;
    }
    return true;
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

  redondeoPrecio(precio:number): number {
    return Math.round(precio * 100) / 100;
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

  private getTotal() {
    let total:number = 0;
    let totalDescuento:number = 0;
    let totalImpuesto:number = 0;
    if(this.documentoLineas.length>0) {
      for (let detalle of this.documentoLineas) {
        total += this.precioParcial(detalle);
        totalDescuento += this.parcialDescuento(detalle);
        totalImpuesto += this.parcialImpuesto(detalle);
      }
    }
    this.total = Math.round(total * 100) / 100;
    this.totalDescuento = Math.round(totalDescuento * 100) / 100;
    this.totalImpuesto = Math.round(totalImpuesto * 100) / 100;
    this.setTotal(this.total);
  }

  setTotal(total: number): void {
    this.documentoService.getOrden().then(orden => {
      if (total) {
        orden.total = total;
        this.documentoService.setOrden(orden).then(()=>{});
      }
    });
  }

  showTotalFull() {
    const moneda = this.selectedMoneda.sigla;
    let message = "Descuentos: " + moneda +" "+ this.totalDescuento + "<br/>";
        message+= "Impuestos:  " + moneda +" "+ this.totalImpuesto + "<br/>";
        message+= "Total:      " + moneda +" "+ this.total + "<br/>";
    const alert = this.alertCtrl.create({
      title: 'Totales',
      subTitle: message,
      buttons: ['Aceptar']
    });
    alert.present();
  }

  swipeEvent(e) {
    if (e.direction == '2') { // Derecha
      this.goToSelectArticulo();
    } else if (e.direction == '4') { /* Izquierda*/
    }
  }

  goToSelectCliente() {
    this.navCtrl.push(SeleccionClientePage);
  }

  onSelectChange(sucursal:Sucursal) {
    this.setSucursal(sucursal);
  }

  private setSucursal(sucursal: Sucursal): void {
    this.documentoService.getOrden().then(orden => {
      if (sucursal) {
        orden.sucursal = sucursal;
        this.documentoService.setOrden(orden).then(()=>{});
      }
    });
  }

}
