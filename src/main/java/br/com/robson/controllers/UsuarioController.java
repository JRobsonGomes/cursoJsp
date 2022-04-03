package br.com.robson.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.jakartaee.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.robson.dao.EnderecoDao;
import br.com.robson.dao.UsuarioDao;
import br.com.robson.enums.PerfilUsuario;
import br.com.robson.models.Endereco;
import br.com.robson.models.Usuario;
import br.com.robson.utils.ServletGenericUtil;
import br.com.robson.utils.Util;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig
@WebServlet("/UsuarioController")
public class UsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private Endereco endereco;
	private UsuarioDao dao = new UsuarioDao();
	private EnderecoDao enderecoDao = new EnderecoDao();

	public UsuarioController() {
		super();
	}

	protected void listarUsuarios(HttpServletRequest request, HttpServletResponse response, Integer offset)
			throws ServletException, IOException {
		try {

			List<Usuario> usuarios = dao.buscarTodosPaginado(offset, super.getUserLogado(request).getId());
			int totalPaginas = dao.totalPaginas(super.getUserLogado(request).getId());
			
			request.setAttribute("usuariosList", usuarios);
			request.setAttribute("totalPaginas", totalPaginas);
			request.getRequestDispatcher("usuario/index.jsp").forward(request, response);
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
			endereco = new Endereco();

			setUsuario(request);
			boolean login = dao.validarLogin(usuario);

			if (login && usuario.getId() == null) {
				msg = "Já existe usuário com o mesmo login, informe outro login!";

			} else {
				Usuario usuarioExistente = dao.buscarUsuario(usuario.getLogin());

				if (usuario.getId() != null && usuarioExistente != null
						&& (usuario.getId() == usuarioExistente.getId())) {
					dao.salvar(usuario);
					enderecoDao.salvar(usuario.getEndereco(), usuario.getId());
					
					usuario = dao.buscarUsuario(usuario.getId(), getUserLogado(request).getId());
					msg = "Atualizado com sucesso!";

				} else if (login) {
					msg = "Já existe usuário com o mesmo login, informe outro login!";

				} else {
					dao.salvar(usuario);
					enderecoDao.salvar(usuario.getEndereco(), usuario.getId());
					
					usuario = dao.buscarUsuario(usuario.getId(), getUserLogado(request).getId());
					msg = "Salvo com sucesso!";

				}
			}

			request.setAttribute("msg", msg);
			request.setAttribute("tituloForm", "Edição");
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

			if (acao != null && !acao.isBlank() && Arrays.asList("deletar", "deletarAjax").contains(acao)) {
				Long id = Long.parseLong(request.getParameter("id"));

				dao.deletar(id);
				if (acao.equalsIgnoreCase("deletar")) {
					request.setAttribute("msg", "Excluido com sucesso!");
				} else {
					response.getWriter().write("Excluido com sucesso!");
					return; //Interrompe o processamento para baixo
				}
				
			} else if (acao != null && !acao.isBlank() && acao.equalsIgnoreCase("buscarUserAjax")) {
				String nomeBusca = request.getParameter("nomeBusca");

				List<Usuario> dadosJsonUser = dao.buscarUsuarioConsulta(nomeBusca, super.getUserLogado(request).getId());

				ObjectMapper mapper = new ObjectMapper();

				String json = mapper.writeValueAsString(dadosJsonUser);

				response.getWriter().write(json);
				return;
				
			} else if (acao != null && !acao.isBlank() && acao.equalsIgnoreCase("editar")) {
				Long id = Long.parseLong(request.getParameter("id"));

				Usuario usuario = dao.buscarUsuario(id, super.getUserLogado(request).getId());

				request.setAttribute("tituloForm", "Edição");
				request.setAttribute("usuario", usuario);
				
			} else if (acao != null && !acao.isBlank() && acao.equalsIgnoreCase("downloadFoto")) {
				Long id = Long.parseLong(request.getParameter("id"));

				Usuario usuario = dao.buscarUsuario(id, super.getUserLogado(request).getId());
				if (usuario.getFoto() != null) {
					response.setHeader("Content-Disposition",
							"attachment;filename=fotoUsuario." + usuario.getExtensaoFoto());
					response.getOutputStream().write(Base64.decodeBase64(usuario.getFoto().split("\\,")[1]));
				}
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
		String acao = request.getParameter("acao");

		if (acao != null && !acao.isBlank() && acao.equalsIgnoreCase("salvar")) {
			salvarUsuario(request, response);
		} else {
			listarUsuarios(request, response, 0);
		}
	}

	private void setUsuario(HttpServletRequest request) throws Exception {
		try {

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			String cep = request.getParameter("cep").replaceAll("[^0-9]", "");
			String logradouro = request.getParameter("logradouro");
			String bairro = request.getParameter("bairro");
			String uf = request.getParameter("uf");
			String cidade = request.getParameter("cidade");
			String numero = request.getParameter("numero");
			String complemento = request.getParameter("complemento");
			String dataNascimento = request.getParameter("dataNascimento");
			Long usuarioCadId = super.getUserLogado(request).getId();

			usuario.setId(id != null && !id.isBlank() ? Long.parseLong(id) : null);
			usuario.setNome(nome);
			usuario.setEmail(email);
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setPerfil(PerfilUsuario.valueOf(perfil));
			usuario.setUsuarioCadId(usuarioCadId);
			usuario.setSexo(sexo);
			if (dataNascimento != null && !dataNascimento.isBlank())
				usuario.setDataNascimento(Util.parseStringTolocalDateFromPattern(dataNascimento, "dd/MM/yyyy"));
			
			endereco.setCep(Integer.parseInt(cep));
			endereco.setLogradouro(logradouro);
			endereco.setBairro(bairro);
			endereco.setCidade(cidade);
			endereco.setUf(uf);
			endereco.setNumero(Integer.parseInt(numero));
			endereco.setComplemento(complemento);
			usuario.setEndereco(endereco);
			
			Part part = request.getPart("fileFoto");
			if (ServletFileUpload.isMultipartContent(request) && part.getSize() > 0) {
				String extensao = part.getContentType().split("\\/")[1];
				byte[] foto = IOUtils.toByteArray(part.getInputStream());
				String base64 = "data:image/" + extensao + ";base64," + Base64.encodeBase64String(foto);
				
				usuario.setFoto(base64);
				usuario.setExtensaoFoto(extensao);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao setar usuario!");
		}
	}
}
