package com.app.distrity.data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.app.distrity.data.idata.ICalificacionRepository;
import com.app.distrity.model.Calificacion;
import com.app.distrity.util.Constants;

@Stateless
public class CalificacionRepository implements ICalificacionRepository {

	@PersistenceContext(unitName = Constants.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	public CalificacionRepository() {}

	@Override
	public Calificacion getById(Long id) {
		return em.find(Calificacion.class, id);
	}

	@Override
	public Calificacion getByCodigo(String codigo) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Calificacion> criteria = cb.createQuery(Calificacion.class);
		Root<Calificacion> calificacion = criteria.from(Calificacion.class);
		criteria.select(calificacion).where(cb.equal(calificacion.get("codigo"), codigo));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Calificacion> getAll() {
		return em.createQuery("FROM " + Calificacion.class.getName()).getResultList();
	}
	
	@Override
	public List<Calificacion> getAllOrderedByCodigo() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Calificacion> criteria = cb.createQuery(Calificacion.class);
		Root<Calificacion> calificacion = criteria.from(Calificacion.class);
		criteria.select(calificacion).orderBy(cb.asc(calificacion.get("codigo")));
		return em.createQuery(criteria).getResultList();
	}

	
	
	
	@Override
	public List<Calificacion> search(String term) {
		List<Calificacion> list = new ArrayList<Calificacion>();
		String sql = "FROM " + Calificacion.class.getName() + " t "
				+ "WHERE t.descripcion LIKE :term "
				+ "OR t.codigo LIKE :term ";
		List<?> result = em.createQuery(sql).setParameter("term", '%' + term + '%').getResultList();
		if (!result.isEmpty()) {
			for (Object obj : result) {
				list.add((Calificacion) obj);
			}
		}
		return list;
	}

	@Override
	public Boolean add(Calificacion calificacion) {
		Boolean result = false;
		try {
			em.persist(calificacion);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean update(Calificacion calificacion) {
		Boolean result = false;
		try {
			em.merge(calificacion);
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
			Calificacion calificacion = this.getById(id);
			result = this.remove(calificacion);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public Boolean remove(Calificacion calificacion) {
		Boolean result = false;
		try {
			calificacion = em.find(Calificacion.class, calificacion.getId());
			em.remove(calificacion);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
}
