package com.app.distrity.util.filter;

import java.io.Serializable;
import java.util.Date;

import com.app.distrity.model.Estado;
import com.app.distrity.util.paginator.PaginatorRequest;

public class OrdenVentaFilter implements Serializable {
	private static final long serialVersionUID = 8867534840366397530L;

	private Long operadorId;
	
	private Long clienteId;

	private Date from;
	
	private Date to;

	private String term;
	
	private Integer limit;
	
	private Estado estado;
	
	private PaginatorRequest paginatorRequest;
	
	public OrdenVentaFilter() {}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
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

	public Long getOperadorId() {
		return operadorId;
	}

	public void setOperadorId(Long operadorId) {
		this.operadorId = operadorId;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public PaginatorRequest getPaginatorRequest() {
		return paginatorRequest;
	}

	public void setPaginatorRequest(PaginatorRequest paginatorRequest) {
		this.paginatorRequest = paginatorRequest;
	}
	
}