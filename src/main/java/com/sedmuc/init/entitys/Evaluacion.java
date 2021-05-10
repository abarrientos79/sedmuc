package com.sedmuc.init.entitys;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Evaluacion implements Serializable {

	private static final long serialVersionUID = -8568621173467578743L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long id;
	
	@Column
	@NotNull
	private Long secuencia;
	
	@Column
	private String evaluador;
	
	@Column
	private String evaluado;
	
	@Column
	@NotNull
	@Size(min=1,max=255,message="Ingrese una Obeservacion")
	private String observaciones;
	
	@Column
	@NotNull
	@Positive(message = "Ingrese una nota")
	private Long nota_final;

	@Column
	private Long estado_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Long secuencia) {
		this.secuencia = secuencia;
	}

	public String getEvaluador() {
		return evaluador;
	}

	public void setEvaluador(String evaluador) {
		this.evaluador = evaluador;
	}

	public String getEvaluado() {
		return evaluado;
	}

	public void setEvaluado(String evaluado) {
		this.evaluado = evaluado;
	}

	public Long getNota_final() {
		return nota_final;
	}

	public void setNota_final(Long nota_final) {
		this.nota_final = nota_final;
	}

	public Long getEstado_id() {
		return estado_id;
	}

	public void setEstado_id(Long estado_id) {
		this.estado_id = estado_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estado_id == null) ? 0 : estado_id.hashCode());
		result = prime * result + ((evaluado == null) ? 0 : evaluado.hashCode());
		result = prime * result + ((evaluador == null) ? 0 : evaluador.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nota_final == null) ? 0 : nota_final.hashCode());
		result = prime * result + ((observaciones == null) ? 0 : observaciones.hashCode());
		result = prime * result + ((secuencia == null) ? 0 : secuencia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evaluacion other = (Evaluacion) obj;
		if (estado_id == null) {
			if (other.estado_id != null)
				return false;
		} else if (!estado_id.equals(other.estado_id))
			return false;
		if (evaluado == null) {
			if (other.evaluado != null)
				return false;
		} else if (!evaluado.equals(other.evaluado))
			return false;
		if (evaluador == null) {
			if (other.evaluador != null)
				return false;
		} else if (!evaluador.equals(other.evaluador))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nota_final == null) {
			if (other.nota_final != null)
				return false;
		} else if (!nota_final.equals(other.nota_final))
			return false;
		if (observaciones == null) {
			if (other.observaciones != null)
				return false;
		} else if (!observaciones.equals(other.observaciones))
			return false;
		if (secuencia == null) {
			if (other.secuencia != null)
				return false;
		} else if (!secuencia.equals(other.secuencia))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Evaluacion [id=" + id + ", secuencia=" + secuencia + ", evaluador=" + evaluador + ", evaluado="
				+ evaluado + ", observaciones=" + observaciones + ", nota_final=" + nota_final + ", estado_id="
				+ estado_id + "]";
	}

	
}
