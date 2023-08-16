import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ArticuloComponent } from './components/articulo/articulo.component';
import { UnidadComponent } from './components/unidad/unidad.component';
import { CategoriaComponent } from './components/categoria/categoria.component';
import { ImpuestoComponent } from './components/impuesto/impuesto.component';

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'articulos', component: ArticuloComponent },
  { path: 'unidades', component: UnidadComponent },
  { path: 'categorias', component: CategoriaComponent },
  { path: 'impuestos', component: ImpuestoComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ArticuloRoutingModule { }
