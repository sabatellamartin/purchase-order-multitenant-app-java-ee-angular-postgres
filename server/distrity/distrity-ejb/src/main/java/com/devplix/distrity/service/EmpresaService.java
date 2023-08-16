package com.app.distrity.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.app.distrity.data.idata.IEmpresaRepository;
import com.app.distrity.model.Direccion;
import com.app.distrity.model.Distribuidor;
import com.app.distrity.model.Empresa;
import com.app.distrity.model.Sucursal;
import com.app.distrity.service.iservice.IEmpresaService;
import com.app.distrity.service.iservice.IGisService;
import com.app.distrity.util.Constants;

@Stateless
public class EmpresaService implements IEmpresaService {

	@Inject
	private IEmpresaRepository empresaRepository;
	
	@Inject
	private IGisService gisService;

	@Override
	public Empresa getById(Long id) {
		Empresa empresa = empresaRepository.getById(id);
		List<Sucursal> sucursales = new ArrayList<Sucursal>();
		for (Sucursal sucursal : empresa.getSucursales()) {
			if (sucursal.getDireccion()!=null) {
				Long featureId = sucursal.getDireccion().getFeatureId();
				if (featureId!=null && featureId>0) {
					String wkbString = gisService.getFeatureById(featureId);
					sucursal.getDireccion().setLongitud(gisService.wkbStringToCoord("LON", wkbString));
					sucursal.getDireccion().setLatitud(gisService.wkbStringToCoord("LAT", wkbString));
				}
			}
			sucursales.add(sucursal);
		}
		empresa.setSucursales(sucursales);
		return empresa;
	}

	@Override
	public Empresa getByRut(String rut) {
		return empresaRepository.getByRut(rut);
	}

	@Override
	public List<Empresa> getAll() {
		return empresaRepository.getAll();
	}
	
	@Override
	public List<Empresa> search(String term) {
		List<Empresa> empresas = new ArrayList<Empresa>();
		if (term == null || term.compareToIgnoreCase("void")==0) {
			empresas = this.getAll();
		} else {
			empresas = empresaRepository.search(term);
		} 
		return empresas;
	}

	@Override
	public Boolean add(Empresa empresa, String tenant) {
		empresa.setAlta(new Date());
		List <Sucursal> sucursales = empresa.getSucursales();
		empresa.setSucursales(new ArrayList<Sucursal>());
		for (Sucursal sucursal : sucursales) {
			Double longitud = sucursal.getDireccion().getLongitud();
			Double latitud = sucursal.getDireccion().getLatitud();
			System.out.println("ADD SUCURSAL LON: "+longitud.toString()+" LAT: "+latitud.toString());
			if (longitud!=null && latitud!=null) {
				/* RESOLVER GUARDADO GEOGRAFICO SIN GEOSERVER CON HIBERNATE GEO
				Long featureId = this.gisService.savePoint(longitud, latitud, tenant);
				sucursal.getDireccion().setFeatureId(featureId);*/
			}
			if (!sucursal.getCasaCentral()) {
				sucursal.setCasaCentral(false);
			}
			empresa.addSucursal(sucursal);
		}
		return empresaRepository.add(empresa);
	}

	@Override
	public Boolean update(Empresa empresa, String tenant) {
		List <Sucursal> sucursales = empresa.getSucursales();
		empresa.setSucursales(new ArrayList<Sucursal>());
		for (Sucursal sucursal : sucursales) {
			Direccion direccion = sucursal.getDireccion();
			if (direccion.getLongitud()!=null && direccion.getLatitud()!=null) {
				/* RESOLVER GUARDADO GEOGRAFICO SIN GEOSERVER CON HIBERNATE GEO
				if (direccion.getFeatureId()>0) {
					direccion.setFeatureId(
							this.gisService.updatePoint(
									direccion.getFeatureId(), 
									direccion.getLongitud(), 
									direccion.getLatitud(), 
									tenant));
				} else {
					direccion.setFeatureId(
							this.gisService.savePoint(
									direccion.getLongitud(),
									direccion.getLatitud(), 
									tenant));
				}*/
			}
			sucursal.setDireccion(direccion);
			if (!sucursal.getCasaCentral()) {
				sucursal.setCasaCentral(false);
			}
			empresa.addSucursal(sucursal);
		}
		return empresaRepository.update(empresa);
	}

	@Override
	public Boolean removeById(Long id, String tenant) {
		return this.remove(this.getById(id), tenant);
	}

	@Override
	public Boolean remove(Empresa empresa, String tenant) {
		List <Sucursal> sucursales = empresa.getSucursales();
		for (Sucursal sucursal : sucursales) {
			Direccion direccion = sucursal.getDireccion();
			if (direccion.getFeatureId()>0) {
				this.gisService.deletePoint(direccion.getFeatureId(), tenant);
			}
		}
		return empresaRepository.remove(empresa);
	}
	
	@Override
	public List<Empresa> searchByTipo(String term, String tipo) {
		List<Empresa> empresas = new ArrayList<Empresa>();
		empresas = empresaRepository.searchByTipo(term,tipo);
		return empresas;
	}

	
	@Override
	public Empresa getDistribuidor() {
		Empresa distribuidor = new Distribuidor();
		List<Empresa> empresas = new ArrayList<Empresa>();
		empresas = empresaRepository.searchByTipo("", Constants.DISTRIBUIDOR);
		if (!empresas.isEmpty()) {
			distribuidor = (Distribuidor)empresas.get(0);
		}
		return distribuidor;
	}
	
}
