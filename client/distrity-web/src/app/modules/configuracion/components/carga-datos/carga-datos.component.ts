import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

import { ArticuloService } from '../../../articulo/providers/articulo.service';

export class Help {
  title: string;
  header: string;
  body: string[];
  fileExample: any;
  linkExample: string;
}

@Component({
  selector: 'configuracion-carga-datos',
  templateUrl: './carga-datos.component.html',
  styleUrls: ['./carga-datos.component.css']
})
export class CargaDatosComponent implements OnInit {

  modalHelp = new EventEmitter<string|MaterializeAction>();

  help: Help = new Help();

  modalOutput = new EventEmitter<string|MaterializeAction>();
  output: any;

  constructor(
    private router: Router,
    private articuloService: ArticuloService) {
  }

  ngOnInit() {
    this.loadHelp();
  }

  openModalHelp(){
    this.modalHelp.emit({action:"modal",params:['open']});
  }

  closeModalHelp() {
    this.modalHelp.emit({action:"modal",params:['close']});
  }

  openModalOutput(){
    this.modalOutput.emit({action:"modal",params:['open']});
  }

  closeModalOutput() {
    this.modalOutput.emit({action:"modal",params:['close']});
  }

  loadHelp(){
    this.help.title = "Ayuda carga de artículos";
    this.help.header = "La carga de datos se realiza mediante archivos de texto con información estructurada en formato JSON.";
    this.help.body = [
      "No importa el nombre del archivo a subir",
      "La información debe respetar el formato JSON.",
      "Puede verificar la estructura JSON en https://jsonlint.com/",
      "Los artículos son identificados por código.",
      "Si existe el código actualiza la información.",
      "Si no existe el código crea un nuevo registro.",
      "Al final del proceso se retornaran los registros fallidos",
      "Si no hay retorno entonces todos los datos fueron procesados de forma correcta.",
      "Para agregar más de un dato personal [{ datos A...}, { datos B...}, { datos C...}]",
      "La estructura para datos personales tiene el siguiente formato..."
    ];
    this.help.fileExample = [{
        "codigo": "EXAMPLE01",
        "codigoBarra": "78985465132115",
        "nombre": "Nombre",
        "descripcion": "Descripcion",
        "precioCompra": 50,
        "precioVenta": 100,
        "precioBase": 70,
        "porcentajeDescuento": 20,
        "observaciones": "Observaciones",
        "fechaBaja": null
    }];
    this.help.linkExample = "/assets/files/articulos";
  }

  fileChangeDatosArticulos(event) {
    let fileList: FileList = event.target.files;
    if(fileList.length > 0) {
      let file: File = fileList[0];
      let formData:FormData = new FormData();
      formData.append('uploadFile', file, file.name);
      this.articuloService.loadDatosArticulosFile(formData).then(response => {
        this.output = response;
        this.openModalOutput();
      });
    }
  }


}
