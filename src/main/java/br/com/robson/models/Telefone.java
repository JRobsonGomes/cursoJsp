package br.com.robson.models;

import java.io.Serializable;
import java.util.Objects;

public class Telefone implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String numero;
	private Long usuarioId;
	private Long usuarioCadId;
	
	public Telefone() {
		
	}
	
	public Telefone(Long id, String numero, Long usuarioId, Long usuarioCadId) {
		this.id = id;
		this.numero = numero;
		this.usuarioId = usuarioId;
		this.usuarioCadId = usuarioCadId;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public Long getUsuarioId() {
		return usuarioId;
	}
	
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	public Long getUsuarioCadId() {
		return usuarioCadId;
	}
	
	public void setUsuarioCadId(Long usuarioCadId) {
		this.usuarioCadId = usuarioCadId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Telefone other = (Telefone) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Telefone [id=" + id + ", numero=" + numero + ", usuarioId=" + usuarioId + ", usuarioCadId="
				+ usuarioCadId + "]";
	}

}
