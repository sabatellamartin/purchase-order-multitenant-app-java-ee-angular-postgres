package com.app.distrity.data.idata;

import java.util.List;

import com.app.distrity.model.TipoEmpresa;

public interface ITipoEmpresaRepository {

	public TipoEmpresa getById(Long id);
	
	public TipoEmpresa getBySigla(String sigla);

	public List<TipoEmpresa> getAll();
	
	public List<TipoEmpresa> getAllOrderedBySigla();

	public List<TipoEmpresa> search(String term);

	public Boolean add(TipoEmpresa tipoEmpresa);
	
	public Boolean update(TipoEmpresa tipoEmpresa);

	public Boolean removeById(Long id);

	public Boolean remove(TipoEmpresa tipoEmpresa);

}
