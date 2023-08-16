import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// Routing
import { PersonaRoutingModule } from './persona-routing.module';

// Paginator
import { NgxPaginationModule } from 'ngx-pagination';

// Modules
import { MaterialModule } from '../material/material.module';
import { SharedModule } from '../shared/shared.module';

// Components
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { TipoDocumentoComponent } from './components/tipo-documento/tipo-documento.component';
import { PersonaComponent } from './components/persona/persona.component';

// Providers
import { TipoDocumentoService } from './providers/tipo-documento.service';
import { PersonaService } from './providers/persona.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    PersonaRoutingModule,
    MaterialModule,
    NgxPaginationModule,
    SharedModule
  ],
  declarations: [
    DashboardComponent,
    TipoDocumentoComponent,
    PersonaComponent
  ],
  exports: [
    DashboardComponent,
    TipoDocumentoComponent,
    PersonaComponent
  ],
  providers: [
    TipoDocumentoService,
    PersonaService
  ]
})
export class PersonaModule { }
