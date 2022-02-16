package br.com.robson.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.robson.connection.DbException;
import br.com.robson.connection.SingleConnectionBanco;
import br.com.robson.models.Usuario;

public class UsuarioDao {

	private Connection connection;

	public UsuarioDao() {
		connection = SingleConnectionBanco.getConnection();
	}

	public Usuario buscarUsuario(String login) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement("SELECT * FROM tb_usuario WHERE UPPER(login) = UPPER(?)");
			st.setString(1, login);
			rs = st.executeQuery();

			if (rs.next()) {
				Usuario obj = new Usuario();
				obj.setId(rs.getLong("id"));
				obj.setNome(rs.getString("nome"));
				obj.setEmail(rs.getString("email"));
				obj.setLogin(rs.getString("login"));
				obj.setSenha(rs.getString("senha"));
				
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
	
	public Usuario buscarUsuario(Long id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = connection.prepareStatement("SELECT * FROM tb_usuario WHERE id = ?");
			st.setLong(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Usuario obj = new Usuario();
				obj.setId(rs.getLong("id"));
				obj.setNome(rs.getString("nome"));
				obj.setEmail(rs.getString("email"));
				obj.setLogin(rs.getString("login"));
				obj.setSenha(rs.getString("senha"));
				
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

	public void salvar(Usuario usuario) throws Exception {
		PreparedStatement st = null;

		try {
			String sql = "INSERT INTO tb_usuario(login, senha, nome, email) VALUES (?, ?, ?, ?)";
			st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, usuario.getLogin());
			st.setString(2, usuario.getSenha());
			st.setString(3, usuario.getNome());
			st.setString(4, usuario.getEmail());

			int rowsAffected = st.executeUpdate();
			connection.commit();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					Long id = rs.getLong(1);
					usuario.setId(id);
				}
				SingleConnectionBanco.closeResultSet(rs);
			} else {
				throw new DbException("Erro inesperado! Nenhuma linha afetada!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			SingleConnectionBanco.closeStatement(st);
		}
	}

	public boolean validarAutenticacao(Usuario usuario) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM tb_usuario WHERE UPPER(login) = UPPER(?) and UPPER(senha) = UPPER(?) ";

			st = connection.prepareStatement(sql);

			st.setString(1, usuario.getLogin());
			st.setString(2, usuario.getSenha());

			rs = st.executeQuery();

			if (rs.next()) {
				return true;/* autenticado */
			}
			return false; /* nao autenticado */

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			SingleConnectionBanco.closeStatement(st);
			SingleConnectionBanco.closeResultSet(rs);
		}
	}
}
