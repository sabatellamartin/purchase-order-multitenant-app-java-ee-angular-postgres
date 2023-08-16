package com.app.distrity.service.iservice;

import java.util.List;

import com.app.distrity.model.Moneda;

public interface IMonedaService {
	
	public Moneda getById(Long id);
	
	public List<Moneda> getAll();

	public List<Moneda> search(String term);

	public Boolean add(Moneda moneda);
	
	public Boolean update(Moneda moneda);

	public Boolean removeById(Long id);

	public Boolean remove(Moneda moneda);

	public Moneda getByCodigo(String codigo);
	
}
