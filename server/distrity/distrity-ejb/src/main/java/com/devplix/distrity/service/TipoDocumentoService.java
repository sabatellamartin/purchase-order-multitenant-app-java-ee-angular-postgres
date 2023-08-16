package com.app.distrity.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.app.distrity.data.idata.ITipoDocumentoRepository;
import com.app.distrity.model.TipoDocumento;
import com.app.distrity.service.iservice.ITipoDocumentoService;

@Stateless
public class TipoDocumentoService implements ITipoDocumentoService {

	@Inject
	private ITipoDocumentoRepository tipoDocumentoRepository;

	@Override
	public TipoDocumento getById(Long id) {
		return tipoDocumentoRepository.getById(id);
	}

	@Override
	public TipoDocumento getBySigla(String sigla) {
		return tipoDocumentoRepository.getBySigla(sigla);
	}

	@Override
	public List<TipoDocumento> getAll() {
		return tipoDocumentoRepository.getAll();
	}
	
	@Override
	public List<TipoDocumento> search(String term) {
		List<TipoDocumento> tipoDocumentos = new ArrayList<TipoDocumento>();
		if (term == null || term.compareToIgnoreCase("void")==0) {
			tipoDocumentos = this.getAll();
		} else {
			tipoDocumentos = tipoDocumentoRepository.search(term);
		} 
		return tipoDocumentos;
	}

	@Override
	public Boolean add(TipoDocumento tipoDocumento) {
		return tipoDocumentoRepository.add(tipoDocumento);
	}

	@Override
	public Boolean update(TipoDocumento tipoDocumento) {
		return tipoDocumentoRepository.update(tipoDocumento);
	}

	@Override
	public Boolean removeById(Long id) {
		return tipoDocumentoRepository.removeById(id);
	}

	@Override
	public Boolean remove(TipoDocumento tipoDocumento) {
		return tipoDocumentoRepository.remove(tipoDocumento);
	}

}
