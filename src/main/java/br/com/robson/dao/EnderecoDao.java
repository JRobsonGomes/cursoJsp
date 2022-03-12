package br.com.robson.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.robson.connection.DbException;
import br.com.robson.connection.SingleConnectionBanco;
import br.com.robson.models.Endereco;

public class EnderecoDao {

	private Connection connection;

	public EnderecoDao() {
		connection = SingleConnectionBanco.getConnection();
	}

	public Endereco salvar(Endereco endereco, Long usuarioId) {
		PreparedStatement st = null;
		Endereco enderecoUser = buscarEndereco(usuarioId);
		
		String sql = enderecoUser == null //Se nao existir endereco para o usuario informado, cria um novo se nao atualiza existente
				? "INSERT INTO tb_endereco(logradouro, bairro, cidade, uf, cep, numero, complemento, usuario_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
				: "UPDATE tb_endereco SET logradouro=?, bairro=?, cidade=?, uf=?, cep=?, numero=?, complemento=?, usuario_id=? WHERE id = ?";

		try {
			st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, endereco.getLogradouro());
			st.setString(2, endereco.getBairro());
			st.setString(3, endereco.getCidade());
			st.setString(4, endereco.getUf());
			st.setInt(5, endereco.getCep());
			st.setInt(6, endereco.getNumero());
			st.setString(7, endereco.getComplemento());
			st.setLong(8, usuarioId);
			if (enderecoUser != null) st.setLong(9, enderecoUser.getId());

			int rowsAffected = st.executeUpdate();
			connection.commit();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					Long id = rs.getLong(1);
					endereco.setId(id);
				}

				SingleConnectionBanco.closeResultSet(rs);
				
				return buscarEndereco(usuarioId);
			} else {
				throw new DbException("Erro inesperado! Nenhuma linha afetada!");
			}
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		} finally {
			SingleConnectionBanco.closeStatement(st);
		}
	}
	
	public Endereco buscarEndereco(Long usuarioId) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = connection.prepareStatement("SELECT * FROM tb_endereco WHERE usuario_id = ?");
			st.setLong(1, usuarioId);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Endereco obj = new Endereco();
				obj.setId(rs.getLong("id"));
				obj.setLogradouro(rs.getString("logradouro"));
				obj.setBairro(rs.getString("bairro"));
				obj.setCidade(rs.getString("cidade"));
				obj.setUf(rs.getString("uf"));
				obj.setCep(rs.getInt("cep"));
				obj.setNumero(rs.getInt("numero"));
				obj.setComplemento(rs.getString("complemento"));
				obj.setUsuarioId(rs.getLong("usuario_id"));
				
				return obj;
			}
			
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			SingleConnectionBanco.closeStatement(st);
			SingleConnectionBanco.closeResultSet(rs);
		}
	}
}
