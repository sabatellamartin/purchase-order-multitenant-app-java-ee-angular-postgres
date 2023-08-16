package com.app.distrity.data.idata;

import java.util.List;

import com.app.distrity.model.Empresa;

public interface IEmpresaRepository {

	public Empresa getById(Long id);

	public List<Empresa> getAll();
	
	public List<Empresa> getAllOrderedByNombre();

	public List<Empresa> search(String term);

	public Boolean add(Empresa empresa);
	
	public Boolean update(Empresa empresa);

	public Boolean removeById(Long id);

	public Boolean remove(Empresa empresa);

	public Empresa getByRut(String rut);

	public List<Empresa> searchByTipo(String term, String tipo);

}
