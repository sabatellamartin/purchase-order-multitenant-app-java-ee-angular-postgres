import { NgModule } from '@angular/core';
import { IonicModule } from 'ionic-angular';

// Modules
import { SharedModule } from '../shared/shared.module';
import { AuthModule } from '../auth/auth.module';

// Providers
import { PersonaService } from './providers/persona.service';

// Pages
import { BuscarPersonaPage } from './pages/buscar-persona/buscar-persona';

@NgModule({
  declarations: [
    BuscarPersonaPage
  ],
  imports: [
    IonicModule,
    SharedModule,
    AuthModule
  ],
  exports: [
    BuscarPersonaPage
  ],
  entryComponents: [
    BuscarPersonaPage
  ],
  providers: [
    PersonaService
  ]
})
export class PersonaModule { }
