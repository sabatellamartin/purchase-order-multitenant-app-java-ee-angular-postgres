package com.app.distrity.service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.app.distrity.data.idata.IDocumentoRepository;
import com.app.distrity.model.Detalle;
import com.app.distrity.model.Distribuidor;
import com.app.distrity.model.Documento;
import com.app.distrity.model.Estado;
import com.app.distrity.model.OrdenVenta;
import com.app.distrity.model.auth.Operador;
import com.app.distrity.service.iservice.IDocumentoService;
import com.app.distrity.service.iservice.IEmpresaService;
import com.app.distrity.util.filter.OrdenVentaFilter;
import com.app.distrity.util.paginator.PaginatorResponse;
import com.app.distrity.util.pdf.IPDFDocumento;

@Stateless
public class DocumentoService implements IDocumentoService {

	@Inject
	private IDocumentoRepository documentoRepository;
    
	@Inject
	private IEmpresaService empresaService;
	
	@Inject
    private IPDFDocumento pdfDocumento;
	
	@Override
	public Documento getById(Long id) {
		return documentoRepository.getById(id);
	}

	@Override
	public List<Documento> getAll() {
		return documentoRepository.getAll();
	}
	
	@Override
	public Boolean addOrdenVenta(OrdenVenta documento, Operador usuario) {
		documento.setFecha(new Date());
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DATE, 30);
		Date vencimiento = calendar.getTime();
		documento.setVencimiento(vencimiento);
		if (documento instanceof OrdenVenta) {
			long unixTime = System.currentTimeMillis() / 1000L;
			documento.setNumeroDocumento("OV"+unixTime);
		}
		documento.setUsuario(usuario);
		((OrdenVenta)documento).setEstado(Estado.CREADO);
		// FALTA VALIDAR TOTAL
		List<Detalle> detalles = documento.getDetalles();
		documento.setDetalles(new ArrayList<Detalle>());
		Double total = 0.0;
		for (Detalle detalle : detalles) {
			total += this.precioParcial(detalle);
			documento.addDetalle(detalle); // for each save bidirectional reference
		}
		// TERMINAR DE VALIDAR EL TOTAL
		System.out.println("TOTAL FRONTEND " + documento.getTotal().toString());
		System.out.println("TOTAL BACKEND " + total.toString());
		return documentoRepository.add(documento);
	}
	
	@Override
	public Boolean updateOrdenVenta(OrdenVenta documento) {
		List<Detalle> detalles = documento.getDetalles();
		documento.setDetalles(new ArrayList<Detalle>());
		Double total = 0.0;
		for (Detalle detalle : detalles) {
			total += this.precioParcial(detalle);
			documento.addDetalle(detalle); // for each save bidirectional reference
		}
		// TERMINAR DE VALIDAR EL TOTAL
		System.out.println("TOTAL FRONTEND " + documento.getTotal().toString());
		System.out.println("TOTAL BACKEND " + total.toString());
		return documentoRepository.update(documento);
	}

	@Override
	public Boolean update(Documento documento) {
		return documentoRepository.update(documento);
	}
	
	private Double precioParcial(Detalle detalle) {
		return this.redondeoPrecio(this.precioUnitario(detalle.getPrecio(),detalle.getDescuento())*detalle.getCantidad());	
	}

	private Double precioUnitario(Double precio, Double descuento) {
		return this.redondeoPrecio(precio*(1-(descuento/100)));
	}
	
	private Double redondeoPrecio(Double precio) {
		return round(precio, 2); // if 200.3566998 then returns 200.35
	}
	
	public static Double round(Double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor; 
	}

	@Override
	public Boolean removeById(Long id) {
		return documentoRepository.removeById(id);
	}

	@Override
	public Boolean remove(Documento documento) {
		return documentoRepository.remove(documento);
	}
	
	@Override
	public Boolean changeEstadoById(Long id) {
		Boolean response = false;
		OrdenVenta documento = null;
		if (id>0) {
			documento = (OrdenVenta) this.getById(id);
		}
		if (documento!=null) {
			documento.setEstado(this.getNextEstado(documento.getEstado()));
			response = this.update(documento);
		}
		return response;
	}

	public Estado getNextEstado(Estado estado) {
	    Estado nextEstado = Estado.CANCELADO;
	    if (estado == Estado.CREADO) {
	      nextEstado = Estado.VALIDADO;
	    } else if (estado == Estado.VALIDADO) {
	      nextEstado = Estado.PROCESADO;
	    } else if (estado == Estado.PROCESADO) {
	      nextEstado = Estado.ENVIADO;
	    } else if (estado == Estado.ENVIADO) {
	      nextEstado = Estado.ENTREGADO;
	    }
	    return nextEstado;
	}

	@Override
	public Boolean cancelarToggle(Long id) {
		Boolean response = false;
		OrdenVenta documento = null;
		if (id>0) {
			documento = (OrdenVenta) this.getById(id);
		}
		if (documento!=null) {
			if (documento.getEstado() == Estado.CANCELADO) {
				documento.setEstado(Estado.CREADO);
			} else if (documento.getEstado() != Estado.CANCELADO) {
				documento.setEstado(Estado.CANCELADO);
			} 
			response = this.update(documento);
		}
		return response;
	}
	
	@Override
	public ByteArrayOutputStream ordenVentaToPdfById(Long id) {
		Documento ordenVenta = this.getById(id);
		Distribuidor distribuidor = (Distribuidor)this.empresaService.getDistribuidor();
		ByteArrayOutputStream output = this.pdfDocumento.getOrdenVentaPDFDocument((OrdenVenta)ordenVenta, distribuidor);
		return output;
	}
	
	@Override
	public PaginatorResponse<OrdenVenta> searchOrdenVenta(OrdenVentaFilter ordenVentaFilter) {
		return documentoRepository.searchOrdenVenta(ordenVentaFilter);
	}
	
}
