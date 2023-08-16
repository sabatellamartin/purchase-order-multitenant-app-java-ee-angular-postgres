package com.app.distrity.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.app.distrity.data.idata.IPersonaRepository;
import com.app.distrity.model.Persona;
import com.app.distrity.service.iservice.IPersonaService;

@Stateless
public class PersonaService implements IPersonaService {

	@Inject
	private IPersonaRepository personaRepository;

	@Override
	public Persona getById(Long id) {
		return personaRepository.getById(id);
	}

	@Override
	public Persona getByDocumento(String numero, Long tipoDocumentoId) {
		return personaRepository.getByDocumento(numero, tipoDocumentoId);
	}

	@Override
	public List<Persona> getAll() {
		return personaRepository.getAll();
	}
	
	@Override
	public List<Persona> search(String term) {
		List<Persona> personas = new ArrayList<Persona>();
		if (term == null || term.compareToIgnoreCase("void")==0) {
			personas = this.getAll();
		} else {
			personas = personaRepository.search(term);
		} 
		return personas;
	}

	@Override
	public Boolean add(Persona persona) {
		return personaRepository.add(persona);
	}

	@Override
	public Boolean update(Persona persona) {
		return personaRepository.update(persona);
	}

	@Override
	public Boolean removeById(Long id) {
		return personaRepository.removeById(id);
	}

	@Override
	public Boolean remove(Persona persona) {
		return personaRepository.remove(persona);
	}
	
	@Override
	public List<Persona> searchByTipo(String term, String tipo) {
		List<Persona> personas = new ArrayList<Persona>();
		personas = personaRepository.searchByTipo(term,tipo);
		return personas;
	}

}
