package com.app.distrity.data.idata;

import java.util.List;

import com.app.distrity.model.Calificacion;

public interface ICalificacionRepository {

	public Calificacion getById(Long id);

	public List<Calificacion> getAll();
	
	public List<Calificacion> getAllOrderedByCodigo();

	public List<Calificacion> search(String term);

	public Boolean add(Calificacion calificacion);
	
	public Boolean update(Calificacion calificacion);

	public Boolean removeById(Long id);

	public Boolean remove(Calificacion calificacion);

	public Calificacion getByCodigo(String codigo);

}
