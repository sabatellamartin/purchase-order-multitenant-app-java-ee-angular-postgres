package com.app.distrity.service.iservice;

import java.util.List;

import com.app.distrity.model.Unidad;

public interface IUnidadService {
	
	public Unidad getById(Long id);
	
	public List<Unidad> getAll();

	public List<Unidad> search(String term);

	public Boolean add(Unidad unidad);
	
	public Boolean update(Unidad unidad);

	public Boolean removeById(Long id);

	public Boolean remove(Unidad unidad);

	public Unidad getByCodigo(String codigo);
	
}
