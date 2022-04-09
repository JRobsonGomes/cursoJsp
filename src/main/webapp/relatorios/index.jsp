<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="br.com.robson.utils.Util"%>

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
											<div class="col-sm-12">
												<div class="card">
													<div class="card-header">
														<h5>Relatório de usuário</h5>
													</div>
													<div class="card-block">
														<form id="formRelatorioUser" class="form-material"
															method="get"
															action="<%=request.getContextPath()%>/RelatorioController">
															<input type="hidden" name="acao" id="acao" class="form-control" value="buscar">
															<div class="d-md-flex justify-content-between align-items-end">
																<div class="form-group form-default col-md-4 px-0 pr-md-3">
																	<input type="text" name="dataInicial" id="dataInicial" class="form-control" value="${dataInicial}">
																	<span class="form-bar"></span>
																	<label class="float-label">Data Inicial</label>
																</div>
																<div class="form-group form-default col-md-4 px-0 pr-md-3">
																	<input type="text" name="dataFinal" id="dataFinal" class="form-control" value="${dataFinal}">
																	<span class="form-bar"></span> 
																	<label class="float-label">Data Final</label>
																</div>
																<div class="form-group form-default col-md-3 px-0">
																	<button class="btn btn-block btn-sm waves-effect waves-light btn-primary">Buscar</button>
																</div>
															</div>
															<span class="text-success" id="msg">${msg}</span>
														</form>
													</div>
												</div>
											</div>

											<!-- Listagem -->
											<div class="col-sm-12">
												<div class="card">
													<div class="card-header d-flex justify-content-between">
														<h5>Listagem usuários</h5>
														<c:if test="${fn:length(usuariosList) != 0}">
															<h6 class="text-success">${fn:length(usuariosList)} resultados encontrados</h6>
														</c:if>
													</div>
													<div class="card-block table-border-style pb-2">
														<div class="table-responsive">
															<table class="table table-hover table-striped">
																<thead>
																	<tr>
																		<th>#</th>
																		<th>Nome</th>
																		<th>Email</th>
																		<th>Login</th>
																		<th>Perfil</th>
																		<th>Renda mensal</th>
																		<th>Data nascimento</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${usuariosList}" var="usu">
																		<tr>
																			<th scope="row">${usu.id}</th>
																			<td>${usu.nome}</td>
																			<td>${usu.email}</td>
																			<td>${usu.login}</td>
																			<td>${usu.perfil}</td>
																			<td>
																				<c:if test="${usu.rendaMensal != null && usu.rendaMensal != 0.0}">
																					<fmt:formatNumber value = "${usu.rendaMensal}" type = "currency"/>
																				</c:if>
																			</td>
																			<td>${Util.formatLocalDateToPattern(usu.dataNascimento, 'dd/MM/yyyy')}</td>
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
