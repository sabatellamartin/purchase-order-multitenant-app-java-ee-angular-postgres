import { NgModule } from '@angular/core';
import { IonicModule } from 'ionic-angular';

// Modules
import { SharedModule } from '../shared/shared.module';
import { AuthModule } from '../auth/auth.module';

// Providers
import { ArticuloService } from './providers/articulo.service';

// Pages
import { BuscarArticuloPage } from './pages/buscar-articulo/buscar-articulo';
import { VerArticuloPage } from './pages/ver-articulo/ver-articulo';

@NgModule({
  declarations: [
    BuscarArticuloPage,
    VerArticuloPage
  ],
  imports: [
    IonicModule,
    SharedModule,
    AuthModule
  ],
  exports: [
    BuscarArticuloPage,
    VerArticuloPage
  ],
  entryComponents: [
    BuscarArticuloPage,
    VerArticuloPage
  ],
  providers: [
    ArticuloService
  ]
})
export class ArticuloModule { }
