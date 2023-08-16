package com.app.distrity.data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.app.distrity.data.idata.IMonedaRepository;
import com.app.distrity.model.Moneda;
import com.app.distrity.util.Constants;

@Stateless
public class MonedaRepository implements IMonedaRepository {

	@PersistenceContext(unitName = Constants.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	public MonedaRepository() {}

	@Override
	public Moneda getById(Long id) {
		return em.find(Moneda.class, id);
	}

	@Override
	public Moneda getByCodigo(String codigo) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Moneda> criteria = cb.createQuery(Moneda.class);
		Root<Moneda> moneda = criteria.from(Moneda.class);
		criteria.select(moneda).where(cb.equal(moneda.get("codigo"), codigo));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Moneda> getAll() {
		return em.createQuery("FROM " + Moneda.class.getName()).getResultList();
	}
	
	@Override
	public List<Moneda> getAllOrderedByCodigo() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Moneda> criteria = cb.createQuery(Moneda.class);
		Root<Moneda> moneda = criteria.from(Moneda.class);
		criteria.select(moneda).orderBy(cb.asc(moneda.get("codigo")));
		return em.createQuery(criteria).getResultList();
	}


	@Override
	public List<Moneda> search(String term) {
		List<Moneda> list = new ArrayList<Moneda>();
		String sql = "FROM " + Moneda.class.getName() + " t "
				+ "WHERE t.descripcion LIKE :term "
				+ "OR t.codigo LIKE :term "
				+ "OR t.sigla LIKE :term "
				+ "OR t.codigoDgi LIKE :term ";
		List<?> result = em.createQuery(sql).setParameter("term", '%' + term + '%').getResultList();
		if (!result.isEmpty()) {
			for (Object obj : result) {
				list.add((Moneda) obj);
			}
		}
		return list;
	}

	@Override
	public Boolean add(Moneda moneda) {
		Boolean result = false;
		try {
			em.persist(moneda);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean update(Moneda moneda) {
		Boolean result = false;
		try {
			em.merge(moneda);
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
			Moneda moneda = this.getById(id);
			result = this.remove(moneda);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public Boolean remove(Moneda moneda) {
		Boolean result = false;
		try {
			moneda = em.find(Moneda.class, moneda.getId());
			em.remove(moneda);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
}
