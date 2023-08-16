import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// Modules
import { RouterModule } from '@angular/router';
import { MaterialModule } from '../material/material.module';
import { GisModule } from '../gis/gis.module';

// Components
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    GisModule,
    RouterModule
  ],
  declarations: [
    DashboardComponent,
    NavbarComponent,
    FooterComponent
  ],
  exports: [
    DashboardComponent,
    NavbarComponent,
    FooterComponent
  ],
  providers: []
})
export class LayoutModule { }
