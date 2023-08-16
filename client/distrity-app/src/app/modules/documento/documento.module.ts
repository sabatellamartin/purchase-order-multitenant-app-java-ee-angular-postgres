import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from 'ionic-angular';

import { SharedModule } from '../shared/shared.module';

import { DatePicker } from '@ionic-native/date-picker';

import { File } from '@ionic-native/file';
import { FileOpener } from '@ionic-native/file-opener';

// Providers
import { DocumentoService } from './providers/documento.service';
import { MonedaService } from './providers/moneda.service';

// Pages
import { CrearOrdenVentaPage } from './pages/crear-orden-venta/crear-orden-venta';
import { SeleccionArticuloPage } from './pages/seleccion-articulo/seleccion-articulo';
import { SeleccionClientePage } from './pages/seleccion-cliente/seleccion-cliente';
import { EditarDetallePage } from './pages/editar-detalle/editar-detalle';
import { BuscarDocumentoPage } from './pages/buscar-documento/buscar-documento';
import { VerDocumentoPage } from './pages/ver-documento/ver-documento';

@NgModule({
  declarations: [
    CrearOrdenVentaPage,
    SeleccionArticuloPage,
    SeleccionClientePage,
    EditarDetallePage,
    BuscarDocumentoPage,
    VerDocumentoPage
  ],
  imports: [
    CommonModule,
    IonicModule,
    SharedModule
  ],
  exports: [
    CrearOrdenVentaPage,
    SeleccionArticuloPage,
    SeleccionClientePage,
    EditarDetallePage,
    BuscarDocumentoPage,
    VerDocumentoPage,
  ],
  entryComponents: [
    CrearOrdenVentaPage,
    SeleccionArticuloPage,
    SeleccionClientePage,
    EditarDetallePage,
    BuscarDocumentoPage,
    VerDocumentoPage
  ],
  providers: [
    DatePicker,
    File,
    FileOpener,
    DocumentoService,
    MonedaService
  ]
})
export class DocumentoModule { }
