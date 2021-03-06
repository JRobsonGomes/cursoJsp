package br.com.robson.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.robson.connection.DbException;
import br.com.robson.connection.SingleConnectionBanco;
import br.com.robson.dto.GraficoSalarioDTO;
import br.com.robson.enums.PerfilUsuario;
import br.com.robson.models.Usuario;
import br.com.robson.utils.Util;

public class UsuarioDao {

	private Connection connection;

	public UsuarioDao() {
		connection = SingleConnectionBanco.getConnection();
	}

	public GraficoSalarioDTO montarGraficoSalario(Long usuarioCadId, LocalDate dataInicial, LocalDate dataFinal) {
		PreparedStatement st = null;
		ResultSet rs = null;
		var graficoSalarioDTO = new GraficoSalarioDTO();
		
		try {
			String sql = "SELECT AVG(renda_mensal)::numeric(10,2) AS media_mensal, perfil FROM tb_usuario WHERE usuario_cad_id = ? ";
			if (dataInicial != null && dataFinal != null) {
				sql += "AND data_nascimento >= ? AND data_nascimento <= ? ";
			} 
			sql += "GROUP BY perfil ORDER BY media_mensal ASC";
			
			st = connection.prepareStatement(sql);
			st.setLong(1, usuarioCadId);
			if (dataInicial != null && dataFinal != null) {
				st.setDate(2, Date.valueOf(dataInicial));
				st.setDate(3, Date.valueOf(dataFinal));
			}
			rs = st.executeQuery();
			
			var perfis = new ArrayList<String>();	
			var salarios = new ArrayList<Double>();
			while (rs.next()) {
				salarios.add(rs.getDouble("media_mensal"));
				perfis.add(rs.getString("perfil"));
			}
			
			graficoSalarioDTO.setSalarios(salarios);
			graficoSalarioDTO.setPerfis(perfis);
			
			return graficoSalarioDTO;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			SingleConnectionBanco.closeStatement(st);
			SingleConnectionBanco.closeResultSet(rs);
		}
	}
	
	public List<Usuario> buscarUsuarioConsulta(String nome, Long usuarioCadId) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement("SELECT * FROM tb_usuario WHERE UPPER(nome) LIKE UPPER(?) AND user_admin IS FALSE AND usuario_cad_id = ? ORDER BY id");
			st.setString(1, "%" + nome + "%");
			st.setLong(2, usuarioCadId);

			rs = st.executeQuery();

