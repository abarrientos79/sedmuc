package com.sedmuc.init.entitys;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
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
	private Long evaluador_id;
	
	@Column
	private Long supervisor_id;
	
	@Column
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

	public Long getEvaluador_id() {
		return evaluador_id;
	}

	public void setEvaluador_id(Long evaluador_id) {
		this.evaluador_id = evaluador_id;
	}

	public Long getSupervisor_id() {
		return supervisor_id;
	}

	public void setSupervisor_id(Long supervisor_id) {
		this.supervisor_id = supervisor_id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estado_id == null) ? 0 : estado_id.hashCode());
		result = prime * result + ((evaluador_id == null) ? 0 : evaluador_id.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nota_final == null) ? 0 : nota_final.hashCode());
		result = prime * result + ((secuencia == null) ? 0 : secuencia.hashCode());
		result = prime * result + ((supervisor_id == null) ? 0 : supervisor_id.hashCode());
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
		if (evaluador_id == null) {
			if (other.evaluador_id != null)
				return false;
		} else if (!evaluador_id.equals(other.evaluador_id))
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
		if (secuencia == null) {
			if (other.secuencia != null)
				return false;
		} else if (!secuencia.equals(other.secuencia))
			return false;
		if (supervisor_id == null) {
			if (other.supervisor_id != null)
				return false;
		} else if (!supervisor_id.equals(other.supervisor_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Evaluacion [id=" + id + ", secuencia=" + secuencia + ", evaluador_id=" + evaluador_id
				+ ", supervisor_id=" + supervisor_id + ", nota_final=" + nota_final + ", estado_id=" + estado_id + "]";
	}

	

}
