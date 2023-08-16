package com.app.distrity.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.app.distrity.util.Constants;

@Entity
@DiscriminatorValue(Constants.ORDEN_VENTA)
public class OrdenVenta extends Documento implements Serializable {
	private static final long serialVersionUID = 5816629494274144694L;

	@Enumerated(EnumType.STRING)
	private Estado estado;
    
    @ManyToOne
    @JoinColumn(name="cliente_id", nullable=true)
    private Cliente cliente;
    
    @ManyToOne(optional=true) 
    @JoinColumn(name="sucursal_id", nullable=true, updatable=true)
    private Sucursal sucursal;
    
	public OrdenVenta() {}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	
}
