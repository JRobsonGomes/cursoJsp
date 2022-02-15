package br.com.robson.controllers;

import java.io.IOException;

import br.com.robson.dao.UsuarioDao;
import br.com.robson.models.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UsuarioController")
public class UsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private UsuarioDao dao = new UsuarioDao();

	public UsuarioController() {
		super();
	}

	protected void salvarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			usuario = new Usuario();

			setUsuario(request);
			dao.salvar(usuario);
			
			request.setAttribute("msg", "Operação realizada com sucesso!");
			request.setAttribute("usuario", usuario);
			request.getRequestDispatcher("usuario/").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", "Erro ao salvar usuario: " + e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		salvarUsuario(request, response);
	}

	private void setUsuario(HttpServletRequest request) throws Exception {
		try {

			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");

			usuario.setNome(nome);
			usuario.setEmail(email);
			usuario.setLogin(login);
			usuario.setSenha(senha);

		} catch (Exception e) {
			throw new Exception("Erro ao setar usuario!");
		}
	}
}
