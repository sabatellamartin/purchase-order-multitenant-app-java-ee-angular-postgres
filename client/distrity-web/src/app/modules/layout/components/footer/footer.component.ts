import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";

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
  selector: 'layout-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {
  brand: Brand;
  copyright: string;
  menuItems: MenuItem[];
  web: MenuItem;
  description: string;

  constructor(private router:Router) {
    const logo = "../../../../assets/images/logo.svg";
    this.brand = { name: 'Distribution', route: '/', image: logo };
    this.menuItems = [
        { name: "app", route: "http://www.localhost", icon: "" },
        { name: "Distribution", route: "http://distrity.localhost", icon: "" }
    ];
    this.web = { name: "Web Distribution", route: "http://distrity.localhost", icon: "" };
    this.copyright = "© 2018  Distribution by app. Todos los derechos reservados.";
    this.description = "Aplicación para distribución a minoristas";
  }

  ngOnInit() {}

}
