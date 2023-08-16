import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { MonedaComponent } from './components/moneda/moneda.component';
import { DocumentoComponent } from './components/documento/documento.component';
import { DocumentoFormComponent } from './components/documento-form/documento-form.component';

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'monedas', component: MonedaComponent },
  { path: 'documentos', component: DocumentoComponent },
  { path: 'form', component: DocumentoFormComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DocumentoRoutingModule { }
