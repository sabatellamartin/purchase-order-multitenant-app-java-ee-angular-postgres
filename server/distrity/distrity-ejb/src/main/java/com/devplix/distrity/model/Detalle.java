package com.app.distrity.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "detalles")
public class Detalle implements Serializable {
	private static final long serialVersionUID = 6928456304478267946L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "cantidad")
	private Integer cantidad;
	
	@Column(name = "descuento")
	private Double descuento;
	
	@Column(name = "precio")
	private Double precio;
	
	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="documento_id", nullable=false, updatable=false)
    private Documento documento;
	
    @ManyToOne(optional=false) 
    @JoinColumn(name="articulo_id", nullable=false, updatable=false)
    private Articulo articulo;

	public Detalle() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}
	
}