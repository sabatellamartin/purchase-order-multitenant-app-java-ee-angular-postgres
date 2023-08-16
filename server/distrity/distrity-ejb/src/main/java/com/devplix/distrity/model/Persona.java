package com.app.distrity.model;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@DiscriminatorColumn(name="persona_tipo")
@Table(name = "personas")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Persona implements Serializable {
	private static final long serialVersionUID = 18225977543483740L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "numero_documento", length = 20)
	private String numeroDocumento;

	@Column(name = "primer_nombre", length = 25)
	private String primerNombre;

	@Column(name = "segundo_nombre", length = 25)
	private String segundoNombre;

	@Column(name = "primer_apellido", length = 25)
	private String primerApellido;

	@Column(name = "segundo_apellido", length = 25)
	private String segundoApellido;

	@Column(name = "nombre_busqueda", length = 100, insertable = false, updatable = false)
	private String nombreBusqueda;
	
	@Column(name = "nombre_completo", length = 100, insertable = false, updatable = false)
	private String nombreCompleto;
	
	@Column(name = "direccion_email", length = 100)
	private String direccionEmail;
	
	@Column(name = "telefono")
	private String telefono;

	@Column(name = "fecha_nacimiento")
	private Date fechaNacimiento;
	
	@Column(name = "fecha_fallecimiento")
	private Date fechaFallecimiento;
    
    @ManyToOne(optional=true) 
    @JoinColumn(name="tipo_documento_id", nullable=true, updatable=true)
    private TipoDocumento tipoDocumento;
	
	@OneToOne
    @JoinColumn(name="direccion_id")
    private Direccion direccion;
   
	public Persona() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getNombreBusqueda() {
		StringBuilder sb = new StringBuilder();
		if(primerNombre != null) sb.append(primerNombre);
		if(primerApellido != null) sb.append((sb.length() > 0 ? " " : "") + primerApellido);
		nombreBusqueda = sb.toString();
		return nombreBusqueda;
	}

	public void setNombreBusqueda(String nombreBusqueda) {
		this.nombreBusqueda = nombreBusqueda;
	}
	
	public String getNombreCompleto() {
		StringBuilder sb = new StringBuilder();
		if(primerNombre != null) sb.append(primerNombre);
		if(segundoNombre != null) sb.append((sb.length() > 0 ? " " : "") + segundoNombre);
		if(primerApellido != null) sb.append((sb.length() > 0 ? " " : "") + primerApellido);
		if(segundoApellido != null) sb.append((sb.length() > 0 ? " " : "") + segundoApellido);
		nombreCompleto = sb.toString();
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getDireccionEmail() {
		return direccionEmail;
	}

	public void setDireccionEmail(String direccionEmail) {
		this.direccionEmail = direccionEmail;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaFallecimiento() {
		return fechaFallecimiento;
	}

	public void setFechaFallecimiento(Date fechaFallecimiento) {
		this.fechaFallecimiento = fechaFallecimiento;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

}