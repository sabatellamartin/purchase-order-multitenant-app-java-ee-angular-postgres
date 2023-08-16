package com.app.distrity.util.paginator;

import java.io.Serializable;
import java.util.List;

public class PaginatorResponse<T> implements Serializable {
	private static final long serialVersionUID = -298581594393024254L;

	private Long totalItems;
	
	private List<T> resultList;

	public PaginatorResponse() {}

	public PaginatorResponse(Long totalItems, List<T> results) {
		this.totalItems = totalItems;
		this.resultList = results;
	}

	public Long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	
}