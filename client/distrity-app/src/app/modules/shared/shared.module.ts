import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedService } from './providers/shared.service';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    //SharedComponent
  ],
  entryComponents: [
    //SharedComponent
  ],
  exports: [
    //SharedComponent
  ],
  providers: [
    SharedService
  ]
})
export class SharedModule { }
