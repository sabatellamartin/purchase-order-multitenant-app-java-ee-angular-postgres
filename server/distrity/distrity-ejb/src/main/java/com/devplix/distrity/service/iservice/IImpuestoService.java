package com.app.distrity.service.iservice;

import java.util.List;

import com.app.distrity.model.Impuesto;

public interface IImpuestoService {
	
	public Impuesto getById(Long id);
	
	public List<Impuesto> getAll();

	public List<Impuesto> search(String term);

	public Boolean add(Impuesto impuesto);
	
	public Boolean update(Impuesto impuesto);

	public Boolean removeById(Long id);

	public Boolean remove(Impuesto impuesto);

	public Impuesto getByNombre(String nombre);
	
}
