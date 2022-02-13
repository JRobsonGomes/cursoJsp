<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt-BR">

<jsp:include page="fragments/head.jsp"></jsp:include>

<body>
	<!-- Pre-loader start -->
	
	<jsp:include page="fragments/theme-loader.jsp"></jsp:include>
	<!-- Pre-loader end -->

	<section class="login-block">
		<!-- Container-fluid starts -->
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<!-- Authentication card start -->

					<form class="md-float-material form-material needs-validation"
						action="<%=request.getContextPath()%>/LoginController"
						method="post" novalidate>
						<div class="text-center">
							<h1>Curso Jsp</h1>
						</div>
						<div class="auth-box card">
							<div class="card-block">
								<div class="row m-b-20">
									<div class="col-md-12">
										<h3 class="text-center">Login</h3>
									</div>
								</div>
								<input type="hidden" value="<%=request.getParameter("url")%>" name="url">
								<div class="form-group form-primary">
									<input type="text" name="login" class="form-control" required>
									<span class="form-bar"></span>
									<label class="float-label">Login</label>
								</div>
								<div class="form-group form-primary">
									<input type="password" name="senha" class="form-control" required>
									<span class="form-bar"></span>
									<label class="float-label">Senha</label>
								</div>
								<div class="row m-t-25 text-left">
									<div class="col-12">
										<div class="checkbox-fade fade-in-primary d-">
											<label> <input type="checkbox" value=""> <span
												class="cr"><i
													class="cr-icon icofont icofont-ui-check txt-primary"></i></span> <span
												class="text-inverse">Lembrar-me</span>
											</label>
										</div>
										<div class="forgot-phone text-right f-right">
											<a href="#" class="text-right f-w-600"> Esqueceu a senha?</a>
										</div>
									</div>
								</div>
								<div class="row m-t-30">
									<div class="col-md-12">
										<button type="submit"
											class="btn btn-primary btn-md btn-block waves-effect waves-light text-center m-b-20">Enviar</button>
									</div>
								</div>
								<div class="row m-b-50">
									<div class="col-md-12">
										<p class="text-left m-b-0 text-danger">${msg}</p>
									</div>
								</div>
							</div>
						</div>
					</form>
					<!-- end of form -->
				</div>
				<!-- end of col-sm-12 -->
			</div>
			<!-- end of row -->
		</div>
		<!-- end of container-fluid -->
	</section>
	<jsp:include page="fragments/javascrip-file.jsp"></jsp:include>

</body>

</html>