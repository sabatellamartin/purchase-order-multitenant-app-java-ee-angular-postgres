package com.app.distrity.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.app.distrity.model.auth.Operador;
import com.app.distrity.util.Constants;

@Entity
@DiscriminatorValue(Constants.EMPLEADO)
public class Empleado extends Persona implements Serializable {
	private static final long serialVersionUID = 4930525586483280015L;

	@Column(name = "cargo")
	private String cargo;

	@Column(name = "tarea")
	private String tarea;
	
	@OneToOne
    @JoinColumn(name="zona_id")
    private Zona zona;
    
	@OneToOne
    @JoinColumn(name="operador_id")
    private Operador operador;
    
	public Empleado() {}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getTarea() {
		return tarea;
	}

	public void setTarea(String tarea) {
		this.tarea = tarea;
	}

	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	public Operador getOperador() {
		return operador;
	}

	public void setOperador(Operador operador) {
		this.operador = operador;
	}

}
