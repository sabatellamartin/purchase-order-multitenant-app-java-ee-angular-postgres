import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

// Cryptojs
import * as crypto from 'crypto-js';

import { Sesion } from '../../models/sesion';
import { AutenticacionService } from '../../providers/autenticacion.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model: any = {};
  error: boolean = false;
  logo: string = "../../../../../assets/images/logo.svg";
  title: string = "Distribution";
  by: string = " by app";
  subtitle: string = "Aplicación de distribución de productos a empresas minoristas";
  description: string = "Distribution es un producto pensado para organizar pedidos de clientes con el objetivo de minimizar los gastos de su empresa.";

  constructor(
    private router: Router,
    private autenticacionService: AutenticacionService) {}

  ngOnInit() {
    // reset login status
    this.autenticacionService.logout();
  }

  autenticar(): void {
    this.error = false; // Set error in false
    let username = this.model.username ? this.model.username : false;
    let password = this.model.password ? crypto.SHA512(this.model.password).toString(crypto.enc.Hex) : false;
    if (username && password) {
      this.autenticacionService.login(username, password)
          .then(result => {
            result ? this.router.navigate(['/']) : this.error = true;
          }, (error) => {
              if (!error) { this.error = true; }
          });
    } else {
      this.error = true;
    }
  }

}
