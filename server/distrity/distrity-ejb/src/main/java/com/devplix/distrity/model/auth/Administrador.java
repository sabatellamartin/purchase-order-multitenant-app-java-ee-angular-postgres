package com.app.distrity.model.auth;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.app.distrity.util.Constants;

@Entity
@DiscriminatorValue(Constants.ADMINISTRADOR)
public class Administrador extends Usuario implements Serializable {
	private static final long serialVersionUID = 3143835471336932604L;

	public Administrador() {}
	
}
