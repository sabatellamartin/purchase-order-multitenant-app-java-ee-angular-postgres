package com.app.distrity.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.app.distrity.data.idata.ITipoDireccionRepository;
import com.app.distrity.model.TipoDireccion;
import com.app.distrity.service.iservice.ITipoDireccionService;

@Stateless
public class TipoDireccionService implements ITipoDireccionService {

	@Inject
	private ITipoDireccionRepository tipoDireccionRepository;

	@Override
	public TipoDireccion getById(Long id) {
		return tipoDireccionRepository.getById(id);
	}

	@Override
	public TipoDireccion getByCodigo(String codigo) {
		return tipoDireccionRepository.getByCodigo(codigo);
	}

	@Override
	public List<TipoDireccion> getAll() {
		return tipoDireccionRepository.getAll();
	}
	
	@Override
	public List<TipoDireccion> search(String term) {
		List<TipoDireccion> tipoDirecciones = new ArrayList<TipoDireccion>();
		if (term == null || term.compareToIgnoreCase("void")==0) {
			tipoDirecciones = this.getAll();
		} else {
			tipoDirecciones = tipoDireccionRepository.search(term);
		} 
		return tipoDirecciones;
	}

	@Override
	public Boolean add(TipoDireccion tipoDireccion) {
		return tipoDireccionRepository.add(tipoDireccion);
	}

	@Override
	public Boolean update(TipoDireccion tipoDireccion) {
		return tipoDireccionRepository.update(tipoDireccion);
	}

	@Override
	public Boolean removeById(Long id) {
		return tipoDireccionRepository.removeById(id);
	}

	@Override
	public Boolean remove(TipoDireccion tipoDireccion) {
		return tipoDireccionRepository.remove(tipoDireccion);
	}

}
