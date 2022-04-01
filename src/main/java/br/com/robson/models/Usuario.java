package br.com.robson.models;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import br.com.robson.enums.PerfilUsuario;
import br.com.robson.utils.Util;

public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String email;
	private String login;
	private String senha;
	private Long usuarioCadId;
	private boolean userAdmin;
	private PerfilUsuario perfil;
	private String sexo;
	private String foto;
	private String extensaoFoto;
	private Endereco endereco;
	private List<Telefone> telefones;
	private String numTelefonesToString;
	
	public Usuario() {
	}

	public Usuario(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}
	
	public Usuario(Long id, String nome, String email, String login, String senha, Long usuarioCadId) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.login = login;
		this.senha = senha;
		this.usuarioCadId = usuarioCadId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getUsuarioCadId() {
		return usuarioCadId;
	}

	public void setUsuarioCadId(Long usuarioId) {
		this.usuarioCadId = usuarioId;
	}

	public boolean isUserAdmin() {
		return userAdmin;
	}

	public void setUserAdmin(boolean userAdmin) {
		this.userAdmin = userAdmin;
	}

	public PerfilUsuario getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilUsuario perfil) {
		this.perfil = perfil;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getExtensaoFoto() {
		return extensaoFoto;
	}

	public void setExtensaoFoto(String extensaoFoto) {
		this.extensaoFoto = extensaoFoto;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public String getNumTelefonesToString() {
		List<String> list = this.telefones.stream().map(t -> Util.formatTelefone(t.getNumero())).collect(Collectors.toList());
		this.numTelefonesToString = list.toString().replaceAll("\\[|\\]", "");
		return numTelefonesToString;
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
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", login=" + login + ", senha=" + senha
				+ ", usuarioCadId=" + usuarioCadId + ", userAdmin=" + userAdmin + ", perfil=" + perfil + ", sexo="
				+ sexo + ", foto=" + foto + ", extensaoFoto=" + extensaoFoto + ", endereco=" + endereco + ", telefones="
				+ telefones.stream().map(t -> Util.formatTelefone(t.getNumero())).collect(Collectors.toList()) + "]";
	}
}
