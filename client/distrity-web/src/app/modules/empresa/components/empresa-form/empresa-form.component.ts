import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';

import { TipoEmpresa } from '../../models/tipo-empresa';
import { Calificacion } from '../../models/calificacion';
import { Empresa } from '../../models/empresa';
import { Sucursal } from '../../models/sucursal';
import { Direccion } from '../../models/direccion';
//import { Punto } from '../../models/punto';

import { EmpresaService } from '../../providers/empresa.service';
import { CalificacionService } from '../../providers/calificacion.service';
import { TipoEmpresaService } from '../../providers/tipo-empresa.service';

//import { AddressResponse } from '../../../shared/models/address-response';
/*
import OlMap from 'ol/Map';
import OlView from 'ol/View';
import OlTileLayer from 'ol/layer/Tile';
import OlXYZ from 'ol/source/XYZ';
import { fromLonLat, transform, get as getProjection } from 'ol/proj';
import { defaults as defaultControls } from 'ol/control';
import MousePosition from 'ol/control/MousePosition';
import { createStringXY } from 'ol/coordinate';

import { Circle as CircleStyle, Fill, Stroke, Style, Icon} from 'ol/style';
import { Vector as VectorLayer } from 'ol/layer';
import { Vector as VectorSource } from 'ol/source';
import Collection from 'ol/Collection';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import GML3 from 'ol/format/GML3';
import WFS from 'ol/format/WFS';*/

@Component({
  selector: 'app-empresa-form',
  templateUrl: './empresa-form.component.html',
  styleUrls: ['./empresa-form.component.css']
})
export class EmpresaFormComponent implements OnInit {

  selectedEmpresa: Empresa = new Empresa();

  selectedSucursal: Sucursal = new Sucursal();
  selectedDireccion: Direccion = new Direccion();

  tiposEmpresas: TipoEmpresa[] = [];
  selectedTipoEmpresa: TipoEmpresa;

  calificaciones: Calificacion[] = [];
  selectedCalificacion: Calificacion;

  relaciones: Array<string> = ['PROVEEDOR', 'CLIENTE', 'DISTRIBUIDOR'];
  selectedRelacion: string = this.relaciones[0];
  disabledRelacion: Boolean = false;

