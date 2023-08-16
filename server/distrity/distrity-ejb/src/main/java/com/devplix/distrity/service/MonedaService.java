package com.app.distrity.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.app.distrity.data.idata.IMonedaRepository;
import com.app.distrity.model.Moneda;
import com.app.distrity.service.iservice.IMonedaService;

@Stateless
public class MonedaService implements IMonedaService {

	@Inject
	private IMonedaRepository monedaRepository;

	@Override
	public Moneda getById(Long id) {
		return monedaRepository.getById(id);
	}

	@Override
	public Moneda getByCodigo(String codigo) {
		return monedaRepository.getByCodigo(codigo);
	}

	@Override
	public List<Moneda> getAll() {
		return monedaRepository.getAll();
	}
	
	@Override
	public List<Moneda> search(String term) {
		List<Moneda> monedas = new ArrayList<Moneda>();
		if (term == null || term.compareToIgnoreCase("void")==0) {
			monedas = this.getAll();
		} else {
			monedas = monedaRepository.search(term);
		} 
		return monedas;
	}

	@Override
	public Boolean add(Moneda moneda) {
		return monedaRepository.add(moneda);
	}

	@Override
	public Boolean update(Moneda moneda) {
		return monedaRepository.update(moneda);
	}

	@Override
	public Boolean removeById(Long id) {
		return monedaRepository.removeById(id);
	}

	@Override
	public Boolean remove(Moneda moneda) {
		return monedaRepository.remove(moneda);
	}

}
