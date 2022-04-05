<%@ page import="br.com.robson.enums.PerfilUsuario"%>
<%@ page import="br.com.robson.models.Telefone"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="br.com.robson.utils.Util"%>

<%
	String titulo = (String) request.getAttribute("tituloForm");
%>

<!DOCTYPE html>
<html lang="pt-BR">

<jsp:include page="../fragments/head.jsp"></jsp:include>


<body>
	<!-- Pre-loader start -->

	<jsp:include page="../fragments/theme-loader.jsp"></jsp:include>

	<!-- Pre-loader end -->
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<jsp:include page="../fragments/navbar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<jsp:include page="../fragments/sidebar.jsp"></jsp:include>

					<div class="pcoded-content">
						<!-- Page-header start -->

						<jsp:include page="../fragments/page-header.jsp"></jsp:include>

						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">
										<div class="row justify-content-center">
											<div class="col-sm-12 mb-3">
												<a href="<%=request.getContextPath()%>/UsuarioController?acao=editar&id=${usuario.id}"
													class="btn waves-effect waves-light btn-inverse">
													<i class="icofont icofont-arrow-left"></i>Voltar
												</a>
											</div>
											<div class="col-sm-12 col-md-6">
												<div class="card">
													<div class="card-header">
														<h5><%=titulo = titulo != null ? titulo : "Cadastro"%> de telefone</h5>
													</div>
													<div class="card-block">
													<form id="formTelefone" class="form-material needs-validation"
															method="post"
															action="<%=request.getContextPath()%>/TelefoneController?acao=salvar"
															novalidate>
															<div class="d-md-flex">
																<div class="form-group form-static-label col-md-2 px-0 pr-md-3">
																	<input type="text" name="usuarioId" id="usuarioId" class="form-control" value="${usuario.id}" readonly="readonly">
																	<span class="form-bar"></span>
																	<label class="float-label">Usuario Id</label>
																</div>
	
																<div class="form-group form-static-label form-default col-md-10 px-0">
																	<input type="text" name="nome" id="nome" class="form-control" value="${usuario.nome}" readonly="readonly">
																	<span class="form-bar"></span>
																	<label class="float-label">Nome</label>
																</div>
															</div>
															
															<input type="hidden" name="id" id="id" class="form-control" value="${telefone.id}" readonly="readonly">
															<div class="form-group form-default">
																<input type="text" name="numeroTel" id="numeroTel" class="form-control" value="${telefone.numero}" maxlength="15" required>
																<span class="form-bar"></span>
																<label class="float-label">Número</label>
															</div>

															<div class="form-group form-default m-t-20">
																<button type="button"
																	class="btn waves-effect waves-light btn-primary"
																	id="btnNovoTel">Novo
																</button>
																<button class="btn waves-effect waves-light btn-success">Salvar</button>
															</div>
															<span class="text-success" id="msg">${msg}</span>
														</form>
													</div>
												</div>
											</div>

											<!-- Listagem -->
											<div class="col-sm-12 col-md-6">
												<div class="card">
													<div class="card-header">
														<h5>Telefones do usuários</h5>
													</div>
													<div class="card-block table-border-style pb-2">
														<div class="table-responsive">
															<table class="table table-hover table-striped">
																<thead>
																	<tr>
																		<th>#</th>
																		<th>Número</th>
																		<th>Ação</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${usuario.telefones}" var="telefone">
																		<tr>
																			<th scope="row">
																				<c:out value="${telefone.id}"></c:out>
																			</th>
																			<td>
																				<c:out value="${Util.formatTelefone(telefone.numero)}"></c:out>
																			</td>
																			<td>
																				<a href="<%= request.getContextPath() %>/TelefoneController?acao=editar&id=${telefone.id}&usuarioId=${telefone.usuarioId}"
																					class="btn btn-sm btn-info">
																					Editar
																				</a>
																				<a href="javascript: confirmarExclusaoTelefone(${telefone.id}, '${telefone.usuarioId}', '${Util.formatTelefone(telefone.numero)}')"
																					class="btn btn-sm btn-danger">
																					Excluir
																				</a>
																			</td>
																		</tr>
																	</c:forEach>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../fragments/javascrip-file.jsp"></jsp:include>
</body>

</html>