  title: string;
/*
  //Search
  addressQuery: string;
  addresses: Array<AddressResponse> = new Array<AddressResponse>();
  selectedAddress: AddressResponse = new AddressResponse();

  // Mapa
  map: OlMap;
  osm: OlXYZ;
  layer: OlTileLayer;
  view: OlView;
  // Para vectores
  source: VectorSource;
  vector: VectorLayer;
  // Map View
  //projection: string = "EPSG:3857"; // "EPSG:4326" "EPSG:3857" "EPSG:900913"
  center: number[] = [-56.1641532297833, -34.89803020867047];//[-6964295.986861576, -4146874.6198026435];
  zoom: number =  10;
*/
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private empresaService: EmpresaService,
    private tipoEmpresaService: TipoEmpresaService,
    private calificacionService: CalificacionService) {
  }

  ngOnInit() {
    this.init();
    /*// Create Vector
    this.createVector();
    // Init Map
    this.initMap();
    //Evento click en el mapa
    this.map.on('click', (e) => {
      let coord = e.coordinate;
      coord = transform(
        e.coordinate,
        getProjection('EPSG:3857'),
        getProjection('EPSG:4326'));
      this.map.getView().setCenter(coord);
      console.log("lon y lat"+coord[0]+" "+coord[1]);
      this.selectedDireccion.longitud = coord[0];
      this.selectedDireccion.latitud = coord[1];
      //this.selectedEmpresa.feature = coord[0] + " " + coord[1];
      this.removeAllFeatures();
      this.addFeature(new Punto(0,coord[0],coord[1]));
      this.reverseAddressAction(coord[0],coord[1]);
      //console.log(this.transactWFS('delete'));
    });*/
  }

  private init(): void {
    this.getAllCalificaciones();
    this.getAllTiposEmpresas();
    this.selectedEmpresa = new Empresa();
    this.selectedEmpresa.tipoEmpresa = this.selectedTipoEmpresa;
    this.selectedEmpresa.calificacion = this.selectedCalificacion;
    this.route.queryParams.subscribe(params => { // Get Params
      const empresaId = params['id']; // Defaults to undefined if no query param provided.
      // Obtengo relacion comercial por parametros
      this.selectedRelacion = this.relaciones.includes(params['type']) ? params['type'] : this.relaciones[0];
      if (empresaId > 0) {
        this.title = "Modificar empresa ".concat(this.selectedRelacion.toLowerCase());
        this.disabledRelacion = true; // desabilito el combo de relacion comercial
        this.empresaService.getById(empresaId).then(empresa => {
          this.selectedEmpresa = empresa;
          console.log(empresa);
        });
      } else {
        this.title = "Crear empresa";
      }
    });
  }

  private getAllCalificaciones(): void {
    this.calificacionService.getAll().then(calificaciones => {
      this.calificaciones = calificaciones;
      this.selectedCalificacion = calificaciones[0];
    });
  }

  private getAllTiposEmpresas(): void {
    this.tipoEmpresaService.getAll().then(tipos => {
     this.tiposEmpresas = tipos;
     this.selectedTipoEmpresa = tipos[0];
    });
  }

  compareFn( optionOne, optionTwo ) : boolean {
    optionTwo = optionTwo==null ? new Empresa():optionTwo;
    return optionOne.id === optionTwo.id;
  }

  onChangeRelacion(relacion : string): void {
    this.selectedRelacion = relacion;
  }

  save() {
    if (this.selectedEmpresa) {
      this.selectedEmpresa.tipoEmpresa = this.selectedTipoEmpresa;
      this.selectedEmpresa.calificacion = this.selectedCalificacion;
      this.selectedEmpresa.email = this.selectedEmpresa.email ? this.selectedEmpresa.email.trim().toLowerCase() : null;
      this.selectedEmpresa.web = this.selectedEmpresa.web ? this.selectedEmpresa.web.trim().toLowerCase():null;
      console.log(this.selectedEmpresa);
      console.log(JSON.stringify(this.selectedEmpresa));
      if (this.selectedEmpresa.id) {
        this.empresaService.update(this.selectedEmpresa, this.selectedRelacion).then(result => {
          if (result) { toast("Empresa " + this.selectedEmpresa.nombreComercial + " editado", 4000); }
          this.empresaService.getById(this.selectedEmpresa.id).then(empresa => this.selectedEmpresa = empresa );
        });
      } else {
        this.empresaService.create(this.selectedEmpresa, this.selectedRelacion).then(result => {
          if (result) { toast("Empresa " + this.selectedEmpresa.nombreComercial + " nro. " + this.selectedEmpresa.rut + " creado", 4000); }
          this.selectedEmpresa = new Empresa();
          this.selectedEmpresa.tipoEmpresa = this.tiposEmpresas[0];
          this.selectedEmpresa.calificacion = this.calificaciones[0];
        });
      }
    }
  }

  /* SUCURSAL ACTIONS */

  addSucursalAction(): void {
    this.selectedSucursal.direccion = this.selectedDireccion;
    this.selectedEmpresa.sucursales.push(this.selectedSucursal);
    this.selectedSucursal = new Sucursal();
    this.selectedDireccion = new Direccion();
  }

  loadSucursalAction(sucursal: Sucursal): void {
    this.selectedSucursal = sucursal;
    this.selectedDireccion = this.selectedSucursal.direccion;
    let longitud = this.selectedSucursal.direccion.longitud;
    let latitud = this.selectedSucursal.direccion.latitud;
    console.log("LON: " +longitud+" LAT: " +latitud);
    if (longitud && latitud) {
      /* El split no es necesario*/
      /*let lon = feature.split(" ")[0];
      /*let lat = feature.split(" ")[1];*/
      //this.addFeature(new Punto(0, Number(longitud), Number(latitud)));
    }
  }

  centralSucursalAction(sucursal: Sucursal): void {
    if (sucursal.casaCentral) {
      sucursal.casaCentral = false;
      toast("Sucursal " + sucursal.nombre + " es sucursal secundaria", 4000);
    } else {
      sucursal.casaCentral = true;
      toast("Sucursal " + sucursal.nombre + " es casa central", 4000);
    }
  }

  removeSucursalAction(sucursal: Sucursal): void {
    this.selectedEmpresa.sucursales = this.selectedEmpresa.sucursales.filter(s => s.nombre !== sucursal.nombre);
  }
/*
  searchAddressAction(): void {
    if (this.addressQuery) {
      this.shared.searchAddress(this.addressQuery).then(addresses => {
        this.addresses = addresses;
        if (addresses.length>0) {
          this.selectedAddress = addresses[0];
          this.addFeature(new Punto(0, Number(this.selectedAddress.lon), Number(this.selectedAddress.lat)));
        }
      });
    }
  }

  reverseAddressAction(lon: string, lat:string): void {
    this.shared.reverseAddress(lon, lat).then(address => {
      this.selectedAddress = address;
      if (address) {
        this.addresses = [];
        this.addresses.push(address);
        this.addFeature(new Punto(0, Number(this.selectedAddress.lon), Number(this.selectedAddress.lat)));
      }
    });
  }

  loadAddressAction(address: AddressResponse): void {
    if (address) {
      this.selectedAddress = address;
      this.selectedDireccion.ubicacion = address.display_name;
      this.selectedDireccion.longitud = Number(address.lon);
      this.selectedDireccion.latitud = Number(address.lat);
      this.addFeature(new Punto(0, Number(address.lon), Number(address.lat)));
    }
  }
*/
}
