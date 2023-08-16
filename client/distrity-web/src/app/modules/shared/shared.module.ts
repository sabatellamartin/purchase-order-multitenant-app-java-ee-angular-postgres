import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MaterialModule } from '../material/material.module';
import { NgxPaginationModule } from 'ngx-pagination';
import { PagerComponent } from './components/pager/pager.component';
import { MapComponent } from './components/map/map.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgxPaginationModule,
    MaterialModule
  ],
  declarations: [
    PagerComponent,
    MapComponent
  ],
  exports: [
    PagerComponent,
    MapComponent
  ],
  providers: []
})
export class SharedModule { }
