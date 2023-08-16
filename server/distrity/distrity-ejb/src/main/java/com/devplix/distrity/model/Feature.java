package com.app.distrity.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "features")
public class Feature implements Serializable {
	private static final long serialVersionUID = -6666214848359253920L;

	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "geometry")
	private String geometry;
	
	public Feature() {}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getGeometry() {
		return geometry;
	}

	@Transient
	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

}
