package com.app.distrity.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.app.distrity.data.idata.IArticuloRepository;
import com.app.distrity.model.Articulo;
import com.app.distrity.util.Constants;
import com.app.distrity.util.filter.ArticuloFilter;
import com.app.distrity.util.paginator.PaginatorResponse;

@Stateless
public class ArticuloRepository implements IArticuloRepository {

	@PersistenceContext(unitName = Constants.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	public ArticuloRepository() {}

	@Override
	public Articulo getById(Long id) {
		return em.find(Articulo.class, id);
	}

	@Override
	public Articulo getByCodigo(String codigo) {
		Articulo response = null;
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Articulo> criteria = cb.createQuery(Articulo.class);
		Root<Articulo> articulo = criteria.from(Articulo.class);
		criteria.select(articulo).where(cb.equal(articulo.get("codigo"), codigo));
		//return em.createQuery(criteria).getSingleResult();
		List<?> results = em.createQuery(criteria).getResultList();
		if (!results.isEmpty()) {
			response = (Articulo) results.get(0);
		}
		return response;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Articulo> getAll() {
		return em.createQuery("FROM " + Articulo.class.getName()).getResultList();
	}
	
	@Override
	public List<Articulo> getAllOrderedByCodigo() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Articulo> criteria = cb.createQuery(Articulo.class);
		Root<Articulo> articulo = criteria.from(Articulo.class);
		criteria.select(articulo).orderBy(cb.asc(articulo.get("codigo")));
		return em.createQuery(criteria).getResultList();
	}

	
	
	
	@Override
	public List<Articulo> search(String term) {
		List<Articulo> list = new ArrayList<Articulo>();
		String sql = "FROM " + Articulo.class.getName() + " a "
				+ "WHERE lower(a.descripcion) LIKE lower(:term) "
				+ "OR lower(a.codigo) LIKE lower(:term) "
				+ "OR lower(a.nombre) LIKE lower(:term) ";
		List<?> result = em.createQuery(sql).setParameter("term", '%' + term + '%').getResultList();
		if (!result.isEmpty()) {
			for (Object obj : result) {
				list.add((Articulo) obj);
			}
		}
		return list;
	}

	@Override
	public Boolean add(Articulo articulo) {
		Boolean result = false;
		try {
			em.persist(articulo);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean update(Articulo articulo) {
		Boolean result = false;
		try {
			em.merge(articulo);
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
			Articulo articulo = this.getById(id);
			result = this.remove(articulo);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public Boolean remove(Articulo articulo) {
		Boolean result = false;
		try {
			articulo = em.find(Articulo.class, articulo.getId());
			em.remove(articulo);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public PaginatorResponse<Articulo> searchFilter(ArticuloFilter filter) {
		Long count = countSearchFilter(filter);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Articulo> criteria = cb.createQuery(Articulo.class);
		Root<Articulo> articulo = criteria.from(Articulo.class);
		criteria.select(articulo);
		List<Predicate> predicate = new ArrayList<Predicate>();
	    if(filter.getCategoriaId() != null && filter.getCategoriaId() > 0) {
	    	Expression<Long> categoria = articulo.get("categoria").get("id");
	    	predicate.add(cb.equal(categoria, filter.getCategoriaId()));//EQUAL:categoria
	    }
	    if(filter.getImpuestoId() != null && filter.getImpuestoId() > 0){
	    	Expression<Long> impuesto = articulo.get("impuesto").get("id");
	    	predicate.add(cb.equal(impuesto, filter.getImpuestoId()));//EQUAL:impuesto
	    }
	    if(filter.getUnidadId() != null && filter.getUnidadId() > 0) {
	    	Expression<Long> unidad = articulo.get("unidad").get("id");
	    	predicate.add(cb.equal(unidad, filter.getUnidadId()));//EQUAL:categoria
	    }
	    filter.setTerm(filter.getTerm().toUpperCase());// Term To Upper
	    Expression<String> nombre = articulo.get("nombre");
	    Predicate likeNombre = cb.like(cb.upper(nombre), '%' + filter.getTerm() + '%');
	    Expression<String> descripcion = articulo.get("descripcion");
	    Predicate likeDescripcion = cb.like(cb.upper(descripcion), '%' + filter.getTerm() + '%');
	    Expression<String> codigoBarra = articulo.get("codigoBarra");
	    Predicate likeCodigoBarra = cb.like(cb.upper(codigoBarra), '%' + filter.getTerm() + '%');
	    predicate.add(cb.or(
	    		likeNombre, 
	    		likeDescripcion, 
	    		likeCodigoBarra)); //OR:LIKES
	    if(filter.getFrom()!=null && filter.getTo()!=null) {
		    Expression<Date> fechaAlta = articulo.get("fechaAlta");
		    predicate.add(cb.between(fechaAlta, filter.getFrom(),filter.getTo()));//BETWEEN:fecha
		    Expression<Date> fechaBaja = articulo.get("fechaBaja");
		    predicate.add(cb.between(fechaBaja, filter.getFrom(),filter.getTo()));//BETWEEN:fecha
	    }
	    if(filter.getMaxPrecio() != null && filter.getMaxPrecio() > 0) {
	    	Expression<Long> precioVenta = articulo.get("precioVenta");
	    	predicate.add(cb.le(precioVenta, filter.getMaxPrecio()));//LOWER EQUAL: precio
	    }
	    if(filter.getMinPrecio() != null && filter.getMinPrecio() > 0) {
	    	Expression<Long> precioVenta = articulo.get("precioVenta");
	    	predicate.add(cb.ge(precioVenta, filter.getMinPrecio()));//GREATER EQUAL: precio
	    }
	    
	    if(!predicate.isEmpty()){
	        Predicate[] predicateList = new Predicate[predicate.size()];
	        predicate.toArray(predicateList);
	        criteria.where(predicateList);//WHERE    
	    }
		criteria.orderBy(cb.desc(articulo.get("codigo")));//ORDER
		
		List<Articulo> results = em.createQuery(criteria)
			.setFirstResult(filter.getPaginatorRequest().getFirstResult())
			.setMaxResults(filter.getPaginatorRequest().getPageSize())
			.getResultList();
		return new PaginatorResponse<Articulo>(count, results);
	}
	
	// COUNT TOTAL RESULTS
	private Long countSearchFilter(ArticuloFilter filter) {		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
		Root<Articulo> articulo = criteria.from(Articulo.class);
		
		List<Predicate> predicate = new ArrayList<Predicate>();
	    if(filter.getCategoriaId() != null && filter.getCategoriaId() > 0) {
	    	Expression<Long> categoria = articulo.get("categoria").get("id");
	    	predicate.add(cb.equal(categoria, filter.getCategoriaId()));//EQUAL:categoria
	    }
	    if(filter.getImpuestoId() != null && filter.getImpuestoId() > 0){
	    	Expression<Long> impuesto = articulo.get("impuesto").get("id");
	    	predicate.add(cb.equal(impuesto, filter.getImpuestoId()));//EQUAL:impuesto
	    }
	    if(filter.getUnidadId() != null && filter.getUnidadId() > 0) {
	    	Expression<Long> unidad = articulo.get("unidad").get("id");
	    	predicate.add(cb.equal(unidad, filter.getUnidadId()));//EQUAL:categoria
	    }
	    filter.setTerm(filter.getTerm().toUpperCase());// Term To Upper
	    Expression<String> nombre = articulo.get("nombre");
	    Predicate likeNombre = cb.like(cb.upper(nombre), '%' + filter.getTerm() + '%');
	    Expression<String> descripcion = articulo.get("descripcion");
	    Predicate likeDescripcion = cb.like(cb.upper(descripcion), '%' + filter.getTerm() + '%');
	    Expression<String> codigoBarra = articulo.get("codigoBarra");
	    Predicate likeCodigoBarra = cb.like(cb.upper(codigoBarra), '%' + filter.getTerm() + '%');
	    predicate.add(cb.or(
	    		likeNombre, 
	    		likeDescripcion, 
	    		likeCodigoBarra)); //OR:LIKES
	    if(filter.getFrom()!=null && filter.getTo()!=null) {
		    Expression<Date> fechaAlta = articulo.get("fechaAlta");
		    predicate.add(cb.between(fechaAlta, filter.getFrom(),filter.getTo()));//BETWEEN:fecha
		    Expression<Date> fechaBaja = articulo.get("fechaBaja");
		    predicate.add(cb.between(fechaBaja, filter.getFrom(),filter.getTo()));//BETWEEN:fecha
	    }
	    if(filter.getMaxPrecio() != null && filter.getMaxPrecio() > 0) {
	    	Expression<Long> precioVenta = articulo.get("precioVenta");
	    	predicate.add(cb.le(precioVenta, filter.getMaxPrecio()));//LOWER EQUAL: precio
	    }
	    if(filter.getMinPrecio() != null && filter.getMinPrecio() > 0) {
	    	Expression<Long> precioVenta = articulo.get("precioVenta");
	    	predicate.add(cb.ge(precioVenta, filter.getMinPrecio()));//GREATER EQUAL: precio
	    }
	    
	    if(!predicate.isEmpty()){
	        Predicate[] predicateList = new Predicate[predicate.size()];
	        predicate.toArray(predicateList);
	        criteria.where(predicateList);//WHERE    
	    }
	    
	    criteria.select(cb.count(articulo));
		return em.createQuery(criteria).getSingleResult();
	}
	
}
