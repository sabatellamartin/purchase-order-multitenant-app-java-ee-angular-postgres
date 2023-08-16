package com.app.distrity.data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.app.distrity.data.idata.IPersonaRepository;
import com.app.distrity.model.Consumidor;
import com.app.distrity.model.Empleado;
import com.app.distrity.model.Persona;
import com.app.distrity.model.Referente;
import com.app.distrity.util.Constants;

@Stateless
public class PersonaRepository implements IPersonaRepository {

	@PersistenceContext(unitName = Constants.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	public PersonaRepository() {}

	@Override
	public Persona getById(Long id) {
		return em.find(Persona.class, id);
	}

	@Override
	public Persona getByDocumento(String numero, Long tipoDocumentoId) {
		String sql = "FROM " + Persona.class.getName() + " p "
				+ "WHERE p.numeroDocumento = :numero "
				+ "AND p.tipoDocumento.id = :tipoDocumentoId ";
		Persona result = (Persona) em.createQuery(sql)
				.setParameter("numero", numero)
				.setParameter("tipoDocumentoId", tipoDocumentoId).getSingleResult();	
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Persona> getAll() {
		return em.createQuery("FROM " + Persona.class.getName()).getResultList();
	}
	
	@Override
	public List<Persona> getAllOrderedByNombre() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Persona> criteria = cb.createQuery(Persona.class);
		Root<Persona> persona = criteria.from(Persona.class);
		criteria.select(persona).orderBy(cb.asc(persona.get("primerApellido")));
		return em.createQuery(criteria).getResultList();
	}
	
	@Override
	public List<Persona> search(String term) {
		List<Persona> list = new ArrayList<Persona>();
		String sql = "FROM " + Persona.class.getName() + " p "
				+ "WHERE lower(p.primerNombre) LIKE lower(:term) "
				+ "OR lower(p.segundoNombre) LIKE lower(:term) "
				+ "OR lower(p.primerApellido) LIKE lower(:term) "
				+ "OR lower(p.segundoApellido) LIKE lower(:term) "
				+ "OR lower(p.numeroDocumento) LIKE lower(:term) ";
		List<?> result = em.createQuery(sql).setParameter("term", '%' + term + '%').getResultList();
		if (!result.isEmpty()) {
			for (Object obj : result) {
				list.add((Persona) obj);
			}
		}
		return list;
	}

	@Override
	public Boolean add(Persona persona) {
		Boolean result = false;
		try {
			em.persist(persona);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean update(Persona persona) {
		Boolean result = false;
		try {
			em.merge(persona);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean removeById(Long id) {
		Boolean result = false;
		try {
			Persona persona = this.getById(id);
			result = this.remove(persona);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public Boolean remove(Persona persona) {
		Boolean result = false;
		try {
			persona = em.find(Persona.class, persona.getId());
			em.remove(persona);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Persona> searchByTipo(String term, String tipo) {
		List<Persona> list = new ArrayList<Persona>();
		String sql;
		String className = null;
		List<?> result;
		if(tipo.compareToIgnoreCase(Constants.CONSUMIDOR)==0) {
			className = Consumidor.class.getName();
		} else if(tipo.compareToIgnoreCase(Constants.REFERENTE)==0) {
			className = Referente.class.getName();
		} else if(tipo.compareToIgnoreCase(Constants.EMPLEADO)==0) {
			className = Empleado.class.getName();
		}
		sql = "FROM " + className + " u ";
		if (term == null || term.compareToIgnoreCase("void") == 0) {
			result = em.createQuery(sql).getResultList();
		} else {
			sql += "WHERE lower(u.nombreCompleto) LIKE lower(:term) "
				+ "OR lower(u.primerNombre) LIKE lower(:term) "
				+ "OR lower(u.segundoNombre) LIKE lower(:term) "
				+ "OR lower(u.primerApellido) LIKE lower(:term) "
				+ "OR lower(u.segundoApellido) LIKE lower(:term) "
				+ "OR lower(u.numeroDocumento) LIKE lower(:term) "
				+ "OR lower(u.direccionEmail) LIKE lower(:term) ";
			result = em.createQuery(sql).setParameter("term", '%' + term + '%').getResultList();
		}
		if (!result.isEmpty()) {
			for (Object obj : result) {
				list.add((Persona) obj);
			}
		}
		return list;
	}
}
