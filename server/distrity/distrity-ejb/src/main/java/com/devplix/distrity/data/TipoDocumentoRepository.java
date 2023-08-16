package com.app.distrity.data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.app.distrity.data.idata.ITipoDocumentoRepository;
import com.app.distrity.model.TipoDocumento;
import com.app.distrity.util.Constants;

@Stateless
public class TipoDocumentoRepository implements ITipoDocumentoRepository {

	@PersistenceContext(unitName = Constants.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	public TipoDocumentoRepository() {}

	@Override
	public TipoDocumento getById(Long id) {
		return em.find(TipoDocumento.class, id);
	}

	@Override
	public TipoDocumento getBySigla(String sigla) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TipoDocumento> criteria = cb.createQuery(TipoDocumento.class);
		Root<TipoDocumento> tipoDocumento = criteria.from(TipoDocumento.class);
		criteria.select(tipoDocumento).where(cb.equal(tipoDocumento.get("sigla"), sigla));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TipoDocumento> getAll() {
		return em.createQuery("FROM " + TipoDocumento.class.getName()).getResultList();
	}
	
	@Override
	public List<TipoDocumento> getAllOrderedBySigla() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TipoDocumento> criteria = cb.createQuery(TipoDocumento.class);
		Root<TipoDocumento> tipoDocumento = criteria.from(TipoDocumento.class);
		criteria.select(tipoDocumento).orderBy(cb.asc(tipoDocumento.get("sigla")));
		return em.createQuery(criteria).getResultList();
	}

	
	
	
	@Override
	public List<TipoDocumento> search(String term) {
		List<TipoDocumento> list = new ArrayList<TipoDocumento>();
		String sql = "FROM " + TipoDocumento.class.getName() + " t "
				+ "WHERE t.descripcion LIKE :term "
				+ "OR t.sigla LIKE :term ";
		List<?> result = em.createQuery(sql).setParameter("term", '%' + term + '%').getResultList();
		if (!result.isEmpty()) {
			for (Object obj : result) {
				list.add((TipoDocumento) obj);
			}
		}
		return list;
	}

	@Override
	public Boolean add(TipoDocumento tipoDocumento) {
		Boolean result = false;
		try {
			em.persist(tipoDocumento);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean update(TipoDocumento tipoDocumento) {
		Boolean result = false;
		try {
			em.merge(tipoDocumento);
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
			TipoDocumento tipoDocumento = this.getById(id);
			result = this.remove(tipoDocumento);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public Boolean remove(TipoDocumento tipoDocumento) {
		Boolean result = false;
		try {
			tipoDocumento = em.find(TipoDocumento.class, tipoDocumento.getId());
			em.remove(tipoDocumento);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
}
