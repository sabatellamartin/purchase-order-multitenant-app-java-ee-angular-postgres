package com.app.distrity.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.app.distrity.util.Constants;

@Entity
@DiscriminatorValue(Constants.CLIENTE)
public class Cliente extends Empresa implements Serializable {
	private static final long serialVersionUID = -1066092575528980723L;

	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL, mappedBy="cliente")
    private List<OrdenVenta> ordenes;
   
	public Cliente() {}

	public List<OrdenVenta> getOrdenes() {
		return ordenes;
	}

	public void setOrdenes(List<OrdenVenta> ordenes) {
		this.ordenes = ordenes;
	}

}
