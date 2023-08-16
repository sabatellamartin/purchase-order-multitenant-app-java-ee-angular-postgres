package com.app.distrity.service.iservice;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.app.distrity.model.Documento;
import com.app.distrity.model.OrdenVenta;
import com.app.distrity.model.auth.Operador;
import com.app.distrity.util.filter.OrdenVentaFilter;
import com.app.distrity.util.paginator.PaginatorResponse;

public interface IDocumentoService {
	
	public Documento getById(Long id);
	
	public List<Documento> getAll();

	public Boolean addOrdenVenta(OrdenVenta documento, Operador usuario);

	public Boolean update(Documento documento);
	
	public Boolean removeById(Long id);

	public Boolean remove(Documento documento);

	public Boolean changeEstadoById(Long id);

	public Boolean cancelarToggle(Long id);

	public ByteArrayOutputStream ordenVentaToPdfById(Long id);

	public Boolean updateOrdenVenta(OrdenVenta ordenVenta);

	public PaginatorResponse<OrdenVenta> searchOrdenVenta(OrdenVentaFilter ordenVentaFilter);

}
