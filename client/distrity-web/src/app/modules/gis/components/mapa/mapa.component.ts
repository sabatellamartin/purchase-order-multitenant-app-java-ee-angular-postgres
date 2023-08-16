import { Component, OnInit } from '@angular/core';

import { GeoService } from '../../providers/geo.service';

import OlMap from 'ol/Map';
import OlView from 'ol/View';
import OlTileLayer from 'ol/layer/Tile';
import OlXYZ from 'ol/source/XYZ';

import { fromLonLat } from 'ol/proj';

/* Controls and mouse position */
import { defaults as defaultControls } from 'ol/control';
import MousePosition from 'ol/control/MousePosition';
import { createStringXY } from 'ol/coordinate';

/* Styles */
import { Circle as CircleStyle, Fill, Stroke, Style} from 'ol/style';

import { Vector as VectorLayer } from 'ol/layer';
import { Vector as VectorSource } from 'ol/source';

import Collection from 'ol/Collection';

/* Interacciones */
import { Draw, Modify, Snap } from 'ol/interaction';

/* Serializacion */
import GML3 from 'ol/format/GML3';
import WFS from 'ol/format/WFS';
import Projection from 'ol/proj/Projection';

@Component({
  selector: 'app-mapa',
  templateUrl: './mapa.html',
  styleUrls: ['./mapa.css']
})
export class MapaComponent implements OnInit {

  // Para generar el mapa
  map: OlMap;
  osm: OlXYZ;
  layer: OlTileLayer;
  view: OlView;

  // Para vectores
  source: VectorSource;
  vector: VectorLayer;

  projection: string = "EPSG:4326"; // "EPSG:32721" //"EPSG:900913"
  latitud: number = -34.9056;
  longitud: number = -56.1956;
  zoom: number =  14;
  //format: string = 'image/png';

  // Draw and Modify atributes
  //features: Collection = new Collection();
  draw: Draw; // global so we can remove it later
  snap: Snap; // global so we can remove it later
  currentType: string =  'Point';

  constructor(private geoService: GeoService) { }

  ngOnInit(): void {
    this.getLocationById(3);

    // Create Vector
    this.createVector();
    // Init Map
    this.initMap();
    // Set Modify
    this.setModify();
    // Add Interaction (default Point)
    this.addInteractions();
  }

  initMap(): void {
    /* CapaOpenStreetMap */
    this.osm = new OlXYZ({
      url: 'http://tile.osm.org/{z}/{x}/{y}.png'
    });
    this.layer = new OlTileLayer({
      source: this.osm
    });
    /* Vista Inicial */
    this.view = new OlView({
      center: fromLonLat([this.longitud, this.latitud]),
      zoom: this.zoom
    });
    /* Mapa */
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
      target: document.getElementById('location'),
      coordinateFormat: createStringXY(5),
      undefinedHTML: '&nbsp;'
    });
  }

  createVector(): void {
    this.source = new VectorSource();
    this.vector = new VectorLayer({
      source: this.source,
      style: new Style({
        fill: new Fill({
          color: 'rgba(255, 255, 255, 0.2)'
        }),
        stroke: new Stroke({
          color: '#ffcc33',
          width: 2
        }),
        image: new CircleStyle({
          radius: 7,
          fill: new Fill({
            color: '#ffcc33'
          })
        })
      })
    });
  }

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

  getLocationById(id:number) {
    this.geoService.getLocationById(id).then(response => {
      console.log(response);
      /* Pasar los llamados a geoserver al lado servidor
         Establecer autorizacion a geoServer con usuario y pass
         Ocultar geoserver, no sale para afuera intranet
         Agregar un campo para distinguir el tenant en location y Zones
         Establecer consultas para las features: guardar, borrar, getById, getAll
         Separar en diferentes capas(tablas) la informacion de clientes empresas, etc */

    });
  }

  save() {
    let data = this.transactWFS('insert');
    console.log(data);
    this.geoService.saveGeoserver(data).then(response => {
        console.log(response);
    });
  }

  transactWFS(operation: string): any {
    let formatWFS = new WFS();
    let formatGML = new GML3({
      featureNS: "distrity",
      featureType: "locations",
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


  /*
  drawFeatures(): void {
    let featureOverlay = new VectorSource({
      source: new VectorLayer({features: this.features}),
      style: new Style({
        fill: new Fill({
          color: 'rgba(255, 255, 255, 0.2)'
        }),
        stroke: new Stroke({
          color: '#ffcc33',
          width: 2
        }),
        image: new CircleStyle({
          radius: 7,
          fill: new Fill({
            color: '#ffcc33'
          })
        })
      })
    });
    featureOverlay.setMap(this.map);

    let modify = new Modify({
      features: this.features,
      // the SHIFT key must be pressed to delete vertices, so
      // that new vertices can be drawn at the same position
      // of existing vertices
      deleteCondition: function(event) {
        return ol.events.condition.shiftKeyOnly(event) &&
            ol.events.condition.singleClick(event);

    });
    this.map.addInteraction(modify);

    this.addInteraction(this.currentType);
  }

  // Add new Draw Feature to Map, active onchange types combobox
  addInteraction(typeValue: string) {
    this.currentType = typeValue;
    // Remove Draw
    this.map.removeInteraction(this.draw);
    // Reload Features
    this.draw = new Draw({
      features: this.features,
      type: typeValue // @type {ol.geom.GeometryType}
    });
    // Add Draw Again
    this.map.addInteraction(this.draw);
  }
  */

/*
  capaTiled(): any {
    return new ol.layer.Tile({
      visible: false,
      source: new ol.source.TileWMS({
        url: 'http://localhost:8080/geoserver/GeoService/wms',
        params: {'FORMAT': this.format,
                 'VERSION': '1.1.1',
                 tiled: true,
              STYLES: '',
              LAYERS: 'GeoService:01departamento',
           tilesOrigin: 364123.9375 + "," + 6125208
        }
      })
    });
  }

  capaUntiled(): any {
    return new ol.layer.Image({
       source: new ol.source.ImageWMS({
         ratio: 1,
         url: 'http://localhost:8080/geoserver/GeoService/wms',
         params: {'FORMAT': this.format,
                  'VERSION': '1.1.1',
               STYLES: '',
               LAYERS: 'GeoService:01departamento',
         }
       })
     });
  }
*/

}
