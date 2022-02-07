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
		doPost(request, response);
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

					request.getSession().setAttribute("usuario", usuario);

					if (url == null || url.equals("null")) {
						url = "/home";
					}

					RequestDispatcher redirecionar = request.getRequestDispatcher(url);
					redirecionar.forward(request, response);

				} else {
					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "Informe o login e senha corretamente!");
					redirecionar.forward(request, response);
				}

			} else {
				RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
				request.setAttribute("msg", "Informe o login e senha corretamente!");
				redirecionar.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}
}
