import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// Routing
import { DocumentoRoutingModule } from './documento-routing.module';

// Paginator
import { NgxPaginationModule } from 'ngx-pagination';

//DatePicker
import { MyDateRangePickerModule } from 'mydaterangepicker';
import { MyDatePickerModule } from 'mydatepicker';

// Modules
import { MaterialModule } from '../material/material.module';
import { SharedModule } from '../shared/shared.module';
import { ArticuloModule } from '../articulo/articulo.module';

// Components
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { MonedaComponent } from './components/moneda/moneda.component';
import { DocumentoComponent } from './components/documento/documento.component';
import { DocumentoFormComponent } from './components/documento-form/documento-form.component';
import { EditDetalleComponent } from './components/edit-detalle/edit-detalle.component';

// Providers
import { MonedaService } from './providers/moneda.service';
import { DocumentoService } from './providers/documento.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    DocumentoRoutingModule,
    NgxPaginationModule,
    MyDateRangePickerModule,
    MyDatePickerModule,
    MaterialModule,
    SharedModule,
    ArticuloModule
  ],
  declarations: [
    DashboardComponent,
    MonedaComponent,
    DocumentoComponent,
    DocumentoFormComponent,
    EditDetalleComponent
  ],
  exports: [
    DashboardComponent,
    MonedaComponent,
    DocumentoComponent,
    DocumentoFormComponent
  ],
  providers: [
    MonedaService,
    DocumentoService
  ]
})
export class DocumentoModule { }
