import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from 'ionic-angular';

// Modules
import { SharedModule } from '../shared/shared.module';
import { DocumentoModule } from '../documento/documento.module';

// Pages
import { HomePage } from './pages/home/home';
import { LoginPage } from './pages/login/login';
import { ProfilePage } from './pages/profile/profile';
import { ChangePasswordPage } from './pages/change-password/change-password';

// Providers
import { AutenticacionService } from './providers/autenticacion.service';
import { UsuarioService } from './providers/usuario.service';

@NgModule({
  declarations: [
    HomePage,
    LoginPage,
    ProfilePage,
    ChangePasswordPage
  ],
  imports: [
    CommonModule,
    IonicModule,
    SharedModule,
    DocumentoModule
  ],
  exports: [
    HomePage,
    LoginPage,
    ProfilePage,
    ChangePasswordPage
  ],
  entryComponents: [
    HomePage,
    LoginPage,
    ProfilePage,
    ChangePasswordPage
  ],
  providers: [
    AutenticacionService,
    UsuarioService
  ]
})
export class AuthModule { }
