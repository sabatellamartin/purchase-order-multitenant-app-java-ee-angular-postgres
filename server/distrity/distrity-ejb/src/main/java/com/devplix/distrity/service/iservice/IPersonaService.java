package com.app.distrity.service.iservice;

import java.util.List;

import com.app.distrity.model.Persona;

public interface IPersonaService {
	
	public Persona getById(Long id);
	
	public List<Persona> getAll();

	public List<Persona> search(String term);

	public Boolean add(Persona persona);
	
	public Boolean update(Persona persona);

	public Boolean removeById(Long id);

	public Boolean remove(Persona persona);

	public Persona getByDocumento(String numero, Long tipoDocumentoId);

	public List<Persona> searchByTipo(String term, String tipo);
	
}
