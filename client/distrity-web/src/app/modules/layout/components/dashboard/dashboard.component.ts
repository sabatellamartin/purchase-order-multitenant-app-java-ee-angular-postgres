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
  selector: 'app-dashboard',
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
        { name: "Pedidos",
          description: "Gestión de pedidos",
          route: "/documento/dashboard",
          icon: "description",
          class: "card card-border-orange",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Productos",
          description: "Gestión de productos",
          route: "/articulo/dashboard",
          icon: "store",
          class: "card card-border-green",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Empresas",
          description: "Gestión de empresas",
          route: "/empresa/dashboard",
          icon: "business",
          class: "card card-border-blue",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Personas",
          description: "Gestión de personas",
          route: "/persona/dashboard",
          icon: "people",
          class: "card card-border-blue",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Configuración",
          description: "Configuraciones de la plataforma",
          route: "/configuracion/dashboard",
          icon: "settings",
          class: "card card-border-black",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  }
        /*{ name: "Tipos de zonas",
          description: "Clasificacion de zonas",
          route: "/ubicacion/tipozona",
          icon: "supervisor_account",
          class: "card green darken-1",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Usuarios",
          description: "Gestión de usuarios",
          route: "/auth/usuarios",
          icon: "supervisor_account",
          class: "card green darken-1",
          access: this.sesion.rol=="ADMINISTRADOR" ? true : false  },
        { name: "Operadores",
          description: "Gestión de operadores",
          route: "/auth/operadores",
          icon: "supervisor_account",
          class: "card green darken-1",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Usuarios",
          description: "Gestión de usuarios y supervicion de cuentas",
          route: "/auth/users",
          icon: "supervisor_account",
          class: "card green darken-1",
          access: this.sesion.rol=="ADMINISTRADOR" ? true : false  }*/
    ];
  }

  ngOnInit() {}

  getSesion(): void {
    this.sesion = this.autenticacionService.getSesion();
  }

}
