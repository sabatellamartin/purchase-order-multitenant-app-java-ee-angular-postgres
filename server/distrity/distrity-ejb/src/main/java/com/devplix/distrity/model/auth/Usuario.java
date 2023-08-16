package com.app.distrity.model.auth;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@DiscriminatorColumn(name="usuario_tipo")
@Table(name = "usuarios", schema = "master")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Usuario implements Serializable {
	private static final long serialVersionUID = -6307281401781842912L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "email", unique=true)
	private String email;

	@Column(name = "password")
	private String password;
	
	@Column(name = "alta")
	private Date alta;

	@Column(name = "baja")
	private Date baja;
	
	@Column(name = "bloqueado")
	private Date bloqueado;

	public Usuario() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Date getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Date bloqueado) {
		this.bloqueado = bloqueado;
	}
	
}
