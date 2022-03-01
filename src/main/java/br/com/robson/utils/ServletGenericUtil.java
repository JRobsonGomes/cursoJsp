package br.com.robson.utils;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;

import br.com.robson.dao.UsuarioDao;
import br.com.robson.models.Usuario;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

public class ServletGenericUtil extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 1L;

	private UsuarioDao dao;

	public ServletGenericUtil() {
		dao = new UsuarioDao();
	}

	public Usuario getUserLogado(HttpServletRequest request) throws SQLException, ParseException {
		Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");
		return dao.buscarUsuario(usuarioLogado.getLogin());
	}
}
