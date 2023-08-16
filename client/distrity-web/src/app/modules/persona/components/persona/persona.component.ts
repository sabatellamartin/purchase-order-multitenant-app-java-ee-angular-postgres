import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

import { TipoDocumento } from '../../models/tipo-documento';
import { Persona } from '../../models/persona';

import { PersonaService } from '../../providers/persona.service';
import { TipoDocumentoService } from '../../providers/tipo-documento.service';

@Component({
  selector: 'app-persona',
  templateUrl: './persona.component.html',
  styleUrls: ['./persona.component.css']
})
export class PersonaComponent implements OnInit {

  personas: Persona[];
  @Input() selectedPersona: Persona;

  tiposDocumentos: TipoDocumento[];

  tipos: Array<string> = [];
  selectedTipo: string = 'CONSUMIDOR';

  term: string = "";
  searchTime: Date =  new Date();

  datePickerParams: any;

  modalForm = new EventEmitter<string|MaterializeAction>();

  // Modal Title
  modalTitle: string = "";

  // Paginator
  p: number = 1;
  total: number = 1;

  constructor(
    private router: Router,
    private personaService: PersonaService,
    private tipoDocumentoService: TipoDocumentoService) {
      // Load datepicker params internacionalization
      this.loadPickerParams();
  }

  ngOnInit() {
   this.cargarTiposDocumentos();
   this.cargarTiposPersonas();
   this.searchByTipo();
  }

  private cargarTiposDocumentos(): void {
     this.tipoDocumentoService.getAll().then(response => {
       this.tiposDocumentos = response;
     });
  }

  compareFn( optionOne, optionTwo ) : boolean {
    return optionOne.id === optionTwo.id;
  }

  private cargarTiposPersonas(): void {
     this.tipos.push('REFERENTE');
     this.tipos.push('EMPLEADO');
     this.tipos.push('CONSUMIDOR');
  }

  private loadPickerParams(): void {
    this.datePickerParams = [{
        // The title label to use for the month nav buttons
        labelMonthNext: 'Mes siguiente',
        labelMonthPrev: 'Mes anterior',
        // The title label to use for the dropdown selectors
        labelMonthSelect: 'Selecciona un mes',
        labelYearSelect: 'Selecciona un año',
        // Months and weekdays
        monthsFull: [ 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre' ],
        monthsShort: [ 'Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic' ],
        weekdaysFull: [ 'Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado' ],
        weekdaysShort: [ 'Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab' ],
        // Materialize modified
        weekdaysLetter: [ 'D', 'L', 'M', 'X', 'J', 'V', 'S' ],
        // Today and clear
        today: 'Hoy',
        clear: 'Limpiar',
        close: 'Cerrar',
        // Format date
        format: 'dd/mm/yyyy'
     }];
  }

  openModal() {
    this.modalForm.emit({action:"modal",params:['open']});
  }

  closeModal() {
    this.modalForm.emit({action:"modal",params:['close']});
  }

  setTerm(term: string) {
    this.term = term;
  }

  syncSearch() {
    this.searchByTipo();
  }

  getPersonas(): void {
    this.personaService.getAll().then(personas => this.personas = personas);
  }

  existByDocumento(numero:string, tipoDocumento: TipoDocumento) {
    if (numero) {
      this.personaService.existByDocumento(numero, tipoDocumento).then(result => {
         if(result) { toast("Documento " + numero + " de tipo " + tipoDocumento.sigla + " ya existe", 4000); }
       });
     }
  }

  onChangeTipo(rol : string): void {
    this.selectedTipo = rol;
    this.searchByTipo();
  }

  searchByTipo(): void {
    this.personaService.searchByTipo(this.term, this.selectedTipo).then(personas => {
      this.personas = personas;
      this.total = personas.length;
    });
    this.searchTime = new Date();
  }

  removeAction(persona: Persona): void {
    this.delete(persona);
  }

  newAction(): void {
    this.selectedPersona = new Persona();
    this.selectedPersona.tipoDocumento = this.tiposDocumentos[0];
    this.modalTitle = "Nuevo";
    this.openModal();
  }

  editAction(persona: Persona): void {
    this.selectedPersona = persona;
    this.modalTitle = "Modificar";
    this.openModal();
  }

  save() {
    if (this.selectedPersona) {
      this.selectedPersona.direccionEmail = this.selectedPersona.direccionEmail.trim();
      if (this.selectedPersona.id) {
        this.personaService.update(this.selectedPersona, this.selectedTipo).then(result => {
          if (result) { toast("Persona " + this.selectedPersona.tipoDocumento.sigla + " nro. " + this.selectedPersona.numeroDocumento + " editado", 4000); }
          this.searchByTipo();
          this.selectedPersona = null;
        });
      } else {
        this.personaService.create(this.selectedPersona, this.selectedTipo).then(result => {
          if (result) { toast("Persona " + this.selectedPersona.tipoDocumento.sigla + " nro. " + this.selectedPersona.numeroDocumento + " creado", 4000); }
          this.searchByTipo();
          this.selectedPersona = null;
        });
      }
    }
  }

  private delete(persona: Persona): void {
    this.personaService.delete(persona.id)
      .then(() => {
        this.searchByTipo();
      });
  }

  pageChanged(currentPage: number) {
    this.p = currentPage>0 ? currentPage: 1;
  }

}
