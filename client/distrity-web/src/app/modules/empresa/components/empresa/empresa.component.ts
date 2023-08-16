import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

import { TipoEmpresa } from '../../models/tipo-empresa';
import { Empresa } from '../../models/empresa';

import { EmpresaService } from '../../providers/empresa.service';
import { TipoEmpresaService } from '../../providers/tipo-empresa.service';

@Component({
  selector: 'app-empresa',
  templateUrl: './empresa.component.html',
  styleUrls: ['./empresa.component.css']
})
export class EmpresaComponent implements OnInit {

  empresas: Empresa[];
  selectedEmpresa: Empresa;

  // Relaciones comerciales
  relaciones: Array<string> = ['PROVEEDOR', 'CLIENTE', 'DISTRIBUIDOR'];
  selectedRelacion: string = this.relaciones[0];

  // Search
  term: string = "";
  searchTime: Date =  new Date();
  // Paginator
  p: number = 1;
  total: number = 1;

  constructor(
    private router: Router,
    private empresaService: EmpresaService,
    private tipoEmpresaService: TipoEmpresaService) {
  }

  ngOnInit() {
   this.syncSearch();
  }

  compareFn( optionOne, optionTwo ) : boolean {
    optionTwo = optionTwo==null ? new Empresa():optionTwo;
    return optionOne.id === optionTwo.id;
  }

  setTerm(term: string) {
    this.term = term;
  }

  onChangeRelacion(relacion : string): void {
    this.selectedRelacion = relacion;
    this.syncSearch();
  }

  syncSearch(): void {
    this.empresaService.searchByTipo(this.term, this.selectedRelacion).then(empresas => {
      this.empresas = empresas;
      this.total = empresas.length;
    });
    this.searchTime = new Date();
  }

  newAction(): void {
    this.router.navigateByUrl('empresa/form');
  }

  editAction(empresa: Empresa): void {
    this.selectedEmpresa = empresa;
    this.router.navigate(['empresa/form'], { queryParams: { id: empresa.id, type: this.selectedRelacion } });
  }

  deleteAction(empresa: Empresa): void {
    this.empresaService.delete(empresa.id).then(() => {
      this.syncSearch();
    });
  }

  pageChanged(currentPage: number) {
    this.p = currentPage>0 ? currentPage: 1;
  }

}
