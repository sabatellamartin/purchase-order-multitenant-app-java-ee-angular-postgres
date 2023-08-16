import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";

import { Sesion } from '../../../auth/models/sesion';
import { AutenticacionService } from '../../../auth/providers/autenticacion.service';

export interface MenuItem {
  name: string;
  route: string;
  icon: string;
}

export interface Brand {
  name: string;
  route: string;
  image: string;
}

@Component({
  selector: 'layout-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  brand: Brand;
  navbarItems: MenuItem[];
  menuItems: MenuItem[];
  dropMenuItems: MenuItem[];
  sidenavActions: EventEmitter<any>;
  sidenavParams: any[];
  imgPath: string = "../../../../assets/images/";
  imgUser: string = this.imgPath.concat("user.png");
  imgBanner: string = this.imgPath.concat("banner.jpg");
  sesion: Sesion;

  constructor(
    private router:Router,
    private autenticacionService: AutenticacionService) {
    const logo = this.imgPath.concat("logo.svg");
    this.brand = { name: 'Distribution', route: '/', image: logo };
    this.navbarItems = [
        { name: "Dashboard", route: "/", icon: "dashboard" }
    ];
    this.menuItems = [
        { name: "Dashboard", route: "/", icon: "dashboard" },
        { name: "Pedidos", route: "/documento/dashboard", icon: "description" },
        { name: "Productos", route: "/articulo/dashboard", icon: "store" },
        { name: "Empresas", route: "/empresa/dashboard", icon: "business" },
        { name: "Personas", route: "/persona/dashboard", icon: "people" }
    ];
    this.dropMenuItems = [
        { name: "Perfil", route: "/auth/perfil", icon: "account_box" },
        { name: "Configuración", route: "/configuracion/dashboard", icon: "settings" }
    ];
    this.sidenavActions = new EventEmitter<any>();
    this.sidenavParams = [];
  }

  ngOnInit() {
    this.getSesion();
  }

  public showSidenav(): void {
    this.sidenavParams = ['show'];
  }

  public hideSidenav(): void {
    this.sidenavParams = ['hide'];
  }

  logout() {
    this.autenticacionService.logout();
    this.router.navigate(['']);
    location.reload();
  }

  getSesion(): void {
    this.sesion = this.autenticacionService.getSesion();
  }

}
