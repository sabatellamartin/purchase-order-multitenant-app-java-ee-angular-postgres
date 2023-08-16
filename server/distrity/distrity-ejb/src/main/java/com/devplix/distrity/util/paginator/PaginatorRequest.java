package com.app.distrity.util.paginator;

import java.io.Serializable;

public class PaginatorRequest implements Serializable {
	private static final long serialVersionUID = 6296916289496326186L;

	private Integer pageSize;
	
	private Integer firstResult;

	public PaginatorRequest() {}

	public PaginatorRequest(Integer pageSize, Integer firstResult) {
		this.pageSize = pageSize;
		this.firstResult = firstResult;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}
	
}