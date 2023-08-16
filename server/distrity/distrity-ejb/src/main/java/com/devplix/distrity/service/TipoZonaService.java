package com.app.distrity.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.app.distrity.data.idata.ITipoZonaRepository;
import com.app.distrity.model.TipoZona;
import com.app.distrity.service.iservice.ITipoZonaService;

@Stateless
public class TipoZonaService implements ITipoZonaService {

	@Inject
	private ITipoZonaRepository tipoZonaRepository;

	@Override
	public TipoZona getById(Long id) {
		return tipoZonaRepository.getById(id);
	}

	@Override
	public TipoZona getByCodigo(String codigo) {
		return tipoZonaRepository.getByCodigo(codigo);
	}

	@Override
	public List<TipoZona> getAll() {
		return tipoZonaRepository.getAll();
	}
	
	@Override
	public List<TipoZona> search(String term) {
		List<TipoZona> tipoZonas = new ArrayList<TipoZona>();
		if (term == null || term.compareToIgnoreCase("void")==0) {
			tipoZonas = this.getAll();
		} else {
			tipoZonas = tipoZonaRepository.search(term);
		} 
		return tipoZonas;
	}

	@Override
	public Boolean add(TipoZona tipoZona) {
		return tipoZonaRepository.add(tipoZona);
	}

	@Override
	public Boolean update(TipoZona tipoZona) {
		return tipoZonaRepository.update(tipoZona);
	}

	@Override
	public Boolean removeById(Long id) {
		return tipoZonaRepository.removeById(id);
	}

	@Override
	public Boolean remove(TipoZona tipoZona) {
		return tipoZonaRepository.remove(tipoZona);
	}

}
