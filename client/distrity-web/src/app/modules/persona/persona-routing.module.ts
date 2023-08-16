import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { TipoDocumentoComponent } from './components/tipo-documento/tipo-documento.component';
import { PersonaComponent } from './components/persona/persona.component';

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'tipodocumento', component: TipoDocumentoComponent },
  { path: 'personas', component: PersonaComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PersonaRoutingModule { }
