package br.com.robson.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.robson.dao.UsuarioDao;
import br.com.robson.dto.GraficoSalarioDTO;
import br.com.robson.models.Usuario;
import br.com.robson.utils.ReportUtil;
import br.com.robson.utils.ServletGenericUtil;
import br.com.robson.utils.Util;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RelatorioController")
public class RelatorioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
	
	private UsuarioDao dao = new UsuarioDao();
	private List<Usuario> usuarios = new ArrayList<Usuario>();
       
    public RelatorioController() {
        super();
    }

    protected void listarUsuarios(HttpServletRequest request, HttpServletResponse response, String dataInicial, String dataFinal)
			throws ServletException, IOException {
		try {

			LocalDate dtInicial = Util.parseStringTolocalDateFromPattern(dataInicial, "dd/MM/yyyy");
			LocalDate dtFinal= Util.parseStringTolocalDateFromPattern(dataFinal, "dd/MM/yyyy");
			usuarios = dao.buscarTodosPorDataNascimento(super.getUserLogado(request).getId(), dtInicial, dtFinal);
			
			request.setAttribute("usuariosList", usuarios);
			request.getRequestDispatcher("relatorios/index.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", "Erro ao listar usuarios: " + e.getMessage());
			redirecionar.forward(request, response);
		}
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			String dataInicial = request.getParameter("dataInicial");
			String dataFinal = request.getParameter("dataFinal");
			
			if (acao != null && !acao.isBlank() && acao.equalsIgnoreCase("buscarRelatorio")) {

				request.setAttribute("dataInicial", dataInicial);
				request.setAttribute("dataFinal", dataFinal);
				listarUsuarios(request, response, dataInicial, dataFinal);
				
			} else if(acao != null && !acao.isBlank() && acao.equals("imprimirRelatorio")) {
	    		if (usuarios.size() != 0) {
					
	    			Map<String, Object> params = new HashMap<>();
		    		params.put("PARAM_SUB_REPORT", request.getServletContext().getResource(ReportUtil.getReportsPath()).getFile());
	    			byte[] relatorio = ReportUtil.gerarRelatorioPDF(usuarios, "RelatorioUsuario", params, request.getServletContext());
	    			response.setHeader("Content-Disposition", "attachment;filename=RelatorioUsuarios.pdf");
	    			response.getOutputStream().write(relatorio);
	    			
	    			return;
				} else {
					throw new Exception("Lista de usuarios vazia!");
				}
	    	} else if(acao != null && !acao.isBlank() && acao.equals("graficoSalario")) {
	    		GraficoSalarioDTO dtoGraficoSalario = dao.montarGraficoSalario(super.getUserLogado(request).getId());

	    		response.getWriter().write(new ObjectMapper().writeValueAsString(dtoGraficoSalario));
				return;
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
