import { NgModule } from '@angular/core';
import { IonicModule } from 'ionic-angular';

// Modules
import { SharedModule } from '../shared/shared.module';
import { AuthModule } from '../auth/auth.module';

// Providers
import { EmpresaService } from './providers/empresa.service';

// Pages
import { BuscarClientePage } from './pages/buscar-cliente/buscar-cliente';
import { VerClientePage } from './pages/ver-cliente/ver-cliente';

@NgModule({
  declarations: [
    BuscarClientePage,
    VerClientePage
  ],
  imports: [
    IonicModule,
    SharedModule,
    AuthModule
  ],
  exports: [
    BuscarClientePage,
    VerClientePage
  ],
  entryComponents: [
    BuscarClientePage,
    VerClientePage
  ],
  providers: [
    EmpresaService,
    VerClientePage
  ]
})
export class EmpresaModule { }
