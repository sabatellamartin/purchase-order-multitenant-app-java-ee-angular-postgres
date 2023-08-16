import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// Routing
import { EmpresaRoutingModule } from './empresa-routing.module';

// Paginator
import { NgxPaginationModule } from 'ngx-pagination';

// Modules
import { MaterialModule } from '../material/material.module';
import { SharedModule } from '../shared/shared.module';
import { GisModule } from '../gis/gis.module';

// Components
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { TipoEmpresaComponent } from './components/tipo-empresa/tipo-empresa.component';
import { CalificacionComponent } from './components/calificacion/calificacion.component';
import { EmpresaComponent } from './components/empresa/empresa.component';
import { EmpresaFormComponent } from './components/empresa-form/empresa-form.component';

// Providers
import { TipoEmpresaService } from './providers/tipo-empresa.service';
import { CalificacionService } from './providers/calificacion.service';
import { EmpresaService } from './providers/empresa.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    EmpresaRoutingModule,
    NgxPaginationModule,
    MaterialModule,
    SharedModule,
    GisModule
  ],
  declarations: [
    DashboardComponent,
    TipoEmpresaComponent,
    CalificacionComponent,
    EmpresaComponent,
    EmpresaFormComponent
  ],
  exports: [
    DashboardComponent,
    TipoEmpresaComponent,
    CalificacionComponent,
    EmpresaComponent,
    EmpresaFormComponent
  ],
  providers: [
    TipoEmpresaService,
    CalificacionService,
    EmpresaService
  ]
})
export class EmpresaModule { }
