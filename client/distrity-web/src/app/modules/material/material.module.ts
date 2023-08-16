import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule, MatCheckboxModule } from '@angular/material';
import { MatIconModule } from '@angular/material/icon';
import { MaterializeModule } from "angular2-materialize";

@NgModule({
  imports: [
    CommonModule,
    MaterializeModule,
    MatToolbarModule,
    MatButtonModule,
    MatCheckboxModule,
    MatIconModule
  ],
  exports: [
    CommonModule,
    MaterializeModule,
    MatToolbarModule,
    MatButtonModule,
    MatCheckboxModule,
    MatIconModule
  ],
  declarations: []
})
export class MaterialModule { }
