package com.app.distrity.data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.app.distrity.data.idata.ITipoDireccionRepository;
import com.app.distrity.model.TipoDireccion;
import com.app.distrity.util.Constants;

@Stateless
public class TipoDireccionRepository implements ITipoDireccionRepository {

	@PersistenceContext(unitName = Constants.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	public TipoDireccionRepository() {}

	@Override
	public TipoDireccion getById(Long id) {
		return em.find(TipoDireccion.class, id);
	}

	@Override
	public TipoDireccion getByCodigo(String codigo) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TipoDireccion> criteria = cb.createQuery(TipoDireccion.class);
		Root<TipoDireccion> tipoDireccion = criteria.from(TipoDireccion.class);
		criteria.select(tipoDireccion).where(cb.equal(tipoDireccion.get("codigo"), codigo));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TipoDireccion> getAll() {
		return em.createQuery("FROM " + TipoDireccion.class.getName()).getResultList();
	}
	
	@Override
	public List<TipoDireccion> getAllOrderedByCodigo() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TipoDireccion> criteria = cb.createQuery(TipoDireccion.class);
		Root<TipoDireccion> tipoDireccion = criteria.from(TipoDireccion.class);
		criteria.select(tipoDireccion).orderBy(cb.asc(tipoDireccion.get("codigo")));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<TipoDireccion> search(String term) {
		List<TipoDireccion> list = new ArrayList<TipoDireccion>();
		String sql = "FROM " + TipoDireccion.class.getName() + " t "
				+ "WHERE t.descripcion LIKE :term "
				+ "OR t.codigo LIKE :term ";
		List<?> result = em.createQuery(sql).setParameter("term", '%' + term + '%').getResultList();
		if (!result.isEmpty()) {
			for (Object obj : result) {
				list.add((TipoDireccion) obj);
			}
		}
		return list;
	}

	@Override
	public Boolean add(TipoDireccion tipoDireccion) {
		Boolean result = false;
		try {
			em.persist(tipoDireccion);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean update(TipoDireccion tipoDireccion) {
		Boolean result = false;
		try {
			em.merge(tipoDireccion);
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
			TipoDireccion tipoDireccion = this.getById(id);
			result = this.remove(tipoDireccion);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public Boolean remove(TipoDireccion tipoDireccion) {
		Boolean result = false;
		try {
			tipoDireccion = em.find(TipoDireccion.class, tipoDireccion.getId());
			em.remove(tipoDireccion);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
}
