package com.app.distrity.util.filter;

import java.io.Serializable;
import java.util.Date;

import com.app.distrity.util.paginator.PaginatorRequest;

public class ArticuloFilter implements Serializable {
	private static final long serialVersionUID = 1495464584062024427L;

	private Long categoriaId;
	
	private Long impuestoId;
	
	private Long unidadId;
	
	private Date from;
	
	private Date to;

	private String term;
	
	private Double maxPrecio;
	
	private Double minPrecio;
		
	private PaginatorRequest paginatorRequest;
	
	public ArticuloFilter() {}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public Long getImpuestoId() {
		return impuestoId;
	}

	public void setImpuestoId(Long impuestoId) {
		this.impuestoId = impuestoId;
	}

	public Long getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(Long unidadId) {
		this.unidadId = unidadId;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Double getMaxPrecio() {
		return maxPrecio;
	}

	public void setMaxPrecio(Double maxPrecio) {
		this.maxPrecio = maxPrecio;
	}

	public Double getMinPrecio() {
		return minPrecio;
	}

	public void setMinPrecio(Double minPrecio) {
		this.minPrecio = minPrecio;
	}

	public PaginatorRequest getPaginatorRequest() {
		return paginatorRequest;
	}

	public void setPaginatorRequest(PaginatorRequest paginatorRequest) {
		this.paginatorRequest = paginatorRequest;
	}
	
}