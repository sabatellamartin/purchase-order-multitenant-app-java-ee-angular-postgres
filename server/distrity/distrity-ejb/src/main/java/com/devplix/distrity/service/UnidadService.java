package com.app.distrity.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.app.distrity.data.idata.IUnidadRepository;
import com.app.distrity.model.Unidad;
import com.app.distrity.service.iservice.IUnidadService;

@Stateless
public class UnidadService implements IUnidadService {

	@Inject
	private IUnidadRepository unidadRepository;

	@Override
	public Unidad getById(Long id) {
		return unidadRepository.getById(id);
	}

	@Override
	public Unidad getByCodigo(String codigo) {
		return unidadRepository.getByCodigo(codigo);
	}

	@Override
	public List<Unidad> getAll() {
		return unidadRepository.getAll();
	}
	
	@Override
	public List<Unidad> search(String term) {
		List<Unidad> unidads = new ArrayList<Unidad>();
		if (term == null || term.compareToIgnoreCase("void")==0) {
			unidads = this.getAll();
		} else {
			unidads = unidadRepository.search(term);
		} 
		return unidads;
	}

	@Override
	public Boolean add(Unidad unidad) {
		return unidadRepository.add(unidad);
	}

	@Override
	public Boolean update(Unidad unidad) {
		return unidadRepository.update(unidad);
	}

	@Override
	public Boolean removeById(Long id) {
		return unidadRepository.removeById(id);
	}

	@Override
	public Boolean remove(Unidad unidad) {
		return unidadRepository.remove(unidad);
	}

}
