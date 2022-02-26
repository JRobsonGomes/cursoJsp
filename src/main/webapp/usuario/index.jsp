<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
											<div class="col-sm-12 col-md-4">
												<div class="card">
													<div class="card-header">
														<h5>Cadastro de usuário</h5>
														<!--<span>Add class of <code>.form-control</code> with <code>&lt;input&gt;</code> tag</span>-->
													</div>
													<div class="card-block">
														<form id="formUser" class="form-material needs-validation"
															method="post"
															action="<%=request.getContextPath()%>/UsuarioController"
															novalidate>
															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id"
																	class="form-control" value="${usuario.id}" required
																	readonly="readonly"> <span class="form-bar"></span>
																<label class="float-label">Id</label>
															</div>
															<div class="form-group form-default">
																<input type="text" name="nome" id="nome"
																	class="form-control" value="${usuario.nome}" required>
																<span class="form-bar"></span> <label
																	class="float-label">Nome</label>
															</div>
															<div class="form-group form-default">
																<input type="email" name="email" id="email"
																	class="form-control" value="${usuario.email}" required>
																<span class="form-bar"></span> <label
																	class="float-label">Email (exa@gmail.com)</label>
															</div>
															<div class="form-group form-default">
																<input type="text" name="login" id="login"
																	class="form-control" value="${usuario.login}" required>
																<span class="form-bar"></span> <label
																	class="float-label">Login</label>
															</div>
															<div class="form-group form-default">
																<input type="password" name="senha" id="senha"
																	class="form-control" value="${usuario.senha}" required
																	maxlength="8"> <span class="form-bar"></span> <label
																	class="float-label">Senha</label>
															</div>
															<div class="form-group form-default m-t-50">
																<button type="button"
																	class="btn waves-effect waves-light btn-primary"
																	id="btnNovo">Novo</button>
																<button class="btn waves-effect waves-light btn-success">Salvar</button>
																<button type="button" class="btn btn-info btn-outline-info"
																	data-toggle="modal" data-target="#pesquisaUsuario">
																	Persquisar</button>
															</div>
															<span class="text-success" id="msg">${msg}</span>
														</form>
													</div>
												</div>
											</div>

											<!-- Listagem -->
											<div class="col-sm-12 col-md-8">
												<div class="card">
													<div class="card-header">
														<h5>Listagem usuários</h5>
														<div class="card-header-right">
															<ul class="list-unstyled card-option">
																<li><i class="fa fa fa-wrench open-card-option"></i></li>
																<li><i class="fa fa-window-maximize full-card"></i></li>
																<li><i class="fa fa-minus minimize-card"></i></li>
																<li><i class="fa fa-refresh reload-card"></i></li>
																<li><i class="fa fa-trash close-card"></i></li>
															</ul>
														</div>
													</div>
													<div class="card-block table-border-style pb-2">
														<div class="table-responsive">
															<table class="table table-sm table-hover table-striped">
																<thead>
																	<tr>
																		<th>#</th>
																		<th>Nome</th>
																		<th>Email</th>
																		<th>Login</th>
																		<th>Ação</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${usuariosList}" var="usu">
																		<tr>
																			<th scope="row"><c:out value="${usu.id}"></c:out>
																			</th>
																			<td><c:out value="${usu.nome}"></c:out></td>
																			<td><c:out value="${usu.email}"></c:out></td>
																			<td><c:out value="${usu.login}"></c:out></td>
																			<td><a href="?acao=editar&id=${usu.id}"
																				class="btn btn-sm btn-info">Editar</a> <a
																				href="javascript: confirmar(${usu.id}, '${usu.nome}')"
																				class="btn btn-sm btn-danger">Excluir</a></td>
																		</tr>
																	</c:forEach>
																</tbody>
															</table>
														</div>
													</div>
													<div class="card-footer pt-0">
														<ul class="pagination justify-content-center">
															<%
															int totalPaginas = (int) request.getAttribute("totalPaginas");
															for (int p = 0; p < totalPaginas; p++) {
																String url = request.getContextPath() + "/UsuarioController?pagina=" + p;
																out.print("<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "\">" + (p + 1) + "</a></li>");
															}
															%>
														</ul>
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

	<!-- Modal -->
	<div class="modal fade" id="pesquisaUsuario" tabindex="-1" role="dialog"
		aria-labelledby="pesquisaUsuarioLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="pesquisaUsuarioLabel">Pesquisa de usuários</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="input-group mb-3 col-sm-6">
						<input type="search" class="form-control" placeholder="Digite o nome" id="nomeBusca">
						<div class="input-group-append">
							<button class="btn btn-outline-success" type="button" id="btnBusca">Buscar</button>
						</div>
					</div>
					<div class="input-group mb-3 col-sm-6">
						<span class="text-success" id="msgModal"></span>
					</div>
					<div class="table-responsive">
						<table class="table table-sm table-hover table-striped"	id="consultaUsuariosResult">
							<thead>
								<tr>
									<th>#</th>
									<th>Nome</th>
									<th>Email</th>
									<th>Login</th>
									<th>Ação</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
					<span id="totalResultados"></span>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal" id="btnFecharModal">Fechar</button>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../fragments/javascrip-file.jsp"></jsp:include>
</body>

</html>
