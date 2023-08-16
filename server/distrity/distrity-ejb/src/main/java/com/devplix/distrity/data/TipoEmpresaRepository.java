package com.app.distrity.data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.app.distrity.data.idata.ITipoEmpresaRepository;
import com.app.distrity.model.TipoEmpresa;
import com.app.distrity.util.Constants;

@Stateless
public class TipoEmpresaRepository implements ITipoEmpresaRepository {

	@PersistenceContext(unitName = Constants.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	public TipoEmpresaRepository() {}

	@Override
	public TipoEmpresa getById(Long id) {
		return em.find(TipoEmpresa.class, id);
	}

	@Override
	public TipoEmpresa getBySigla(String sigla) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TipoEmpresa> criteria = cb.createQuery(TipoEmpresa.class);
		Root<TipoEmpresa> tipoEmpresa = criteria.from(TipoEmpresa.class);
		criteria.select(tipoEmpresa).where(cb.equal(tipoEmpresa.get("sigla"), sigla));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TipoEmpresa> getAll() {
		return em.createQuery("FROM " + TipoEmpresa.class.getName()).getResultList();
	}
	
	@Override
	public List<TipoEmpresa> getAllOrderedBySigla() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TipoEmpresa> criteria = cb.createQuery(TipoEmpresa.class);
		Root<TipoEmpresa> tipoEmpresa = criteria.from(TipoEmpresa.class);
		criteria.select(tipoEmpresa).orderBy(cb.asc(tipoEmpresa.get("sigla")));
		return em.createQuery(criteria).getResultList();
	}

	
	
	
	@Override
	public List<TipoEmpresa> search(String term) {
		List<TipoEmpresa> list = new ArrayList<TipoEmpresa>();
		String sql = "FROM " + TipoEmpresa.class.getName() + " t "
				+ "WHERE t.descripcion LIKE :term "
				+ "OR t.sigla LIKE :term ";
		List<?> result = em.createQuery(sql).setParameter("term", '%' + term + '%').getResultList();
		if (!result.isEmpty()) {
			for (Object obj : result) {
				list.add((TipoEmpresa) obj);
			}
		}
		return list;
	}

	@Override
	public Boolean add(TipoEmpresa tipoEmpresa) {
		Boolean result = false;
		try {
			em.persist(tipoEmpresa);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean update(TipoEmpresa tipoEmpresa) {
		Boolean result = false;
		try {
			em.merge(tipoEmpresa);
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
			TipoEmpresa tipoEmpresa = this.getById(id);
			result = this.remove(tipoEmpresa);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public Boolean remove(TipoEmpresa tipoEmpresa) {
		Boolean result = false;
		try {
			tipoEmpresa = em.find(TipoEmpresa.class, tipoEmpresa.getId());
			em.remove(tipoEmpresa);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
}
