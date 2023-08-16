package com.app.distrity.service.iservice;

import java.util.List;

import com.app.distrity.model.TipoDireccion;

public interface ITipoDireccionService {
	
	public TipoDireccion getById(Long id);
	
	public List<TipoDireccion> getAll();

	public List<TipoDireccion> search(String term);

	public Boolean add(TipoDireccion tipoDireccion);
	
	public Boolean update(TipoDireccion tipoDireccion);

	public Boolean removeById(Long id);

	public Boolean remove(TipoDireccion tipoDireccion);

	public TipoDireccion getByCodigo(String codigo);
	
}
