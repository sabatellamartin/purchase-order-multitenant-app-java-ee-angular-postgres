import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// Routing
import { ConfiguracionRoutingModule } from './configuracion-routing.module';

// Paginator
import { NgxPaginationModule } from 'ngx-pagination';

// Modules
import { MaterialModule } from '../material/material.module';
import { SharedModule } from '../shared/shared.module';

// Components
import { ConfiguracionComponent } from './components/configuracion/configuracion.component';
import { CargaDatosComponent } from './components/carga-datos/carga-datos.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ConfiguracionRoutingModule,
    MaterialModule,
    SharedModule,
    NgxPaginationModule
  ],
  declarations: [
    ConfiguracionComponent,
    CargaDatosComponent
  ],
  exports: [
    ConfiguracionComponent,
    CargaDatosComponent
  ],
  providers: [
  ]
})
export class ConfiguracionModule { }
