package br.com.robson.controllers;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

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

	private void listarUsuarios(HttpServletRequest request, HttpServletResponse response, Integer offset)
			throws ServletException, IOException {
		try {

			List<Usuario> usuarios = dao.buscarTodosPaginado(offset);
			int totalPaginas = dao.totalPaginas();
			
			request.setAttribute("usuariosList", usuarios);
			request.setAttribute("totalPaginas", totalPaginas);
			request.getRequestDispatcher("usuario/").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", "Erro ao listar usuarios: " + e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	protected void salvarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			var msg = "";
			usuario = new Usuario();

			setUsuario(request);
			boolean login = dao.validarLogin(usuario);

			if (login && usuario.getId() == null) {
				msg = "Já existe usuário com o mesmo login, informe outro login!";

			} else {
				Usuario usuarioExistente = dao.buscarUsuario(usuario.getLogin());

				if (usuario.getId() != null && usuarioExistente != null
						&& (usuario.getId() == usuarioExistente.getId())) {
					dao.salvar(usuario);
					msg = "Atualizado com sucesso!";

				} else if (login) {
					msg = "Já existe usuário com o mesmo login, informe outro login!";

				} else {
					dao.salvar(usuario);
					msg = "Salvo com sucesso!";

				}
			}

			request.setAttribute("msg", msg);
			request.setAttribute("usuario", usuario);
			listarUsuarios(request, response, 0);

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", "Erro ao salvar usuario: " + e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Integer offset = null;
			String pagina = request.getParameter("pagina");
			String acao = request.getParameter("acao");

			if (acao != null && !acao.isBlank() && acao.equalsIgnoreCase("deletar")) {
				Long id = Long.parseLong(request.getParameter("id"));

				dao.deletar(id);
				request.setAttribute("msg", "Excluido com sucesso!");
				
			} else if (acao != null && !acao.isBlank() && acao.equalsIgnoreCase("buscarUserAjax")) {
				String nomeBusca = request.getParameter("nomeBusca");

				List<Usuario> dadosJsonUser = dao.buscarUsuarioConsulta(nomeBusca);

				ObjectMapper mapper = new ObjectMapper();

				String json = mapper.writeValueAsString(dadosJsonUser);

				response.getWriter().write(json);
				return;
			}
			
			try {
				offset = Integer.parseInt(pagina);
			} catch (Exception e) {
				offset = 0;
			}

			listarUsuarios(request, response, offset);

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		salvarUsuario(request, response);
	}

	private void setUsuario(HttpServletRequest request) throws Exception {
		try {

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");

			usuario.setId(id != null && !id.isBlank() ? Long.parseLong(id) : null);
			usuario.setNome(nome);
			usuario.setEmail(email);
			usuario.setLogin(login);
			usuario.setSenha(senha);

		} catch (Exception e) {
			throw new Exception("Erro ao setar usuario!");
		}
	}
}
