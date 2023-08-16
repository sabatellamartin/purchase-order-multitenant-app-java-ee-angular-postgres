package com.app.distrity.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.app.distrity.data.idata.ICategoriaRepository;
import com.app.distrity.model.Categoria;
import com.app.distrity.service.iservice.ICategoriaService;

@Stateless
public class CategoriaService implements ICategoriaService {

	@Inject
	private ICategoriaRepository categoriaRepository;

	@Override
	public Categoria getById(Long id) {
		return categoriaRepository.getById(id);
	}

	@Override
	public Categoria getByNombre(String nombre) {
		return categoriaRepository.getByNombre(nombre);
	}

	@Override
	public List<Categoria> getAll() {
		return categoriaRepository.getAll();
	}
	
	@Override
	public List<Categoria> search(String term) {
		List<Categoria> categorias = new ArrayList<Categoria>();
		if (term == null || term.compareToIgnoreCase("void")==0) {
			categorias = this.getAll();
		} else {
			categorias = categoriaRepository.search(term);
		} 
		return categorias;
	}

	@Override
	public Boolean add(Categoria categoria) {
		return categoriaRepository.add(categoria);
	}

	@Override
	public Boolean update(Categoria categoria) {
		return categoriaRepository.update(categoria);
	}

	@Override
	public Boolean removeById(Long id) {
		return categoriaRepository.removeById(id);
	}

	@Override
	public Boolean remove(Categoria categoria) {
		return categoriaRepository.remove(categoria);
	}

}
