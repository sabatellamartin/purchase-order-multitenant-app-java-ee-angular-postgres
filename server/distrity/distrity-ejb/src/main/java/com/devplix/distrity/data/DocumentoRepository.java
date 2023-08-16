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

import com.app.distrity.data.idata.IDocumentoRepository;
import com.app.distrity.model.Documento;
import com.app.distrity.model.Estado;
import com.app.distrity.model.OrdenVenta;
import com.app.distrity.util.Constants;
import com.app.distrity.util.filter.OrdenVentaFilter;
import com.app.distrity.util.paginator.PaginatorResponse;

@Stateless
public class DocumentoRepository implements IDocumentoRepository {

	@PersistenceContext(unitName = Constants.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	public DocumentoRepository() {}

	@Override
	public Documento getById(Long id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Documento> criteria = cb.createQuery(Documento.class);
		Root<Documento> documento = criteria.from(Documento.class);
		
		criteria.select(documento).where(cb.equal(documento.get("id"), id));
		return em.createQuery(criteria).getSingleResult();
		
		// OLD FUNCTION
		//return em.find(Documento.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Documento> getAll() {
		return em.createQuery("FROM " + Documento.class.getName()).getResultList();
	}

	@Override
	public Boolean add(Documento documento) {
		Boolean result = false;
		try {
			em.persist(documento);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean update(Documento documento) {
		Boolean result = false;
		try {
			em.merge(documento);
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
			Documento documento = this.getById(id);
			result = this.remove(documento);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public Boolean remove(Documento documento) {
		Boolean result = false;
		try {
			documento = em.find(Documento.class, documento.getId());
			em.remove(documento);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public PaginatorResponse<OrdenVenta> searchOrdenVenta(OrdenVentaFilter filter) {
		Long count = countSearchOrdenVenta(filter);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<OrdenVenta> criteria = cb.createQuery(OrdenVenta.class);
		Root<OrdenVenta> o = criteria.from(OrdenVenta.class);
		criteria.select(o);
		
		List<Predicate> predicate = new ArrayList<Predicate>();
	    if(filter.getOperadorId() != null && filter.getOperadorId() > 0){
	    	Expression<Long> usuario = o.get("usuario").get("id");
	    	predicate.add(cb.equal(usuario, filter.getOperadorId()));//EQUAL:operador
	    }
	    if(filter.getClienteId() != null && filter.getClienteId() > 0){
	    	Expression<Long> cliente = o.get("cliente").get("id");
	    	predicate.add(cb.equal(cliente, filter.getClienteId()));//EQUAL:cliente
	    }
    	Expression<Estado> estado = o.get("estado");
	    if(filter.getEstado()!=null){
	    	predicate.add(cb.equal(estado, filter.getEstado()));//EQUAL:estado
	    } else {
	    	predicate.add(cb.notEqual(estado, Estado.CANCELADO));//NOEQUAL:estado
	    	predicate.add(cb.notEqual(estado, Estado.ENTREGADO));//NOEQUAL:estado
	    }
	    filter.setTerm(filter.getTerm().toUpperCase());// Term To Upper
	    Expression<String> usuarioUsername = o.get("usuario").get("username");
	    Predicate likeUsername = cb.like(cb.upper(usuarioUsername), '%' + filter.getTerm() + '%');
	    Expression<String> numeroDocumento = o.get("numeroDocumento");
	    Predicate likeNroDocumento = cb.like(cb.upper(numeroDocumento), '%' + filter.getTerm() + '%');
	    Expression<String> clienteRut = o.get("cliente").get("rut");
	    Predicate likeClienteRut = cb.like(cb.upper(clienteRut), '%' + filter.getTerm() + '%');
	    Expression<String> clienteRazonSocial = o.get("cliente").get("razonSocial");
	    Predicate likeClienteRazonSocial = cb.like(cb.upper(clienteRazonSocial), '%' + filter.getTerm() + '%');
	    Expression<String> clienteNombreComercial = o.get("cliente").get("nombreComercial");
	    Predicate likeClienteNombreComercial = cb.like(cb.upper(clienteNombreComercial), '%' + filter.getTerm() + '%');
	    predicate.add(cb.or(
	    				likeUsername, 
	    				likeNroDocumento, 
	    				likeClienteRut, 
	    				likeClienteRazonSocial, 
	    				likeClienteNombreComercial)); //OR:LIKES
	    Expression<Date> fecha = o.get("fecha");
	    predicate.add(cb.between(fecha, filter.getFrom(),filter.getTo()));//BETWEEN:fecha
	    
	    if(!predicate.isEmpty()){
	        Predicate[] predicateList = new Predicate[predicate.size()];
	        predicate.toArray(predicateList);
	        criteria.where(predicateList);//WHERE    
	    }
		criteria.orderBy(cb.desc(o.get("fecha")));//ORDER
		List<OrdenVenta> results = em.createQuery(criteria)
			.setFirstResult(filter.getPaginatorRequest().getFirstResult())
			.setMaxResults(filter.getPaginatorRequest().getPageSize())
			.getResultList();
		return new PaginatorResponse<OrdenVenta>(count, results);
	}
	
	// COUNT TOTAL RESULTS
	private Long countSearchOrdenVenta(OrdenVentaFilter filter) {		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
		Root<OrdenVenta> o = criteria.from(OrdenVenta.class);
		List<Predicate> predicate = new ArrayList<Predicate>();
	    if(filter.getOperadorId() != null && filter.getOperadorId() > 0){
	    	Expression<Long> usuario = o.get("usuario").get("id");
	    	predicate.add(cb.equal(usuario, filter.getOperadorId()));//EQUAL:operador
	    }
	    if(filter.getClienteId() != null && filter.getClienteId() > 0){
	    	Expression<Long> cliente = o.get("cliente").get("id");
	    	predicate.add(cb.equal(cliente, filter.getClienteId()));//EQUAL:cliente
	    }
    	Expression<Estado> estado = o.get("estado");
	    if(filter.getEstado()!=null){
	    	predicate.add(cb.equal(estado, filter.getEstado()));//EQUAL:estado
	    } else {
	    	predicate.add(cb.notEqual(estado, Estado.CANCELADO));//NOEQUAL:estado
	    	predicate.add(cb.notEqual(estado, Estado.ENTREGADO));//NOEQUAL:estado
	    }
	    filter.setTerm(filter.getTerm().toUpperCase());// Term To Upper
	    Expression<String> usuarioUsername = o.get("usuario").get("username");
	    Predicate likeUsername = cb.like(cb.upper(usuarioUsername), '%' + filter.getTerm() + '%');
	    Expression<String> numeroDocumento = o.get("numeroDocumento");
	    Predicate likeNroDocumento = cb.like(cb.upper(numeroDocumento), '%' + filter.getTerm() + '%');
	    Expression<String> clienteRut = o.get("cliente").get("rut");
	    Predicate likeClienteRut = cb.like(cb.upper(clienteRut), '%' + filter.getTerm() + '%');
	    Expression<String> clienteRazonSocial = o.get("cliente").get("razonSocial");
	    Predicate likeClienteRazonSocial = cb.like(cb.upper(clienteRazonSocial), '%' + filter.getTerm() + '%');
	    Expression<String> clienteNombreComercial = o.get("cliente").get("nombreComercial");
	    Predicate likeClienteNombreComercial = cb.like(cb.upper(clienteNombreComercial), '%' + filter.getTerm() + '%');
	    predicate.add(cb.or(
	    				likeUsername, 
	    				likeNroDocumento, 
	    				likeClienteRut, 
	    				likeClienteRazonSocial, 
	    				likeClienteNombreComercial)); //OR:LIKES
	    Expression<Date> fecha = o.get("fecha");
	    predicate.add(cb.between(fecha, filter.getFrom(),filter.getTo()));//BETWEEN:fecha
	    
	    if(!predicate.isEmpty()){
	        Predicate[] predicateList = new Predicate[predicate.size()];
	        predicate.toArray(predicateList);
	        criteria.where(predicateList);//WHERE    
	    }
	    
	    criteria.select(cb.count(o));
		return em.createQuery(criteria).getSingleResult();
	}
	
}
