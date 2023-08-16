package com.app.distrity.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.app.distrity.data.idata.ICalificacionRepository;
import com.app.distrity.model.Calificacion;
import com.app.distrity.service.iservice.ICalificacionService;

@Stateless
public class CalificacionService implements ICalificacionService {

	@Inject
	private ICalificacionRepository calificacionRepository;

	@Override
	public Calificacion getById(Long id) {
		return calificacionRepository.getById(id);
	}

	@Override
	public Calificacion getByCodigo(String codigo) {
		return calificacionRepository.getByCodigo(codigo);
	}

	@Override
	public List<Calificacion> getAll() {
		return calificacionRepository.getAll();
	}
	
	@Override
	public List<Calificacion> search(String term) {
		List<Calificacion> calificacions = new ArrayList<Calificacion>();
		if (term == null || term.compareToIgnoreCase("void")==0) {
			calificacions = this.getAll();
		} else {
			calificacions = calificacionRepository.search(term);
		} 
		return calificacions;
	}

	@Override
	public Boolean add(Calificacion calificacion) {
		return calificacionRepository.add(calificacion);
	}

	@Override
	public Boolean update(Calificacion calificacion) {
		return calificacionRepository.update(calificacion);
	}

	@Override
	public Boolean removeById(Long id) {
		return calificacionRepository.removeById(id);
	}

	@Override
	public Boolean remove(Calificacion calificacion) {
		return calificacionRepository.remove(calificacion);
	}

}
