package com.app.distrity.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.app.distrity.util.Constants;

@Entity
@DiscriminatorValue(Constants.REFERENTE)
public class Referente extends Persona implements Serializable {
	private static final long serialVersionUID = 3919905397646144583L;

	@Column(name = "observacion")
	private String observacion;
	
	/*
	@JsonIgnore
    @ManyToMany(mappedBy="referentes")
    private List<Empresa> empresas;
    */
	public Referente() {}

	/*
	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}*/

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
}
