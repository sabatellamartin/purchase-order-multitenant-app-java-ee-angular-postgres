package com.app.distrity.data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.app.distrity.data.idata.ICategoriaRepository;
import com.app.distrity.model.Categoria;
import com.app.distrity.util.Constants;

@Stateless
public class CategoriaRepository implements ICategoriaRepository {

	@PersistenceContext(unitName = Constants.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	public CategoriaRepository() {}

	@Override
	public Categoria getById(Long id) {
		return em.find(Categoria.class, id);
	}

	@Override
	public Categoria getByNombre(String nombre) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Categoria> criteria = cb.createQuery(Categoria.class);
		Root<Categoria> categoria = criteria.from(Categoria.class);
		criteria.select(categoria).where(cb.equal(categoria.get("nombre"), nombre));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Categoria> getAll() {
		return em.createQuery("FROM " + Categoria.class.getName()).getResultList();
	}
	
	@Override
	public List<Categoria> getAllOrderedByCodigo() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Categoria> criteria = cb.createQuery(Categoria.class);
		Root<Categoria> categoria = criteria.from(Categoria.class);
		criteria.select(categoria).orderBy(cb.asc(categoria.get("nombre")));
		return em.createQuery(criteria).getResultList();
	}
	
	@Override
	public List<Categoria> search(String term) {
		List<Categoria> list = new ArrayList<Categoria>();
		String sql = "FROM " + Categoria.class.getName() + " t "
				+ "WHERE t.descripcion LIKE :term "
				+ "OR t.nombre LIKE :term "
				+ "ORDER BY t.nombre";
		List<?> result = em.createQuery(sql).setParameter("term", '%' + term + '%').getResultList();
		if (!result.isEmpty()) {
			for (Object obj : result) {
				list.add((Categoria) obj);
			}
		}
		return list;
	}

	@Override
	public Boolean add(Categoria categoria) {
		Boolean result = false;
		try {
			em.persist(categoria);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean update(Categoria categoria) {
		Boolean result = false;
		try {
			em.merge(categoria);
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
			Categoria categoria = this.getById(id);
			result = this.remove(categoria);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public Boolean remove(Categoria categoria) {
		Boolean result = false;
		try {
			categoria = em.find(Categoria.class, categoria.getId());
			em.remove(categoria);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
}
