import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// Routing
import { GisRoutingModule } from './gis-routing.module';

// Modules
import { MaterialModule } from '../material/material.module';
import { SharedModule } from '../shared/shared.module';

// Components
import { MapaComponent } from './components/mapa/mapa.component';

// Providers
import { GeoService } from './providers/geo.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MaterialModule,
    SharedModule,
    GisRoutingModule
  ],
  declarations: [
    MapaComponent
  ],
  exports: [
    MapaComponent
  ],
  providers: [
    GeoService
  ]
})
export class GisModule { }
