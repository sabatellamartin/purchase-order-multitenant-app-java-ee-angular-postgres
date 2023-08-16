package com.app.distrity.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.app.distrity.data.idata.IImpuestoRepository;
import com.app.distrity.model.Impuesto;
import com.app.distrity.service.iservice.IImpuestoService;

@Stateless
public class ImpuestoService implements IImpuestoService {

	@Inject
	private IImpuestoRepository impuestoRepository;

	@Override
	public Impuesto getById(Long id) {
		return impuestoRepository.getById(id);
	}

	@Override
	public Impuesto getByNombre(String nombre) {
		return impuestoRepository.getByNombre(nombre);
	}

	@Override
	public List<Impuesto> getAll() {
		return impuestoRepository.getAll();
	}
	
	@Override
	public List<Impuesto> search(String term) {
		List<Impuesto> impuestos = new ArrayList<Impuesto>();
		if (term == null || term.compareToIgnoreCase("void")==0) {
			impuestos = this.getAll();
		} else {
			impuestos = impuestoRepository.search(term);
		} 
		return impuestos;
	}

	@Override
	public Boolean add(Impuesto impuesto) {
		return impuestoRepository.add(impuesto);
	}

	@Override
	public Boolean update(Impuesto impuesto) {
		return impuestoRepository.update(impuesto);
	}

	@Override
	public Boolean removeById(Long id) {
		return impuestoRepository.removeById(id);
	}

	@Override
	public Boolean remove(Impuesto impuesto) {
		return impuestoRepository.remove(impuesto);
	}

}
