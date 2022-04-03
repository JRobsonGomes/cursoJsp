<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="br.com.robson.enums.PerfilUsuario"%>
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
											<div class="col-sm-12">
												<div class="card">
													<div class="card-header">
														<h5><%=titulo = titulo != null ? titulo : "Cadastro"%> de usuário</h5>
													</div>
													<div class="card-block">
														<form id="formUser" class="form-material needs-validation"
															method="post"
															action="<%=request.getContextPath()%>/UsuarioController?acao=salvar"
															enctype="multipart/form-data"
															novalidate>
															<div class="form-group form-default d-md-flex mb-5">
																<c:if test="${usuario.foto != null && usuario.foto != ''}">
																	<a href="<%= request.getContextPath() %>/UsuarioController?acao=downloadFoto&id=${usuario.id}">
																		<img id="fotoBase64" class="img-70 img-radius mr-md-4"
																		alt="fotoUser" src="${usuario.foto}">
																	</a>
																</c:if>
																<c:if test="${usuario.foto == null || usuario.foto == ''}">
																	<img id="fotoBase64" class="img-70 img-radius mr-md-4" alt="fotoUser"
																		src="<%=request.getContextPath()%>/assets/images/avatar-blank.jpg">
																</c:if>
																<div class="align-items-md-center">
																	<input type="file" name="fileFoto" id="fileFoto"
																		class="form-control-file m-t-20" value=""
																		accept="image/*">
																	<span class="text-danger" id="fileMsg"></span>
																</div>
															</div>
															<div class="d-md-flex">
																<div class="col-sm-12 col-md-6 px-0 pr-md-3">
																	<div class="d-md-flex">
																		<div class="form-group form-static-label col-md-1 px-0 pl-md-0">
																			<input type="text" name="id" id="id"
																				class="form-control" value="${usuario.id}" required
																				readonly="readonly"> <span class="form-bar"></span>
																			<label class="float-label">Id</label>
																		</div>
																		<div class="form-group form-static-label col-md-5 px-0 px-md-3">
																			<select name="perfil" class="form-control" required>
																				<option value="" disabled="disabled" selected="selected">
																					Selecione o Perfil
																				</option>
																				<option value="<%= PerfilUsuario.ADMIN %>"
																					${usuario.perfil == 'ADMIN' ? 'selected="selected"' : ''}>
																					Admin
																				</option>
																				<option value="<%=PerfilUsuario.AUXILIAR%>"
																					${usuario.perfil == 'AUXILIAR' ? 'selected="selected"' : ''}>
																					Auxiliar
																				</option>
																				<option value="<%=PerfilUsuario.GERENTE%>"
																					${usuario.perfil == 'GERENTE' ? 'selected="selected"' : ''}>
																					Gerente
																				</option>
																				<option value="<%=PerfilUsuario.SECRETARIA%>"
																					${usuario.perfil == 'SECRETARIA' ? 'selected="selected"' : ''}>
																					Secretaria
																				</option>
																			</select>
																			<label class="float-label" style="color: #455a64">
																				Perfil
																			</label>
																		</div>
																		<div class="form-group col-md-6 px-0 d-flex align-items-end justify-content-around"
																			style="border-bottom: 1px solid #ccc;">
																			<div class="pr-2 d-flex align-items-baseline">
																				<input type="radio" name="sexo" value="MASCULINO"
																					${usuario.sexo == 'MASCULINO' ? 'checked="checked"' : ''}
																					class="form-radio mr-1" value="${usuario.sexo}" required>
																				<label class="static-label">Masculino</label>
																			</div>
																			<div class="pl-2 d-flex align-items-baseline">
																				<input type="radio" name="sexo" value="FEMININO"
																					${usuario.sexo == 'FEMININO' ? 'checked="checked"' : ''}
																					class="form-radio mr-1" value="${usuario.sexo}" required>
																				<label class="static-label">Feminino</label>
																			</div>
																		</div>
																	</div>
																	<div class="d-md-flex">
																		<div class="form-group form-default col-md-8 px-0 pr-md-3">
																			<input type="text" name="nome" id="nome" class="form-control" value="${usuario.nome}" maxlength="60" required>
																			<span class="form-bar"></span>
																			<label class="float-label">Nome</label>
																		</div>
																		<div class="form-group form-default col-md-4 px-0">
																			<input type="text" name="dataNascimento" id="dataNascimento" class="form-control" value="${Util.formatLocalDateToPattern(usuario.dataNascimento, 'dd/MM/yyyy')}">
																			<span class="form-bar"></span> 
																			<label class="float-label">Data Nascimento</label>
																		</div>
																	</div>
																	<div class="d-md-flex">
																		<div class="form-group form-default col-md-8 px-0 pr-md-3">
																			<input type="email" name="email" id="email" class="form-control" value="${usuario.email}" maxlength="60" required>
																			<span class="form-bar"></span>
																			<label class="float-label">Email (exa@gmail.com)</label>
																		</div>
																		<div class="form-group form-default col-md-4 px-0">
																			<%-- <fmt:setLocale value = "en_US"/> Caso queira mudar o local de formatacao--%>
																			<input type="text" name="rendaMensal" id="rendaMensal" class="form-control" value="<fmt:formatNumber value = "${usuario.rendaMensal}" type = "currency"/>"
																				data-thousands="." data-decimal="," data-prefix="R$ ">
																			<span class="form-bar"></span>
																			<label class="float-label">Renda Mensal</label>
																		</div>
																	</div>
																	<div class="form-group form-default">
																		<input type="text" name="login" id="login"
																			class="form-control" value="${usuario.login}" maxlength="40" required>
																		<span class="form-bar"></span> <label
																			class="float-label">Login</label>
																	</div>
																	<div class="form-group form-default">
																		<input type="password" name="senha" id="senha"
																			class="form-control" value="${usuario.senha}" maxlength="10" required
																			maxlength="8"> <span class="form-bar"></span> <label
																			class="float-label">Senha</label>
																	</div>
																</div>
																<div class="col-sm-12 col-md-6 px-0 pl-md-3">
																	<div class="form-group form-default">
																		<input type="text" name="cep" id="cep"
																			class="form-control"
																			value="<fmt:formatNumber pattern="00000000" value="${usuario.endereco.cep}"/>"
																			maxlength="9"
																			required>
																			<span class="form-bar"></span>
																			<label class="float-label">Cep</label>
																	</div>
																	<div class="form-group form-default">
																		<input type="text" name="logradouro" id="logradouro"
																			class="form-control" value="${usuario.endereco.logradouro}" maxlength="60" required>
																		<span class="form-bar"></span>
																		<label class="float-label">Logradouro (Rua)</label>
																	</div>
																	<div class="form-group form-default">
																		<input type="text" name="bairro" id="bairro"
																			class="form-control" value="${usuario.endereco.bairro}" maxlength="45" required>
																		<span class="form-bar"></span>
																		<label class="float-label">Bairro</label>
																	</div>
																	<div class="form-group form-default">
																		<input type="text" name="cidade" id="cidade"
																			class="form-control" value="${usuario.endereco.cidade}" maxlength="45" required>
																		<span class="form-bar"></span>
																		<label class="float-label">Cidade</label>
																	</div>
																	<div class="d-md-flex justify-content-between">
																		<div class="form-group form-default">
																			<input type="text" name="uf" id="uf"
																				class="form-control" value="${usuario.endereco.uf}" maxlength="2" required>
																			<span class="form-bar"></span>
																			<label class="float-label">Estado</label>
																		</div>
																		<div class="form-group form-default mx-md-3">
																			<input type="text" name="numero" id="numero"
																				class="form-control" value="${usuario.endereco.numero}" maxlength="5" required>
																			<span class="form-bar"></span>
																			<label class="float-label">Número</label>
																		</div>
																		<div class="form-group form-default">
																			<input type="text" name="complemento" id="complemento"
																				class="form-control" value="${usuario.endereco.complemento}">
																			<span class="form-bar"></span>
																			<label class="float-label">Complemento</label>
																		</div>
																	</div>
																</div>
															</div>
															<div class="form-group form-default m-t-20">
																<button type="button"
																	class="btn waves-effect waves-light btn-primary"
																	id="btnNovo">Novo
																</button>
																<button class="btn waves-effect waves-light btn-success">Salvar</button>
																<button type="button" class="btn btn-info btn-outline-info"
																	data-toggle="modal" data-target="#pesquisaUsuario">
																	Pesquisar
																</button>
																<c:if test="${usuario != null}">
																	<a href="<%= request.getContextPath() %>/TelefoneController?usuarioId=${usuario.id}"
																		class="btn btn-secondary">
																		Telefone
																	</a>
																</c:if>
															</div>
															<span class="text-success" id="msg">${msg}</span>
														</form>
													</div>
												</div>
											</div>

											<!-- Listagem -->
											<div class="col-sm-12">
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
															<table class="table table-hover table-striped">
																<thead>
																	<tr>
																		<th>#</th>
																		<th>Nome</th>
																		<th>Email</th>
																		<th>Login</th>
																		<th>Perfil</th>
																		<th>Endereço</th>
																		<th>Telefone</th>
																		<th>Ação</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${usuariosList}" var="usu">
																		<tr>
																			<th scope="row"><c:out value="${usu.id}"></c:out></th>
																			<td><c:out value="${usu.nome}"></c:out></td>
																			<td><c:out value="${usu.email}"></c:out></td>
																			<td><c:out value="${usu.login}"></c:out></td>
																			<td><c:out value="${usu.perfil}"></c:out></td>
																			<td>
																				<c:if test="${usu.endereco != null}">
																					<button type="button"
																						class="btn btn-secondary btn-sm"
																						data-container="body" data-toggle="popover"
																						data-placement="left" data-trigger="focus"
																						data-content="${usu.endereco}">
																						<i class="fa fa-eye fa-lg" aria-hidden="true"></i>
																					</button>
																				</c:if>
																			</td>
																			<td>
																				<c:if test="${fn:length(usu.telefones) != 0}">
																					<button type="button"
																						class="btn btn-secondary btn-sm"
																						data-container="body" data-toggle="popover"
																						data-placement="left" data-trigger="focus"
																						data-content="${usu.numTelefonesToString}">
																						<i class="fa fa-phone fa-lg" aria-hidden="true"></i>
																					</button>
																				</c:if>
																			</td>
																			<td><a href="<%= request.getContextPath() %>/UsuarioController?acao=editar&id=${usu.id}"
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
									<th>Perfil</th>
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
