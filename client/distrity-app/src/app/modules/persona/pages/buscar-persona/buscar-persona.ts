import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { FormControl } from '@angular/forms';

import 'rxjs/add/operator/debounceTime';

// Plugins
import { LoadingController } from 'ionic-angular';

// Providers
import { PersonaService } from '../../providers/persona.service';

import { Persona } from '../../models/persona';

@Component({
  selector: 'page-buscar-persona',
  templateUrl: 'buscar-persona.html'
})
export class BuscarPersonaPage {

  personas: Persona[];
  selectedPersona: Persona;

  tipo: string = 'R';

  searchTerm: string = "";
  searchTime: Date =  new Date();
  searchControl: FormControl;
  searching: any = false;

  constructor(
    public navCtrl: NavController,
    public loadingCtrl: LoadingController,
  	private personaService: PersonaService) {
    this.searchControl = new FormControl();
  }

  ionViewDidEnter() {
    this.syncSearch();
    this.searchControl.valueChanges.debounceTime(1000).subscribe(search  => {
      this.searching = false;
      this.syncSearch();
    });
  }

  syncSearch(): void {
    if (this.tipo) {
      this.personaService.searchByTipo(this.searchTerm, this.tipo).then(personas => {
        this.personas = personas;
      });
    } else {
      this.personaService.searchByTerm(this.searchTerm).then(personas => {
        this.personas = personas;
      });
    }
    this.searchTime = new Date();
  }

  onSearchInput() {
   this.searching = true;
  }

  onSegmentChange(){
    this.searchTerm = '';
    this.syncSearch();
  }

}
