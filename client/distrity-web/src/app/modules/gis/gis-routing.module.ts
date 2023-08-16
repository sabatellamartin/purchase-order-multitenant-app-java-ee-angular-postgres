import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MapaComponent } from './components/mapa/mapa.component';

const routes: Routes = [
  { path: 'mapa', component: MapaComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GisRoutingModule { }

/*
Copyright 2017-2018 Google Inc. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at http://angular.io/license
*/
