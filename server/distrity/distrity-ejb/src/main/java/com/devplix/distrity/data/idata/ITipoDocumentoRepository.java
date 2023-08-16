package com.app.distrity.data.idata;

import java.util.List;

import com.app.distrity.model.TipoDocumento;

public interface ITipoDocumentoRepository {

	public TipoDocumento getById(Long id);
	
	public TipoDocumento getBySigla(String sigla);

	public List<TipoDocumento> getAll();
	
	public List<TipoDocumento> getAllOrderedBySigla();

	public List<TipoDocumento> search(String term);

	public Boolean add(TipoDocumento tipoDocumento);
	
	public Boolean update(TipoDocumento tipoDocumento);

	public Boolean removeById(Long id);

	public Boolean remove(TipoDocumento tipoDocumento);

}
