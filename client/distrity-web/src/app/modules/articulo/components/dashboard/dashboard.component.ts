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
  selector: 'articulo-dashboard',
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
        { name: "Unidades",
          description: "Gestión de unidades",
          route: "/articulo/unidades",
          icon: "class",
          class: "card green darken-1",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Articulos",
          description: "Gestión de articulos",
          route: "/articulo/articulos",
          icon: "shopping_cart",
          class: "card green darken-1",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  }
    ];
  }

  ngOnInit() {}

  getSesion(): void {
    this.sesion = this.autenticacionService.getSesion();
  }

}
