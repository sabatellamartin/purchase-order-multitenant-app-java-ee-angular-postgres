import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MaterializeDirective } from "angular2-materialize";
import { MaterializeAction } from 'angular2-materialize';
import { toast } from 'angular2-materialize';


import { AddressResponse } from '../../models/map/address-response';
import { Punto } from '../../models/map/punto';

import { MapService } from '../../../shared/providers/map.service';

import OlMap from 'ol/Map';
import OlView from 'ol/View';
import OlTileLayer from 'ol/layer/Tile';
import OlXYZ from 'ol/source/XYZ';
import { fromLonLat, transform, get as getProjection } from 'ol/proj';
/* Controls and mouse position */
import { defaults as defaultControls } from 'ol/control';
import MousePosition from 'ol/control/MousePosition';
import { createStringXY } from 'ol/coordinate';
/* Styles */
import { Circle as CircleStyle, Fill, Stroke, Style, Icon} from 'ol/style';
import { Vector as VectorLayer } from 'ol/layer';
import { Vector as VectorSource } from 'ol/source';
import Collection from 'ol/Collection';
//import Projection from 'ol/proj/Projection';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';

/* Serializacion */
import GML3 from 'ol/format/GML3';
import WFS from 'ol/format/WFS';


@Component({
  selector: 'map-component',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  /* FOR SHARED PURPOSES
  @Input() currentPage: number = 1; // Pagina actual
  @Input() totalItems: number = 100; // Limite de resutados
  @Input() itemsPerPage: number = 10; // Resultados por pagina

  @Output() pageChange = new EventEmitter();
  @Output() itemsPerPageChange = new EventEmitter();

  searchTime: Date =  new Date(); // Instante de consulta
*/


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


  latitud:any;
  longitud:any;

  constructor(public shared: MapService) {}

  ngOnInit() {
    // Create Vector
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
      this.longitud = coord[0];
      this.latitud = coord[1];
      //this.selectedEmpresa.feature = coord[0] + " " + coord[1];
      this.removeAllFeatures();
      this.addFeature(new Punto(0,coord[0],coord[1]));
      this.reverseAddressAction(coord[0],coord[1]);
      //console.log(this.transactWFS('delete'));
    });
  }

  /* FOR SHARED PURPOSES
  pageChanged(currentPage: number) {
    this.searchTime = new Date();
    this.currentPage = currentPage;
    this.pageChange.emit(currentPage);
  }

  itemsPerPageChanged(items: number) {
    items = items>0?items:1;
    items = items<this.totalItems?items:this.totalItems;
    this.itemsPerPage = items;
    this.itemsPerPageChange.emit(items);
  }*/

  private addFeature(punto: Punto): void {
    this.removeFeatureById(punto.id);
    let coords = transform(
      [punto.longitud, punto.latitud],
      getProjection('EPSG:4326'),
      getProjection('EPSG:3857'));
    if (coords) {
      this.map.getView().setCenter(coords);
      let feature = new Feature(new Point(coords));
      feature.setStyle([
        new Style({
           image: new Icon(({
                   anchor: [0.5, 1],
                   anchorXUnits: 'fraction',
                   anchorYUnits: 'fraction',
                   opacity: 1,
                   src: this.shared.iconPath.concat("origen.png")
         }))
        })
      ]);
      feature.setId(punto.id);
      this.source.addFeature(feature);
    }
  }

  private removeFeatureById(id: any): void {
    let feature = this.source.getFeatures().find(e=> e.getId() == id);
    if (feature) {
        this.source.removeFeature(feature);
    }
  }

  private removeAllFeatures(): void {
    this.source.clear();
  }

  initMap(): void {
    // CapaOpenStreetMap
    this.osm = new OlXYZ({
      url: 'http://tile.osm.org/{z}/{x}/{y}.png'
    });
    this.layer = new OlTileLayer({
      source: this.osm
    });
    // Vista Inicial
    this.view = new OlView({
      projection: "EPSG:3857",
      center: fromLonLat(this.center),
      zoom: this.zoom
    });
    // Mapa
    this.map = new OlMap({
      target: 'map',
      layers: [ this.layer, this.vector ],
      view: this.view,
      controls: defaultControls({
        attribution: false
      }).extend([this.mousePosition()])
    });
  }

  mousePosition(): MousePosition {
    return new MousePosition({
      className: 'custom-mouse-position',
      target: document.getElementById('mouse-position'),
      coordinateFormat: createStringXY(5),
      projection: "EPSG:4326",
      undefinedHTML: '&nbsp;'
    });
  }

  createVector(): void {
    this.source = new VectorSource();
    this.vector = new VectorLayer({
      source: this.source,
      style: new Style({
         image: new Icon(({
                 anchor: [0.5, 1],
                 anchorXUnits: 'fraction',
                 anchorYUnits: 'fraction',
                 opacity: 1,
                 src: this.shared.iconPath.concat("origen.png")
       }))
      })
    });
  }

  /*
  setModify() {
    let modify = new Modify({ source: this.source });
    this.map.addInteraction(modify);
  }

  addInteractions() {
    this.draw = new Draw({
      source: this.source,
      type: this.currentType
    });
    this.map.addInteraction(this.draw);
    this.snap = new Snap({source: this.source});
    this.map.addInteraction(this.snap);
  }

  setType(type:string): void {
    this.currentType = type;
    this.map.removeInteraction(this.draw);
    this.map.removeInteraction(this.snap);
    this.addInteractions();
  }

  transactWFS(operation: string): any {
    let formatWFS = new WFS();
    let formatGML = new GML3({
      featureNS: "distrity",
      featureType: "slave", //locations
      srsName: this.map.getView().getProjection().getCode()
    });
    let feature = this.source.getFeatures();
    let node: any;
    switch (operation) {
        case 'insert':
            node = formatWFS.writeTransaction(feature, null, null, formatGML);
            break;
        case 'update':
            node = formatWFS.writeTransaction(null, feature, null, formatGML);
            break;
        case 'delete':
            node = formatWFS.writeTransaction(null, null, feature, formatGML);
            break;
    }
    // Serializo a XML el Nodo de transaccion
    return new XMLSerializer().serializeToString(node);
  }
  */







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
      console.log(address.display_name);
      this.longitud = Number(address.lon);
      this.latitud = Number(address.lat);
      this.addFeature(new Punto(0, Number(address.lon), Number(address.lat)));
    }
  }

}
