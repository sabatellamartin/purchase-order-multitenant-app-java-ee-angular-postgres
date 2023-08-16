import { Component, ViewChild } from '@angular/core';
import { Nav, Platform } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { ScreenOrientation } from '@ionic-native/screen-orientation';

// Pages
import { HomePage } from './modules/auth/pages/home/home';
import { LoginPage } from './modules/auth/pages/login/login';
import { ProfilePage } from './modules/auth/pages/profile/profile';
import { CrearOrdenVentaPage } from './modules/documento/pages/crear-orden-venta/crear-orden-venta';
//import { BuscarArticuloPage } from './modules/articulo/pages/buscar-articulo/buscar-articulo';
//import { BuscarClientePage } from './modules/empresa/pages/buscar-cliente/buscar-cliente';
//import { BuscarDocumentoPage } from './modules/documento/pages/buscar-documento/buscar-documento';
//import { BuscarPersonaPage } from './modules/persona/pages/buscar-persona/buscar-persona';

import { LoadingController } from 'ionic-angular';
import { Events } from 'ionic-angular';

// Providers
import { AutenticacionService } from './modules/auth/providers/autenticacion.service';

class Page {
  title: string;
  icon:string;
  component: any;
}

@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  @ViewChild(Nav) nav: Nav;

  rootPage: any;

  sesion: any = false;

  loader: any;

  pages: Page[];
  picture: string = '../assets/avatars/avatar.png';

  title: string = "Distribution";

  constructor(
    public platform: Platform,
    public statusBar: StatusBar,
    public splashScreen: SplashScreen,
    private screenOrientation: ScreenOrientation,
    public loadingCtrl: LoadingController,
    public events: Events,
    private autenticacionService: AutenticacionService) {
    events.subscribe('sesion:stored', status => this.loadSesion());
    this.loadSesion();
    this.loadMenu();
    this.presentLoading();
    this.initializeApp();
  }

  private loadSesion() {
    this.autenticacionService.getSesion().then(sesion => {
      this.sesion = sesion;
      if (sesion) {
        this.rootPage = HomePage;
      } else {
        this.rootPage = LoginPage;
      }
    });
  }

  private presentLoading() {
    this.loader = this.loadingCtrl.create({
      content: "Cargando...",
      duration: 100
    });
    this.loader.present();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      this.statusBar.styleDefault();
      this.splashScreen.hide();
      // set to landscape
      this.screenOrientation.lock(this.screenOrientation.ORIENTATIONS.PORTRAIT);
    });
  }

  openPage(page) {
  //  this.nav.setRoot(page.component);
  }

  logout() {
    this.autenticacionService.logout();
    this.platform.exitApp();
  }

  private loadMenu() {
    this.pages = [
      { title: 'Buscar', icon: 'search', component: HomePage },
      /*{ title: 'Agenda', icon: 'calendar', component: BuscarClientePage },*/
      { title: 'Perfil', icon: 'contact',component: ProfilePage },
      /*{ title: 'Artículos', icon: 'cube',component: BuscarArticuloPage },*/
      { title: 'Pedido', icon: 'list-box', component: CrearOrdenVentaPage }
    ];
  }

  openProfilePage() {
    this.nav.setRoot(ProfilePage);
    this.rootPage = ProfilePage;
  }

}