			List<Usuario> list = new ArrayList<>();
			while (rs.next()) {
				Usuario obj = new Usuario();
				obj.setId(rs.getLong("id"));
				obj.setNome(rs.getString("nome"));
				obj.setEmail(rs.getString("email"));
				obj.setLogin(rs.getString("login"));
				obj.setPerfil(PerfilUsuario.valueOf(rs.getString("perfil")));

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
				obj.setUserAdmin(rs.getBoolean("user_admin"));
				obj.setUsuarioCadId(rs.getLong("usuario_cad_id"));
				obj.setPerfil(PerfilUsuario.valueOf(rs.getString("perfil")));
				obj.setSexo(rs.getString("sexo"));
				obj.setFoto(rs.getString("foto"));
				obj.setExtensaoFoto(rs.getString("extensao_foto"));
				
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
	
	public Usuario buscarUsuario(Long id, Long usuarioCadId) {
		PreparedStatement st = null;
		ResultSet rs = null;
		EnderecoDao enderecoDao = new EnderecoDao();
		TelefoneDao telefoneDao = new TelefoneDao();
		
		try {
			st = connection.prepareStatement("SELECT * FROM tb_usuario WHERE id = ? AND user_admin IS FALSE AND usuario_cad_id = ?");
			st.setLong(1, id);
			st.setLong(2, usuarioCadId);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Usuario obj = new Usuario();
				obj.setId(rs.getLong("id"));
				obj.setNome(rs.getString("nome"));
				obj.setEmail(rs.getString("email"));
				obj.setLogin(rs.getString("login"));
				obj.setSenha(rs.getString("senha"));
				obj.setPerfil(PerfilUsuario.valueOf(rs.getString("perfil")));
				obj.setSexo(rs.getString("sexo"));
				obj.setFoto(rs.getString("foto"));
				obj.setExtensaoFoto(rs.getString("extensao_foto"));
				if (rs.getString("data_nascimento") != null) obj.setDataNascimento(Util.parseStringTolocalDateFromPattern(rs.getString("data_nascimento"), "yyyy-MM-dd"));
				if (rs.getString("renda_mensal") != null) obj.setRendaMensal(Double.valueOf(rs.getString("renda_mensal")));
				obj.setEndereco(enderecoDao.buscarEndereco(id));
				obj.setTelefones(telefoneDao.buscarTodosDoUsuario(id));
				
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
		String sql = usuario.getId() == null ? "INSERT INTO tb_usuario(login, senha, nome, email, usuario_cad_id, perfil, sexo, data_nascimento, renda_mensal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
				: "UPDATE tb_usuario SET login=?, senha=?, nome=?, email=?, usuario_cad_id=?, perfil=?, sexo=?, data_nascimento=?, renda_mensal=? WHERE id = ?";

		try {
			st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, usuario.getLogin());
			st.setString(2, usuario.getSenha());
			st.setString(3, usuario.getNome());
			st.setString(4, usuario.getEmail());
			st.setLong(5, usuario.getUsuarioCadId());
			st.setString(6, usuario.getPerfil().name());
			st.setString(7, usuario.getSexo());
			st.setDate(8, usuario.getDataNascimento() != null ? Date.valueOf(usuario.getDataNascimento()) : null);
			if (usuario.getRendaMensal() != null) {
				st.setDouble(9, usuario.getRendaMensal());
			} else {
				st.setNull(9, 0);
			}
			
			if (usuario.getId() != null) st.setLong(10, usuario.getId());

			int rowsAffected = st.executeUpdate();
			connection.commit();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					Long id = rs.getLong(1);
					usuario.setId(id);
				}
				
				updateFoto(usuario);//Executa um update depois commitar a tranzacao e setar o id para buscar o usuario por id.
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
			String sql = "SELECT * FROM tb_usuario WHERE UPPER(login) = UPPER(?) AND UPPER(senha) = UPPER(?) ";

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

	public List<Usuario> buscarTodosPorDataNascimento(Long usuarioCadId, LocalDate dataInicial, LocalDate dataFinal) {
		PreparedStatement st = null;
		ResultSet rs = null;
		EnderecoDao enderecoDao = new EnderecoDao();
		TelefoneDao telefoneDao = new TelefoneDao();
		
		try {
			String sql = dataInicial != null && dataFinal != null ? "SELECT * FROM tb_usuario WHERE usuario_cad_id = ? "
					+ "AND user_admin IS FALSE AND data_nascimento >= ? AND data_nascimento <= ? ORDER BY data_nascimento DESC " 
					: "SELECT * FROM tb_usuario WHERE usuario_cad_id = ? AND user_admin IS FALSE ORDER BY id ";
				
			st = connection.prepareStatement(sql);
			st.setLong(1, usuarioCadId);
			if (dataInicial != null && dataFinal != null) {
				st.setDate(2, Date.valueOf(dataInicial));
				st.setDate(3, Date.valueOf(dataFinal));
			}
			rs = st.executeQuery();
			
			List<Usuario> list = new ArrayList<>();
			while (rs.next()) {
				Usuario obj = new Usuario();
				long id = rs.getLong("id");
				
				obj.setId(id);
				obj.setNome(rs.getString("nome"));
				obj.setEmail(rs.getString("email"));
				obj.setLogin(rs.getString("login"));
				obj.setPerfil(PerfilUsuario.valueOf(rs.getString("perfil")));
				if (rs.getString("data_nascimento") != null) obj.setDataNascimento(Util.parseStringTolocalDateFromPattern(rs.getString("data_nascimento"), "yyyy-MM-dd"));
				if (rs.getString("renda_mensal") != null) obj.setRendaMensal(Double.valueOf(rs.getString("renda_mensal")));
				obj.setEndereco(enderecoDao.buscarEndereco(id));
				obj.setTelefones(telefoneDao.buscarTodosDoUsuario(id));
				
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
	
	public List<Usuario> buscarTodos(Long usuarioCadId) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = connection.prepareStatement("SELECT * FROM tb_usuario WHERE usuario_cad_id = ? AND user_admin IS FALSE ORDER BY id ");
			st.setLong(1, usuarioCadId);
			rs = st.executeQuery();
			
			List<Usuario> list = new ArrayList<>();
			while (rs.next()) {
				Usuario obj = new Usuario();
				obj.setId(rs.getLong("id"));
				obj.setNome(rs.getString("nome"));
				obj.setEmail(rs.getString("email"));
				obj.setLogin(rs.getString("login"));
				obj.setPerfil(PerfilUsuario.valueOf(rs.getString("perfil")));
				if (rs.getString("data_nascimento") != null) obj.setDataNascimento(Util.parseStringTolocalDateFromPattern(rs.getString("data_nascimento"), "yyyy-MM-dd"));
				if (rs.getString("renda_mensal") != null) obj.setRendaMensal(Double.valueOf(rs.getString("renda_mensal")));
				
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
	
	public List<Usuario> buscarTodosPaginado(Integer offset, Long usuarioCadId) {
		PreparedStatement st = null;
		ResultSet rs = null;
		EnderecoDao enderecoDao = new EnderecoDao();
		TelefoneDao telefoneDao = new TelefoneDao();
		
		try {
			st = connection.prepareStatement("SELECT * FROM tb_usuario WHERE usuario_cad_id = ? AND user_admin IS FALSE ORDER BY id OFFSET ? LIMIT 8");
			st.setLong(1, usuarioCadId);
			st.setInt(2, offset * 8);//Multiplicar por 8 pois vem da view 0, 1, 2 ...
			rs = st.executeQuery();
			
			List<Usuario> list = new ArrayList<>();
			while (rs.next()) {
				Usuario obj = new Usuario();
				long id = rs.getLong("id");
				
				obj.setId(id);
				obj.setNome(rs.getString("nome"));
				obj.setEmail(rs.getString("email"));
				obj.setLogin(rs.getString("login"));
				obj.setPerfil(PerfilUsuario.valueOf(rs.getString("perfil")));
				obj.setEndereco(enderecoDao.buscarEndereco(id));
				obj.setTelefones(telefoneDao.buscarTodosDoUsuario(id));
				
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
	
	public int totalPaginas(Long usuarioCadId) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = connection.prepareStatement("SELECT COUNT(1) AS total FROM tb_usuario WHERE user_admin IS FALSE AND usuario_cad_id = ?");
			st.setLong(1, usuarioCadId);
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
			st = connection.prepareStatement("DELETE FROM tb_usuario WHERE Id = ? AND user_admin IS FALSE");
			
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
	
	public void updateFoto(Usuario usuario) {
		if (usuario.getId() != null && usuario.getFoto() != null && !usuario.getFoto().isBlank()) {
			PreparedStatement st = null;

			try {
				st = connection.prepareStatement("UPDATE tb_usuario SET foto = ?, extensao_foto = ? WHERE id = ?");
				st.setString(1, usuario.getFoto());
				st.setString(2, usuario.getExtensaoFoto());
				st.setLong(3, usuario.getId());

				int rows = st.executeUpdate();

				connection.commit();

				if (rows == 0) {
					throw new DbException("Erro ao atualizar foto usu?rio!");
				}
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			} finally {
				SingleConnectionBanco.closeStatement(st);
			}
		}
	}
}
