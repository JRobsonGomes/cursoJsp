package br.com.robson.controllers;

import java.io.IOException;

import br.com.robson.dao.TelefoneDao;
import br.com.robson.dao.UsuarioDao;
import br.com.robson.models.Telefone;
import br.com.robson.models.Usuario;
import br.com.robson.utils.ServletGenericUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/TelefoneController")
public class TelefoneController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
	
	private Telefone telefone;
	private TelefoneDao telefoneDao = new TelefoneDao();
	private UsuarioDao dao = new UsuarioDao();
	
    public TelefoneController() {
    }
    
	protected void listarTelefones(HttpServletRequest request, HttpServletResponse response, Long usuarioId)
			throws ServletException, IOException {
		try {

			Usuario usuario = dao.buscarUsuario(usuarioId, getUserLogado(request).getId());

			request.setAttribute("usuario", usuario);
			request.getRequestDispatcher("telefone/index.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", "Erro ao listar telefones: " + e.getMessage());
			redirecionar.forward(request, response);
		}
	}

    protected void salvarTelefone(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			var msg = "";
			telefone = new Telefone();
			setTelefone(request);

			if (telefone.getId() == null && telefoneDao.validarTelefone(telefone.getNumero(), telefone.getUsuarioId())) {
				msg = "Não é permitido cadastrar o mesmo número duas vezes para o mesmo usuário, informe outro número!";
				
			} else {
				msg = telefone.getId() != null ? "Atualizado com sucesso!" : "Salvo com sucesso!";
				telefoneDao.salvar(telefone);
				
				request.setAttribute("tituloForm", "Edição");
				request.setAttribute("telefone", telefone);
			}
			
			request.setAttribute("msg", msg);
			listarTelefones(request, response, telefone.getUsuarioId());

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", "Erro ao salvar telefone: " + e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String userId = request.getParameter("usuarioId");
			String acao = request.getParameter("acao");
			String id = request.getParameter("id");
			
			if (userId != null && !userId.isBlank()) {
				Long usuarioId = Long.parseLong(userId);
				
				if (acao != null && !acao.isBlank() && acao.equals("deletar")) {

					telefoneDao.deletar(Long.parseLong(id));
					request.setAttribute("msg", "Excluido com sucesso!");
					
				} else if (acao != null && !acao.isBlank() && acao.equals("editar")) {
					
					Telefone telefone = telefoneDao.buscar(Long.parseLong(id));
					
					request.setAttribute("tituloForm", "Edição");
					request.setAttribute("telefone", telefone);
				}
				
				listarTelefones(request, response, usuarioId);
			} else {
				new UsuarioController().listarUsuarios(request, response, 0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");

		if (acao != null && !acao.isBlank() && acao.equalsIgnoreCase("salvar")) {
			salvarTelefone(request, response);
			
		} else {
			new UsuarioController().listarUsuarios(request, response, 0);
		}
	}

	private void setTelefone(HttpServletRequest request) throws Exception {
		try {

			String id = request.getParameter("id");
			String numero = request.getParameter("numeroTel").replaceAll("[^0-9]", "");
			String usuarioId = request.getParameter("usuarioId");
			Long usuarioCadId = super.getUserLogado(request).getId();

			telefone.setId(id != null && !id.isBlank() ? Long.parseLong(id) : null);
			telefone.setNumero(Long.parseLong(numero));
			telefone.setUsuarioId(Long.parseLong(usuarioId));
			telefone.setUsuarioCadId(usuarioCadId);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao setar telefone!");
		}
	}
}
