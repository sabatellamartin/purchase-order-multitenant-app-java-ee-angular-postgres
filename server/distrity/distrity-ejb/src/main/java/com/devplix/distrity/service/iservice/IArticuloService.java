package com.app.distrity.service.iservice;

import java.io.InputStream;
import java.util.List;

import com.app.distrity.model.Articulo;
import com.app.distrity.util.filter.ArticuloFilter;
import com.app.distrity.util.paginator.PaginatorResponse;

public interface IArticuloService {
	
	public Articulo getById(Long id);
	
	public List<Articulo> getAll();

	public Boolean add(Articulo articulo);
	
	public Boolean update(Articulo articulo);

	public Boolean removeById(Long id);

	public Boolean remove(Articulo articulo);

	public Articulo getByCodigo(String codigo);

	public Boolean existByCodigo(String codigo);

	public List<Articulo> loadFullData(InputStream incomingData);

	public List<Articulo> search(String term);

	public PaginatorResponse<Articulo> searchFilter(ArticuloFilter articuloFilter);
	
}
