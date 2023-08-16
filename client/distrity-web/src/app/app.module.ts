import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

// Modules
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AuthModule } from './modules/auth/auth.module';
import { LayoutModule } from './modules/layout/layout.module';
import { MaterialModule } from './modules/material/material.module';
import { MaterializeModule } from 'angular2-materialize';
import { SharedModule } from './modules/shared/shared.module';
import { GisModule } from './modules/gis/gis.module';
import { PersonaModule } from './modules/persona/persona.module';
import { EmpresaModule } from './modules/empresa/empresa.module';
import { UbicacionModule } from './modules/ubicacion/ubicacion.module';
import { ArticuloModule } from './modules/articulo/articulo.module';
import { DocumentoModule } from './modules/documento/documento.module';

import { MyDateRangePickerModule } from 'mydaterangepicker';

// Components
import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpModule,
    SharedModule,
    AuthModule,
    LayoutModule,
    GisModule,
    PersonaModule,
    EmpresaModule,
    UbicacionModule,
    ArticuloModule,
    DocumentoModule,
    MaterialModule,
    MaterializeModule,
    MyDateRangePickerModule,
    AppRoutingModule // Order imports matters, AppRouting is a last module
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
