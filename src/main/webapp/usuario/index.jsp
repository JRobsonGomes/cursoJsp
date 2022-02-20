<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
											<div class="col-sm-12 col-md-8">
												<div class="card">
													<div class="card-header">
														<h5>Cadastro de usuário</h5>
														<!--<span>Add class of <code>.form-control</code> with <code>&lt;input&gt;</code> tag</span>-->
													</div>
													<div class="card-block">
														<form id="formUser" class="form-material needs-validation" method="post"
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
																<button type="button" class="btn waves-effect waves-light btn-primary" id="btnNovo">Novo</button>
																<button class="btn waves-effect waves-light btn-success">Salvar</button>
															</div>
															<span class="text-success" id="msg">${msg}</span>
														</form>
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
