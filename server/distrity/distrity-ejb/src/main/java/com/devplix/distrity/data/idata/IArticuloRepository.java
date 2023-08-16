package com.app.distrity.data.idata;

import java.util.List;

import com.app.distrity.model.Articulo;
import com.app.distrity.util.filter.ArticuloFilter;
import com.app.distrity.util.paginator.PaginatorResponse;

public interface IArticuloRepository {

	public Articulo getById(Long id);

	public List<Articulo> getAll();
	
	public List<Articulo> getAllOrderedByCodigo();

	public List<Articulo> search(String term);

	public Boolean add(Articulo articulo);
	
	public Boolean update(Articulo articulo);

	public Boolean removeById(Long id);

	public Boolean remove(Articulo articulo);

	public Articulo getByCodigo(String codigo);

	public PaginatorResponse<Articulo> searchFilter(ArticuloFilter articuloFilter);

}
