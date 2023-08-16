package com.app.distrity.service.iservice;

import java.util.List;

import com.app.distrity.model.Empresa;

public interface IEmpresaService {
	
	public Empresa getById(Long id);
	
	public List<Empresa> getAll();

	public List<Empresa> search(String term);

	public Boolean add(Empresa empresa, String tenant);
	
	public Boolean update(Empresa empresa, String tenant);

	public Boolean removeById(Long id, String tenant);

	public Boolean remove(Empresa empresa, String tenant);

	public Empresa getByRut(String rut);

	public List<Empresa> searchByTipo(String term, String tipo);

	public Empresa getDistribuidor();

}
