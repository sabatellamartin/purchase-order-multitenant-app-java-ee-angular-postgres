import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// Routing
import { AuthRoutingModule } from './auth-routing.module';

// Paginator
import { NgxPaginationModule } from 'ngx-pagination';

// Modules
import { MaterialModule } from '../material/material.module';
import { SharedModule } from '../shared/shared.module';

// Components
import { LoginComponent } from './components/login/login.component';
import { UsuarioComponent } from './components/usuario/usuario.component';
import { OperadorComponent } from './components/operador/operador.component';
import { PerfilComponent } from './components/perfil/perfil.component';

// Providers
import { AutenticacionService } from './providers/autenticacion.service';
import { UsuarioService } from './providers/usuario.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    AuthRoutingModule,
    MaterialModule,
    SharedModule,
    NgxPaginationModule
  ],
  declarations: [
    LoginComponent,
    UsuarioComponent,
    OperadorComponent,
    PerfilComponent
  ],
  exports: [
    LoginComponent,
    UsuarioComponent,
    OperadorComponent,
    PerfilComponent
  ],
  providers: [
    AutenticacionService,
    UsuarioService
  ]
})
export class AuthModule { }
