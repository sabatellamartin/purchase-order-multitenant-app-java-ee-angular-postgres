package com.app.distrity.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.app.distrity.util.Constants;

@Entity
@DiscriminatorValue(Constants.PROVEEDOR)
public class Proveedor extends Empresa implements Serializable {
	private static final long serialVersionUID = -4728279793528983892L;

	@JsonIgnore
    @ManyToMany
    @JoinTable(name="proveedores_articulos",
        joinColumns=@JoinColumn(name="articulo_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="proveedor_id", referencedColumnName="id"))
    private List<Articulo> articulos;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="proveedor")
    private List<OrdenCompra> ordenes;
	
	public Proveedor() {}

	public List<Articulo> getArticulos() {
		return articulos;
	}

	public void setArticulos(List<Articulo> articulos) {
		this.articulos = articulos;
	}

}
