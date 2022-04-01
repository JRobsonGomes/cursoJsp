package br.com.robson.models;

import java.io.Serializable;
import java.util.Objects;

import br.com.robson.utils.Util;

public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String uf;
	private Integer cep;
	private Integer numero;
	private String complemento;
	private Long usuarioId;
	
	public Endereco() {
	}
	
	public Endereco(Long id, String logradouro, String bairro, String cidade, String uf, Integer cep, Integer numero,
			String complemento) {
		this.id = id;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.cep = cep;
		this.numero = numero;
		this.complemento = complemento;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLogradouro() {
		return logradouro;
	}
	
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	public String getBairro() {
		return bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getUf() {
		return uf;
	}
	
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public Integer getCep() {
		return cep;
	}
	
	public void setCep(Integer cep) {
		this.cep = cep;
	}
	
	public Integer getNumero() {
		return numero;
	}
	
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	public String getComplemento() {
		return complemento;
	}
	
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
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
		Endereco other = (Endereco) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(logradouro);
		str.append(", ");
		str.append(numero);
		if (complemento != null && !complemento.isBlank()) str.append(", ");
		if (complemento != null && !complemento.isBlank()) str.append(complemento);
		str.append(" - ");
		str.append(bairro);
		str.append(", ");
		str.append(cidade);
		str.append(" - ");
		str.append(uf);
		str.append(", CEP: ");
		str.append(Util.formatCep(cep));
		return str.toString();
	}
}
