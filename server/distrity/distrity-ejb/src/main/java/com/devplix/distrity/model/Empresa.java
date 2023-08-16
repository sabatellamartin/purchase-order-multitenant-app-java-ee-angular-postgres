package com.app.distrity.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@DiscriminatorColumn(name="empresa_tipo")
@Table(name = "empresas")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Empresa implements Serializable {
	private static final long serialVersionUID = -2066213483555912617L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "rut")
	private String rut;
	
	@Column(name = "razon_social", unique=true)
	private String razonSocial;
	
	@Column(name = "nombre_comercial", unique=true)
	private String nombreComercial;
	
	@Column(name = "rubro")
	private String rubro;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "web")
	private String web;

	@Column(name = "email")
	private String email;
	
	@Column(name = "alta")
	private Date alta;
	
	@Column(name = "baja")
	private Date baja;
	
	@Column(name = "referente")
	private String referente;
	
	@Column(name = "observacion",length=3000)
	private String observacion;
	
	@Column(name = "horario")
	private String horario;
	
    @ManyToOne(optional=true) 
    @JoinColumn(name="tipo_empresa_id", nullable=true, updatable=true)
    private TipoEmpresa tipoEmpresa;

    @ManyToOne(optional=true) 
    @JoinColumn(name="calificacion_id", nullable=true, updatable=true)
    private Calificacion calificacion;
    
    @OneToMany(mappedBy="empresa", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Sucursal> sucursales;
	
	public Empresa() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getRubro() {
		return rubro;
	}

	public void setRubro(String rubro) {
		this.rubro = rubro;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getReferente() {
		return referente;
	}

	public void setReferente(String referente) {
		this.referente = referente;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public Calificacion getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Calificacion calificacion) {
		this.calificacion = calificacion;
	}

	public List<Sucursal> getSucursales() {
		return sucursales;
	}

	public void setSucursales(List<Sucursal> sucursales) {
		this.sucursales = sucursales;
	}

    public void addSucursal(Sucursal sucursal) {
        this.sucursales.add(sucursal);
        sucursal.setEmpresa(this);
    }
 
    public void removeSucursal(Sucursal sucursal) {
    	this.sucursales.remove(sucursal);
        sucursal.setEmpresa(null);
    }

}
