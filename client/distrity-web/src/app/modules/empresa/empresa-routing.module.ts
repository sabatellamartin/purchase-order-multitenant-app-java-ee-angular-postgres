import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { TipoEmpresaComponent } from './components/tipo-empresa/tipo-empresa.component';
import { CalificacionComponent } from './components/calificacion/calificacion.component';
import { EmpresaComponent } from './components/empresa/empresa.component';
import { EmpresaFormComponent } from './components/empresa-form/empresa-form.component';

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'tipoempresa', component: TipoEmpresaComponent },
  { path: 'calificacion', component: CalificacionComponent },
  { path: 'empresas', component: EmpresaComponent },
  { path: 'form', component: EmpresaFormComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmpresaRoutingModule { }
