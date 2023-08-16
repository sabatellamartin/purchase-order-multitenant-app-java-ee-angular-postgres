import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DashboardComponent } from './modules/layout/components/dashboard/dashboard.component';

const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'auth', loadChildren: './modules/auth/auth.module#AuthModule' },
  { path: 'empresa', loadChildren: './modules/empresa/empresa.module#EmpresaModule' },
  { path: 'persona', loadChildren: './modules/persona/persona.module#PersonaModule' },
  { path: 'ubicacion', loadChildren: './modules/ubicacion/ubicacion.module#UbicacionModule' },
  { path: 'articulo', loadChildren: './modules/articulo/articulo.module#ArticuloModule' },
  { path: 'documento', loadChildren: './modules/documento/documento.module#DocumentoModule' },
  { path: 'configuracion', loadChildren: './modules/configuracion/configuracion.module#ConfiguracionModule' }
  //,{ path: '', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }


/*
Copyright 2017-2018 Google Inc. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at http://angular.io/license
*/
