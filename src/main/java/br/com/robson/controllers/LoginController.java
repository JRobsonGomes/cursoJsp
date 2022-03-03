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

/*O chamando Controller são as servlets ou ServletLoginController*/
@WebServlet("/LoginController") /* Mapeamento de URL que vem da tela */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioDao usuarioDao = new UsuarioDao();

	public LoginController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && !acao.isBlank() && acao.equalsIgnoreCase("logout")) {
			request.getSession().invalidate();

			redirectToIndex(request, response, null);
		} else {
			doPost(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");

		try {
			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {

				Usuario usuario = new Usuario(login, senha);

				if (usuarioDao.validarAutenticacao(usuario)) { /* Simulando login */

					usuario = usuarioDao.buscarUsuario(login);
					request.getSession().setAttribute("usuarioLogado", usuario);

					if (url == null || url.equals("null")) {
						url = "/home";
					}

					RequestDispatcher redirecionar = request.getRequestDispatcher(url);
					redirecionar.forward(request, response);

				} else {
					redirectToIndex(request, response, "Informe o login e senha corretamente!");
				}

			} else {
				redirectToIndex(request, response, "Informe o login e senha corretamente!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}
	
	protected void redirectToIndex(HttpServletRequest request, HttpServletResponse response, String msg)
			throws ServletException, IOException {
		
		if (msg != null && !msg.isBlank()) request.setAttribute("msg", msg);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
}
