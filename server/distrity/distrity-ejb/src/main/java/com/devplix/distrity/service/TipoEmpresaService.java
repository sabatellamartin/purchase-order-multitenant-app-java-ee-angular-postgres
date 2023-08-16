package com.app.distrity.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.app.distrity.data.idata.ITipoEmpresaRepository;
import com.app.distrity.model.TipoEmpresa;
import com.app.distrity.service.iservice.ITipoEmpresaService;

@Stateless
public class TipoEmpresaService implements ITipoEmpresaService {

	@Inject
	private ITipoEmpresaRepository tipoEmpresaRepository;

	@Override
	public TipoEmpresa getById(Long id) {
		return tipoEmpresaRepository.getById(id);
	}

	@Override
	public TipoEmpresa getBySigla(String sigla) {
		return tipoEmpresaRepository.getBySigla(sigla);
	}

	@Override
	public List<TipoEmpresa> getAll() {
		return tipoEmpresaRepository.getAll();
	}
	
	@Override
	public List<TipoEmpresa> search(String term) {
		List<TipoEmpresa> tipoEmpresas = new ArrayList<TipoEmpresa>();
		if (term == null || term.compareToIgnoreCase("void")==0) {
			tipoEmpresas = this.getAll();
		} else {
			tipoEmpresas = tipoEmpresaRepository.search(term);
		} 
		return tipoEmpresas;
	}

	@Override
	public Boolean add(TipoEmpresa tipoEmpresa) {
		return tipoEmpresaRepository.add(tipoEmpresa);
	}

	@Override
	public Boolean update(TipoEmpresa tipoEmpresa) {
		return tipoEmpresaRepository.update(tipoEmpresa);
	}

	@Override
	public Boolean removeById(Long id) {
		return tipoEmpresaRepository.removeById(id);
	}

	@Override
	public Boolean remove(TipoEmpresa tipoEmpresa) {
		return tipoEmpresaRepository.remove(tipoEmpresa);
	}

}
