package com.app.distrity.data.idata;

import java.util.List;

import com.app.distrity.model.Moneda;

public interface IMonedaRepository {

	public Moneda getById(Long id);

	public List<Moneda> getAll();
	
	public List<Moneda> getAllOrderedByCodigo();

	public List<Moneda> search(String term);

	public Boolean add(Moneda moneda);
	
	public Boolean update(Moneda moneda);

	public Boolean removeById(Long id);

	public Boolean remove(Moneda moneda);

	public Moneda getByCodigo(String codigo);

}
