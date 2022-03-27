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
import br.com.robson.models.Telefone;

public class TelefoneDao {

	private Connection connection;

	public TelefoneDao() {
		connection = SingleConnectionBanco.getConnection();
	}

	public List<Telefone> buscarTodosDoUsuario(Long usuarioId) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = connection.prepareStatement("SELECT * FROM tb_telefone WHERE usuario_id = ? ORDER BY id");
			st.setLong(1, usuarioId);
			rs = st.executeQuery();
			
			List<Telefone> list = new ArrayList<>();
			while (rs.next()) {
				Telefone obj = new Telefone();
				obj.setId(rs.getLong("id"));
				obj.setNumero(rs.getString("numero"));
				obj.setUsuarioId(rs.getLong("usuario_id"));
				obj.setUsuarioCadId(rs.getLong("usuario_cad_id"));
				
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
	
	public Telefone buscar(Long id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = connection.prepareStatement("SELECT * FROM tb_telefone WHERE id = ? ");
			st.setLong(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Telefone obj = new Telefone();
				obj.setId(rs.getLong("id"));
				obj.setNumero(rs.getString("numero"));
				obj.setUsuarioCadId(rs.getLong("usuario_cad_id"));
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
	
	public void salvar(Telefone telefone) throws Exception {
		PreparedStatement st = null;
		String sql = telefone.getId() == null
				? "INSERT INTO tb_telefone(numero, usuario_id, usuario_cad_id) VALUES (?, ?, ?)"
				: "UPDATE tb_telefone SET numero=?, usuario_id=?, usuario_cad_id=? WHERE id = ?";

		try {
			st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, telefone.getNumero());
			st.setLong(2, telefone.getUsuarioId());
			st.setLong(3, telefone.getUsuarioCadId());
			if (telefone.getId() != null) st.setLong(4, telefone.getId());

			int rowsAffected = st.executeUpdate();
			connection.commit();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					Long id = rs.getLong(1);
					telefone.setId(id);
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
	
	public void deletar(Long id) {
		PreparedStatement st = null;

		try {
			st = connection.prepareStatement("DELETE FROM tb_telefone WHERE Id = ?");

			st.setLong(1, id);

			int rows = st.executeUpdate();

			connection.commit();

			if (rows == 0) {
				throw new DbException("Erro ao excluir!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			SingleConnectionBanco.closeStatement(st);
		}
	}
}
