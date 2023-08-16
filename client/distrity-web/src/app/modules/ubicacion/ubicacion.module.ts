import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// Routing
import { UbicacionRoutingModule } from './ubicacion-routing.module';

// Modules
import { MaterialModule } from '../material/material.module';
import { SharedModule } from '../shared/shared.module';

// Components
import { TipoDireccionComponent } from './components/tipo-direccion/tipo-direccion.component';
import { TipoZonaComponent } from './components/tipo-zona/tipo-zona.component';

// Providers
import { TipoDireccionService } from './providers/tipo-direccion.service';
import { TipoZonaService } from './providers/tipo-zona.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    UbicacionRoutingModule,
    MaterialModule,
    SharedModule
  ],
  declarations: [
    TipoDireccionComponent,
    TipoZonaComponent
  ],
  exports: [
    TipoDireccionComponent,
    TipoZonaComponent
  ],
  providers: [
    TipoDireccionService,
    TipoZonaService,
  ]
})
export class UbicacionModule { }
