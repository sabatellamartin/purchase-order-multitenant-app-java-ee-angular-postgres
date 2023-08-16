package com.app.distrity.model.tenant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.app.distrity.model.auth.Operador;

@Entity
@Table(name = "tenants", schema = "master")
public class Tenant implements Serializable {
	private static final long serialVersionUID = 1938587855916911253L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre", unique=true)
	private String nombre;
	
	@Column(name = "alta", nullable=false)
	private Date alta;

	@Column(name = "baja")
	private Date baja;
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="tenant")
	private List<Operador> operadores = new ArrayList<Operador>();
	
	public Tenant() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getAlta() {
		return alta;
	}

	public void setAlta(Date alta) {
		this.alta = alta;
	}

	public Date getBaja() {
		return baja;
	}

	public void setBaja(Date baja) {
		this.baja = baja;
	}

	public List<Operador> getOperadores() {
		return operadores;
	}

	public void setOperadores(List<Operador> operadores) {
		this.operadores = operadores;
	}

}
