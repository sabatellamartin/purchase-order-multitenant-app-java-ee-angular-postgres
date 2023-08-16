import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TipoDireccionComponent } from './components/tipo-direccion/tipo-direccion.component';
import { TipoZonaComponent } from './components/tipo-zona/tipo-zona.component';

const routes: Routes = [
  { path: 'tipodireccion', component: TipoDireccionComponent },
  { path: 'tipozona', component: TipoZonaComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UbicacionRoutingModule { }
