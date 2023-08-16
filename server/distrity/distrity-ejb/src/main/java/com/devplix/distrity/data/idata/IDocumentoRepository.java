package com.app.distrity.data.idata;

import java.util.List;

import com.app.distrity.model.Documento;
import com.app.distrity.model.OrdenVenta;
import com.app.distrity.util.filter.OrdenVentaFilter;
import com.app.distrity.util.paginator.PaginatorResponse;

public interface IDocumentoRepository {

	public Documento getById(Long id);

	public List<Documento> getAll();

	public Boolean add(Documento documento);
	
	public Boolean update(Documento documento);

	public Boolean removeById(Long id);

	public Boolean remove(Documento documento);

	public PaginatorResponse<OrdenVenta> searchOrdenVenta(OrdenVentaFilter ordenVentaFilter);

}
