package com.app.distrity.data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.app.distrity.data.idata.IUnidadRepository;
import com.app.distrity.model.Unidad;
import com.app.distrity.util.Constants;

@Stateless
public class UnidadRepository implements IUnidadRepository {

	@PersistenceContext(unitName = Constants.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	public UnidadRepository() {}

	@Override
	public Unidad getById(Long id) {
		return em.find(Unidad.class, id);
	}

	@Override
	public Unidad getByCodigo(String codigo) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Unidad> criteria = cb.createQuery(Unidad.class);
		Root<Unidad> unidad = criteria.from(Unidad.class);
		criteria.select(unidad).where(cb.equal(unidad.get("codigo"), codigo));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Unidad> getAll() {
		return em.createQuery("FROM " + Unidad.class.getName()).getResultList();
	}
	
	@Override
	public List<Unidad> getAllOrderedByCodigo() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Unidad> criteria = cb.createQuery(Unidad.class);
		Root<Unidad> unidad = criteria.from(Unidad.class);
		criteria.select(unidad).orderBy(cb.asc(unidad.get("codigo")));
		return em.createQuery(criteria).getResultList();
	}

	
	
	
	@Override
	public List<Unidad> search(String term) {
		List<Unidad> list = new ArrayList<Unidad>();
		String sql = "FROM " + Unidad.class.getName() + " t "
				+ "WHERE t.descripcion LIKE :term "
				+ "OR t.codigo LIKE :term ";
		List<?> result = em.createQuery(sql).setParameter("term", '%' + term + '%').getResultList();
		if (!result.isEmpty()) {
			for (Object obj : result) {
				list.add((Unidad) obj);
			}
		}
		return list;
	}

	@Override
	public Boolean add(Unidad unidad) {
		Boolean result = false;
		try {
			em.persist(unidad);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean update(Unidad unidad) {
		Boolean result = false;
		try {
			em.merge(unidad);
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
			Unidad unidad = this.getById(id);
			result = this.remove(unidad);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public Boolean remove(Unidad unidad) {
		Boolean result = false;
		try {
			unidad = em.find(Unidad.class, unidad.getId());
			em.remove(unidad);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
}
