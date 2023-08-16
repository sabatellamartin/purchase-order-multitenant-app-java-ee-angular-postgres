package com.app.distrity.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.app.distrity.util.Constants;

@Entity
@DiscriminatorValue(Constants.ORDEN_COMPRA)
public class OrdenCompra extends Documento implements Serializable {
	private static final long serialVersionUID = 5934022853173153948L;

    @ManyToOne
    @JoinColumn(name="proveedor_id", nullable=true)
    private Proveedor proveedor;
    
	public OrdenCompra() {}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	
}
