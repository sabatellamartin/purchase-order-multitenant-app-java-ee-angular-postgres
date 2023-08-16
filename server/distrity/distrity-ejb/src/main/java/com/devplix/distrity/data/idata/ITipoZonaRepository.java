package com.app.distrity.data.idata;

import java.util.List;

import com.app.distrity.model.TipoZona;

public interface ITipoZonaRepository {

	public TipoZona getById(Long id);
	
	public List<TipoZona> getAll();
	
	public List<TipoZona> getAllOrderedByCodigo();

	public List<TipoZona> search(String term);

	public Boolean add(TipoZona tipoZona);
	
	public Boolean update(TipoZona tipoZona);

	public Boolean removeById(Long id);

	public Boolean remove(TipoZona tipoZona);

	public TipoZona getByCodigo(String codigo);

}
