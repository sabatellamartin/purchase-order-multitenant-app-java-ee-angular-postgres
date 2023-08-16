package com.app.distrity.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.app.distrity.util.Constants;

@Entity
@DiscriminatorValue(Constants.DISTRIBUIDOR)
public class Distribuidor extends Empresa implements Serializable {
	private static final long serialVersionUID = 4321794842816005385L;

	public Distribuidor() {}

}
