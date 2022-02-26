package br.com.robson.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.robson.connection.DbException;
import br.com.robson.connection.SingleConnectionBanco;
import br.com.robson.models.Usuario;

public class UsuarioDao {

	private Connection connection;

	public UsuarioDao() {
		connection = SingleConnectionBanco.getConnection();
	}

	public List<Usuario> buscarUsuarioConsulta(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement("SELECT * FROM tb_usuario WHERE UPPER(nome) LIKE UPPER(?) ORDER BY id");
			st.setString(1, "%" + nome + "%");

			rs = st.executeQuery();

			List<Usuario> list = new ArrayList<>();
			while (rs.next()) {
				Usuario obj = new Usuario();
				obj.setId(rs.getLong("id"));
				obj.setNome(rs.getString("nome"));
				obj.setEmail(rs.getString("email"));
				obj.setLogin(rs.getString("login"));

				list.add(obj);
			}

			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			SingleConnectionBanco.closeStatement(st);
			SingleConnectionBanco.closeResultSet(rs);
		}
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
		String sql = usuario.getId() == null ? "INSERT INTO tb_usuario(login, senha, nome, email) VALUES (?, ?, ?, ?)"
				: "UPDATE tb_usuario SET login=?, senha=?, nome=?, email=? WHERE id = ?";

		try {
			st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, usuario.getLogin());
			st.setString(2, usuario.getSenha());
			st.setString(3, usuario.getNome());
			st.setString(4, usuario.getEmail());
			if (usuario.getId() != null) st.setLong(5, usuario.getId());

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
	
	public boolean validarLogin(Usuario usuario) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT COUNT(1) > 0 AS existe FROM tb_usuario WHERE UPPER(login) = UPPER(?) ";
			
			st = connection.prepareStatement(sql);
			st.setString(1, usuario.getLogin());
			rs = st.executeQuery();
			
			rs.next();
			return rs.getBoolean("existe");
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			SingleConnectionBanco.closeStatement(st);
			SingleConnectionBanco.closeResultSet(rs);
		}
	}

	public List<Usuario> buscarTodos() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = connection.prepareStatement("SELECT * FROM tb_usuario ORDER BY nome LIMIT 8");
			rs = st.executeQuery();
			
			List<Usuario> list = new ArrayList<>();
			while (rs.next()) {
				Usuario obj = new Usuario();
				obj.setId(rs.getLong("id"));
				obj.setNome(rs.getString("nome"));
				obj.setEmail(rs.getString("email"));
				obj.setLogin(rs.getString("login"));
				
				list.add(obj);
			}
			
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			SingleConnectionBanco.closeStatement(st);
			SingleConnectionBanco.closeResultSet(rs);
		}
	}
	
	public List<Usuario> buscarTodosPaginado(Integer offset) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = connection.prepareStatement("SELECT * FROM tb_usuario ORDER BY id OFFSET ? LIMIT 8");
			st.setInt(1, offset * 8);//Multiplicar por 8 pois vem da view 0, 1, 2 ...
			rs = st.executeQuery();
			
			List<Usuario> list = new ArrayList<>();
			while (rs.next()) {
				Usuario obj = new Usuario();
				obj.setId(rs.getLong("id"));
				obj.setNome(rs.getString("nome"));
				obj.setEmail(rs.getString("email"));
				obj.setLogin(rs.getString("login"));
				
				list.add(obj);
			}
			
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			SingleConnectionBanco.closeStatement(st);
			SingleConnectionBanco.closeResultSet(rs);
		}
	}
	
	public int totalPaginas() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = connection.prepareStatement("SELECT COUNT(1) AS total FROM tb_usuario");
			rs = st.executeQuery();
			rs.next();
			
			Double cadastros = rs.getDouble("total");
			Double porPagina = 8.0; //Mesmo valor do limit do buscarTodosPaginado
			Double total = cadastros / porPagina;
			Double resto = total % 1;//Se numero por pagina for impar aqui tem que ser par e vice-versa
			
			if (resto > 0) {
				total ++;
			}
					
			return total.intValue();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			SingleConnectionBanco.closeStatement(st);
			SingleConnectionBanco.closeResultSet(rs);
		}
	}

	public void deletar(Long id) {
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("DELETE FROM tb_usuario WHERE Id = ?");
			
			st.setLong(1, id);
			
			int rows = st.executeUpdate();
			
			connection.commit();
			
			if (rows == 0) {
				throw new DbException("Erro ao excluir!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			SingleConnectionBanco.closeStatement(st);
		}
	}
}
