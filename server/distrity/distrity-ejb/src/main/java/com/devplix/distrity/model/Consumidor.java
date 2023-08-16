package com.app.distrity.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.app.distrity.util.Constants;

@Entity
@DiscriminatorValue(Constants.CONSUMIDOR)
public class Consumidor extends Persona implements Serializable {
	private static final long serialVersionUID = 7510457675445753884L;

	public Consumidor() {}
	
}
