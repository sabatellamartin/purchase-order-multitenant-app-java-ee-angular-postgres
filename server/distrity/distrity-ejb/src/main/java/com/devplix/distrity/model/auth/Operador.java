package com.app.distrity.model.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.app.distrity.model.tenant.Tenant;
import com.app.distrity.util.Constants;

@Entity
@DiscriminatorValue(Constants.OPERADOR)
public class Operador extends Usuario implements Serializable {
	private static final long serialVersionUID = 5755826546862159414L;

	@Column(name = "rol")
	private String rol;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Tenant tenant;
	
	public Operador() {}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

}
