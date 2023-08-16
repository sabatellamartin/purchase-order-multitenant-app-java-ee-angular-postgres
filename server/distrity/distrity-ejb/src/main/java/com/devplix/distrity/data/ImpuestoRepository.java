package com.app.distrity.data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.app.distrity.data.idata.IImpuestoRepository;
import com.app.distrity.model.Impuesto;
import com.app.distrity.util.Constants;

@Stateless
public class ImpuestoRepository implements IImpuestoRepository {

	@PersistenceContext(unitName = Constants.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	public ImpuestoRepository() {}

	@Override
	public Impuesto getById(Long id) {
		return em.find(Impuesto.class, id);
	}

	@Override
	public Impuesto getByNombre(String nombre) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Impuesto> criteria = cb.createQuery(Impuesto.class);
		Root<Impuesto> impuesto = criteria.from(Impuesto.class);
		criteria.select(impuesto).where(cb.equal(impuesto.get("nombre"), nombre));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Impuesto> getAll() {
		return em.createQuery("FROM " + Impuesto.class.getName()).getResultList();
	}
	
	@Override
	public List<Impuesto> getAllOrderedByCodigo() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Impuesto> criteria = cb.createQuery(Impuesto.class);
		Root<Impuesto> impuesto = criteria.from(Impuesto.class);
		criteria.select(impuesto).orderBy(cb.asc(impuesto.get("nombre")));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<Impuesto> search(String term) {
		List<Impuesto> list = new ArrayList<Impuesto>();
		String sql = "FROM " + Impuesto.class.getName() + " t "
				+ "WHERE t.nombre LIKE :term "
				+ "ORDER BY t.nombre ";
		List<?> result = em.createQuery(sql).setParameter("term", '%' + term + '%').getResultList();
		if (!result.isEmpty()) {
			for (Object obj : result) {
				list.add((Impuesto) obj);
			}
		}
		return list;
	}

	@Override
	public Boolean add(Impuesto impuesto) {
		Boolean result = false;
		try {
			em.persist(impuesto);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean update(Impuesto impuesto) {
		Boolean result = false;
		try {
			em.merge(impuesto);
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
			Impuesto impuesto = this.getById(id);
			result = this.remove(impuesto);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public Boolean remove(Impuesto impuesto) {
		Boolean result = false;
		try {
			impuesto = em.find(Impuesto.class, impuesto.getId());
			em.remove(impuesto);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
}
