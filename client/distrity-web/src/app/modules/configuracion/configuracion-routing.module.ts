import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ConfiguracionComponent } from './components/configuracion/configuracion.component';
import { CargaDatosComponent } from './components/carga-datos/carga-datos.component';

const routes: Routes = [
  { path: 'dashboard', component: ConfiguracionComponent },
  { path: 'carga/datos', component: CargaDatosComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConfiguracionRoutingModule { }
