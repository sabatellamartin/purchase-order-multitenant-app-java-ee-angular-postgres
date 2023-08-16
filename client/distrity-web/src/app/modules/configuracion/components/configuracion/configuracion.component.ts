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
  selector: 'app-configuracion',
  templateUrl: './configuracion.component.html',
  styleUrls: ['./configuracion.component.css']
})
export class ConfiguracionComponent implements OnInit {

  items: CardItem[];

  sesion: Sesion;

  constructor(
    private router:Router,
    private autenticacionService: AutenticacionService) {
    this.getSesion();
    this.items = [
        { name: "Gestión de usuarios",
          description: "Gestión de usuarios operadores de la plataforma",
          route: "/auth/operadores",
          icon: "people",
          class: "card card-border-grey",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Categorías",
          description: "Gestión de categorías de productos",
          route: "/articulo/categorias",
          icon: "style",
          class: "card card-border-orange",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Impuestos",
          description: "Gestión de impuestos sobre productos",
          route: "/articulo/impuestos",
          icon: "pie_chart",
          class: "card card-border-green",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Unidades de medida",
          description: "Gestión de formatos de unidades para artículos",
          route: "/articulo/unidades",
          icon: "class",
          class: "card card-border-orange",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Divisas",
          description: "Gestión de divisas",
          route: "/documento/monedas",
          icon: "attach_money",
          class: "card card-border-green",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Carga de datos",
          description: "Carga masiva de datos",
          route: "/configuracion/carga/datos",
          icon: "cloud_upload",
          class: "card card-border-orange",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Categorizaciones de empresas",
          description: "Categorizaciones, calificaciones o etiquetas sobre las empresas",
          route: "/empresa/calificacion",
          icon: "star",
          class: "card card-border-blue",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Tipos de documentos",
          description: "Tipos de documentos de personas",
          route: "/persona/tipodocumento",
          icon: "fingerprint",
          class: "card card-border-blue",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Entidades empresariales",
          description: "Gestión de entidades empresariales o tipos de empresa.",
          route: "/empresa/tipoempresa",
          icon: "business_center",
          class: "card card-border-blue",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  },
        { name: "Empresa distribuidora",
          description: "Gestión de empresa distribuidora",
          route: "/empresa/dashboard",
          icon: "local_shipping",
          class: "card card-border-blue",
          access: this.sesion.rol=="PROPIETARIO" ? true : false  }

    ];
  }

  ngOnInit() {}

  getSesion(): void {
    this.sesion = this.autenticacionService.getSesion();
  }

}
