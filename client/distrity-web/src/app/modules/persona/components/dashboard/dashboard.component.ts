import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Sesion } from '../../../auth/models/sesion';
import { AutenticacionService } from '../../../auth/providers/autenticacion.service';

export interface CardItem {
  name: string;
  description: string;
  route: string;
  icon: string;
  class: string;
  access: boolean;
}

@Component({
  selector: 'persona-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  cardItems: CardItem[];

  sesion: Sesion;

  constructor(
    private router:Router,
    private autenticacionService: AutenticacionService) {
    this.getSesion();
    this.cardItems = [
        { name: "Personas",
          description: "Gestión de personas",
          route: "/persona/personas",
          icon: "people",
          class: "card green darken-1",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Tipos de documento",
          description: "Gestión de tipos de documentos de personas",
          route: "/persona/tipodocumento",
          icon: "fingerprint",
          class: "card green darken-1",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  }
    ];
  }

  ngOnInit() {}

  getSesion(): void {
    this.sesion = this.autenticacionService.getSesion();
  }

}
