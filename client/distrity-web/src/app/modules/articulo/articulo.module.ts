import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// Routing
import { ArticuloRoutingModule } from './articulo-routing.module';

// Paginator
import { NgxPaginationModule } from 'ngx-pagination';

// Modules
import { MaterialModule } from '../material/material.module';
import { SharedModule } from '../shared/shared.module';

// Components
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ArticuloComponent } from './components/articulo/articulo.component';
import { UnidadComponent } from './components/unidad/unidad.component';
import { CategoriaComponent } from './components/categoria/categoria.component';
import { ImpuestoComponent } from './components/impuesto/impuesto.component';
import { SeleccionArticuloComponent } from './components/seleccion-articulo/seleccion-articulo.component';

// Providers
import { ArticuloService } from './providers/articulo.service';
import { UnidadService } from './providers/unidad.service';
import { CategoriaService } from './providers/categoria.service';
import { ImpuestoService } from './providers/impuesto.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ArticuloRoutingModule,
    MaterialModule,
    SharedModule,
    NgxPaginationModule
  ],
  declarations: [
    DashboardComponent,
    ArticuloComponent,
    UnidadComponent,
    CategoriaComponent,
    ImpuestoComponent,
    SeleccionArticuloComponent
  ],
  exports: [
    DashboardComponent,
    ArticuloComponent,
    UnidadComponent,
    CategoriaComponent,
    ImpuestoComponent,
    SeleccionArticuloComponent
  ],
  providers: [
    ArticuloService,
    UnidadService,
    CategoriaService,
    ImpuestoService,
  ]
})
export class ArticuloModule { }
