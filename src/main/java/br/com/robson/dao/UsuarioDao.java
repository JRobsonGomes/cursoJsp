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

		return null;
	}

	public void salvar(Usuario usuario) throws Exception {
		PreparedStatement preparedSql = null;

		try {
			String sql = "INSERT INTO tb_usuario(login, senha, nome, email)  VALUES (?, ?, ?, ?)";
			preparedSql = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			preparedSql.setString(1, usuario.getLogin());
			preparedSql.setString(2, usuario.getSenha());
			preparedSql.setString(3, usuario.getNome());
			preparedSql.setString(4, usuario.getEmail());

			int rowsAffected = preparedSql.executeUpdate();
			connection.commit();

			if (rowsAffected > 0) {
				ResultSet rs = preparedSql.getGeneratedKeys();
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
			SingleConnectionBanco.closeStatement(preparedSql);
		}
	}

	public boolean validarAutenticacao(Usuario usuario) throws Exception {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "select * from tb_usuario where upper(login) = upper(?) and upper(senha) = upper(?) ";

			statement = connection.prepareStatement(sql);

			statement.setString(1, usuario.getLogin());
			statement.setString(2, usuario.getSenha());

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return true;/* autenticado */
			}
			return false; /* nao autenticado */

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			SingleConnectionBanco.closeStatement(statement);
			SingleConnectionBanco.closeResultSet(resultSet);
		}
	}
}
