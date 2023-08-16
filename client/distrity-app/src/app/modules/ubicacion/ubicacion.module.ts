import { NgModule } from '@angular/core';
import { IonicModule } from 'ionic-angular';

// Modules
import { SharedModule } from '../shared/shared.module';
import { AuthModule } from '../auth/auth.module';

// Providers
import { UbicacionService } from './providers/ubicacion.service';

// Pages

@NgModule({
  declarations: [
  ],
  imports: [
    IonicModule,
    SharedModule,
    AuthModule
  ],
  exports: [
  ],
  entryComponents: [
  ],
  providers: [
    UbicacionService
  ]
})
export class UbicacionModule { }
