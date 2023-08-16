package com.app.distrity.model;

import java.io.Serializable;
import java.util.ArrayList;
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

import com.app.distrity.model.auth.Operador;


@Entity
@DiscriminatorColumn(name="documento_tipo")
@Table(name = "documentos")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Documento implements Serializable {
	private static final long serialVersionUID = -5014166492659868380L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "vencimiento")
	private Date vencimiento;
	
	@Column(name = "numero_documento", length = 15)
	private String numeroDocumento;
    
	@Column(name = "total")
	private Double total;
	
	@Column(name = "observaciones", length = 2000)
	private String observaciones;
	
    @ManyToOne(optional=false) 
    @JoinColumn(name="moneda_id", nullable=false, updatable=true)
    private Moneda moneda;
    
    @ManyToOne(optional = false)
    @JoinColumn(name="usuario_id")
    private Operador usuario;
 
	@OneToMany(mappedBy="documento", cascade=CascadeType.ALL, fetch = FetchType.LAZY , orphanRemoval = true)
    private List<Detalle> detalles = new ArrayList<Detalle>();
    
	public Documento() {}
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	public List<Detalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<Detalle> detalles) {
		this.detalles = detalles;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
	/* Utility method */
    public void addDetalle(Detalle detalle) {
        detalles.add(detalle);
        detalle.setDocumento(this);
    }
 
    /* Utility method */
    public void removeDetalle(Detalle detalle) {
        detalles.remove(detalle);
        detalle.setDocumento(null);
    }

	public Operador getUsuario() {
		return usuario;
	}

	public void setUsuario(Operador usuario) {
		this.usuario = usuario;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

}