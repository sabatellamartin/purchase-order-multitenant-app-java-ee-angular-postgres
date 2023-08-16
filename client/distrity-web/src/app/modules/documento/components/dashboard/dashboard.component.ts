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
  selector: 'documento-dashboard',
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
        { name: "Moneda",
          description: "Gestión de monedas",
          route: "/documento/monedas",
          icon: "attach_money",
          class: "card green darken-1",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Documentos",
          description: "Gestión de documentos",
          route: "/documento/documentos",
          icon: "description",
          class: "card green darken-1",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  }
    ];
  }

  ngOnInit() {}

  getSesion(): void {
    this.sesion = this.autenticacionService.getSesion();
  }

}
