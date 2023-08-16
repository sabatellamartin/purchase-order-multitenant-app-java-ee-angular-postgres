package com.app.distrity.data.idata;

import java.util.List;

import com.app.distrity.model.Impuesto;

public interface IImpuestoRepository {

	public Impuesto getById(Long id);

	public List<Impuesto> getAll();
	
	public List<Impuesto> getAllOrderedByCodigo();

	public List<Impuesto> search(String term);

	public Boolean add(Impuesto impuesto);
	
	public Boolean update(Impuesto impuesto);

	public Boolean removeById(Long id);

	public Boolean remove(Impuesto impuesto);

	public Impuesto getByNombre(String nombre);

}
