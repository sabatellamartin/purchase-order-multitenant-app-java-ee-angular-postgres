import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';

import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import { ListPage } from '../pages/list/list';

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { ScreenOrientation } from '@ionic-native/screen-orientation';

import { HttpModule } from '@angular/http';

// Plugins
import { NativeStorage } from '@ionic-native/native-storage';

// Modules
import { AuthModule } from './modules/auth/auth.module';
import { SharedModule } from './modules/shared/shared.module';

import { ArticuloModule } from './modules/articulo/articulo.module';
import { EmpresaModule } from './modules/empresa/empresa.module';
import { PersonaModule } from './modules/persona/persona.module';
import { UbicacionModule } from './modules/ubicacion/ubicacion.module';
import { DocumentoModule } from './modules/documento/documento.module';

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    ListPage
  ],
  imports: [
    BrowserModule,
    HttpModule,
    AuthModule,
    SharedModule,
    ArticuloModule,
    EmpresaModule,
    PersonaModule,
    UbicacionModule,
    DocumentoModule,
    IonicModule.forRoot(MyApp),
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    ListPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    NativeStorage,
    ScreenOrientation,
    {provide: ErrorHandler, useClass: IonicErrorHandler}
  ]
})
export class AppModule {}
