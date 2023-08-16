package com.app.distrity.data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.app.distrity.data.idata.ITipoZonaRepository;
import com.app.distrity.model.TipoZona;
import com.app.distrity.util.Constants;

@Stateless
public class TipoZonaRepository implements ITipoZonaRepository {

	@PersistenceContext(unitName = Constants.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	public TipoZonaRepository() {}

	@Override
	public TipoZona getById(Long id) {
		return em.find(TipoZona.class, id);
	}

	@Override
	public TipoZona getByCodigo(String codigo) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TipoZona> criteria = cb.createQuery(TipoZona.class);
		Root<TipoZona> tipoZona = criteria.from(TipoZona.class);
		criteria.select(tipoZona).where(cb.equal(tipoZona.get("codigo"), codigo));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TipoZona> getAll() {
		return em.createQuery("FROM " + TipoZona.class.getName()).getResultList();
	}
	
	@Override
	public List<TipoZona> getAllOrderedByCodigo() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TipoZona> criteria = cb.createQuery(TipoZona.class);
		Root<TipoZona> tipoZona = criteria.from(TipoZona.class);
		criteria.select(tipoZona).orderBy(cb.asc(tipoZona.get("codigo")));
		return em.createQuery(criteria).getResultList();
	}

	
	
	
	@Override
	public List<TipoZona> search(String term) {
		List<TipoZona> list = new ArrayList<TipoZona>();
		String sql = "FROM " + TipoZona.class.getName() + " t "
				+ "WHERE t.descripcion LIKE :term "
				+ "OR t.codigo LIKE :term ";
		List<?> result = em.createQuery(sql).setParameter("term", '%' + term + '%').getResultList();
		if (!result.isEmpty()) {
			for (Object obj : result) {
				list.add((TipoZona) obj);
			}
		}
		return list;
	}

	@Override
	public Boolean add(TipoZona tipoZona) {
		Boolean result = false;
		try {
			em.persist(tipoZona);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean update(TipoZona tipoZona) {
		Boolean result = false;
		try {
			em.merge(tipoZona);
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
			TipoZona tipoZona = this.getById(id);
			result = this.remove(tipoZona);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public Boolean remove(TipoZona tipoZona) {
		Boolean result = false;
		try {
			tipoZona = em.find(TipoZona.class, tipoZona.getId());
			em.remove(tipoZona);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
}
