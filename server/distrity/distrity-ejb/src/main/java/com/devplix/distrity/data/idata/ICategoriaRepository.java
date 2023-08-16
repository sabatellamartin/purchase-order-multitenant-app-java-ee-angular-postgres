package com.app.distrity.data.idata;

import java.util.List;

import com.app.distrity.model.Categoria;

public interface ICategoriaRepository {

	public Categoria getById(Long id);

	public List<Categoria> getAll();
	
	public List<Categoria> getAllOrderedByCodigo();

	public List<Categoria> search(String term);

	public Boolean add(Categoria categoria);
	
	public Boolean update(Categoria categoria);

	public Boolean removeById(Long id);

	public Boolean remove(Categoria categoria);

	public Categoria getByNombre(String nombre);

}
