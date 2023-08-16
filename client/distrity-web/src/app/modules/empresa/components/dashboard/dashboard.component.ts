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
  selector: 'empresa-dashboard',
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
        { name: "Empresas",
          description: "Gestión de empresas",
          route: "/empresa/empresas",
          icon: "business",
          class: "card green darken-1",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Tipos de empresas",
          description: "Gestión de tipos de empresas",
          route: "/empresa/tipoempresa",
          icon: "business_center",
          class: "card green darken-1",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Calificaciones",
          description: "Gestión calificaciones de empresas",
          route: "/empresa/calificacion",
          icon: "star",
          class: "card green darken-1",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  }
    ];
  }

  ngOnInit() {}

  getSesion(): void {
    this.sesion = this.autenticacionService.getSesion();
  }

}
