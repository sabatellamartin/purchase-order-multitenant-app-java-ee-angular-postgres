package com.app.distrity.data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.app.distrity.data.idata.IEmpresaRepository;
import com.app.distrity.model.Cliente;
import com.app.distrity.model.Distribuidor;
import com.app.distrity.model.Empresa;
import com.app.distrity.model.Proveedor;
import com.app.distrity.util.Constants;

@Stateless
public class EmpresaRepository implements IEmpresaRepository {

	@PersistenceContext(unitName = Constants.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	public EmpresaRepository() {}

	@Override
	public Empresa getById(Long id) {
		return em.find(Empresa.class, id);
	}

	@Override
	public Empresa getByRut(String rut) {
		String sql = "FROM " + Empresa.class.getName() + " e "
				+ "WHERE e.rut = :rut";
		Empresa result = (Empresa) em.createQuery(sql)
				.setParameter("rut", rut).getSingleResult();	
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Empresa> getAll() {
		return em.createQuery("FROM " + Empresa.class.getName()).getResultList();
	}
	
	@Override
	public List<Empresa> getAllOrderedByNombre() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Empresa> criteria = cb.createQuery(Empresa.class);
		Root<Empresa> empresa = criteria.from(Empresa.class);
		criteria.select(empresa).orderBy(cb.asc(empresa.get("nombreComercial")));
		return em.createQuery(criteria).getResultList();
	}
	
	@Override
	public List<Empresa> search(String term) {
		List<Empresa> list = new ArrayList<Empresa>();
		String sql = "FROM " + Empresa.class.getName() + " e "
				+ "WHERE e.rut LIKE :term "
				+ "OR e.razonSocial LIKE :term "
				+ "OR e.nombreComercial LIKE :term "
				+ "OR e.web LIKE :term "
				+ "OR e.email LIKE :term ";
		List<?> result = em.createQuery(sql).setParameter("term", '%' + term + '%').getResultList();
		if (!result.isEmpty()) {
			for (Object obj : result) {
				list.add((Empresa) obj);
			}
		}
		return list;
	}

	@Override
	public Boolean add(Empresa empresa) {
		Boolean result = false;
		try {
			em.persist(empresa);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean update(Empresa empresa) {
		Boolean result = false;
		try {
			em.merge(empresa);
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
			Empresa empresa = this.getById(id);
			result = this.remove(empresa);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public Boolean remove(Empresa empresa) {
		Boolean result = false;
		try {
			empresa = em.find(Empresa.class, empresa.getId());
			em.remove(empresa);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Empresa> searchByTipo(String term, String tipo) {
		List<Empresa> list = new ArrayList<Empresa>();
		String className = null;
		List<Object> result;
		if(tipo.compareToIgnoreCase(Constants.PROVEEDOR)==0) {
			className = Proveedor.class.getName();
		} else if(tipo.compareToIgnoreCase(Constants.CLIENTE)==0) {
			className = Cliente.class.getName();
		} else if(tipo.compareToIgnoreCase(Constants.DISTRIBUIDOR)==0) {
			className = Distribuidor.class.getName();
		}
		String sql = "FROM " + className + " e ";
		if (term == null || term.compareToIgnoreCase("void") == 0) {
			result = em.createQuery(sql).getResultList();
		} else {
			sql += "WHERE (lower(e.rut) LIKE lower(:term) "
				+ "OR lower(e.razonSocial) LIKE lower(:term) "
				+ "OR lower(e.nombreComercial) LIKE lower(:term) "
				+ "OR lower(e.web) LIKE lower(:term) "
				+ "OR lower(e.email) LIKE lower(:term)) "
				+ "ORDER BY e.nombreComercial,e.razonSocial";
			result = em.createQuery(sql).setParameter("term", '%' + term + '%').getResultList();
		}
		if (!result.isEmpty()) {
			for (Object row : result) {
				Empresa empresa = (Empresa) row;
				list.add(empresa);
			}
		}
		return list;
	}
	
}
