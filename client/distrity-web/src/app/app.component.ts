import { Component } from '@angular/core';

import { Sesion } from './modules/auth/models/sesion';
import { AutenticacionService } from './modules/auth/providers/autenticacion.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  sesion: Sesion;

  constructor(private autenticacionService: AutenticacionService) { }

  getSesion(): void {
    this.sesion = this.autenticacionService.getSesion();
  }

}
