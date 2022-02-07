package br.com.robson.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.robson.connection.SingleConnectionBanco;
import br.com.robson.models.Usuario;

public class UsuarioDao {

	private Connection connection;

	public UsuarioDao() {
		connection = SingleConnectionBanco.getConnection();
	}

	public boolean validarAutenticacao(Usuario usuario) throws Exception {

		String sql = "select * from tb_usuario where upper(login) = upper(?) and upper(senha) = upper(?) ";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, usuario.getLogin());
		statement.setString(2, usuario.getSenha());

		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			return true;/* autenticado */
		}
		return false; /* nao autenticado */
	}
}
