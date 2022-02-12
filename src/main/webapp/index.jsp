<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="Robson Gomes">
<title>Curso JSP</title>

<!-- Bootstrap core CSS -->
<link href="assets/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="assets/css/signin.css" rel="stylesheet">
</head>
<body class="text-center">
	<main class="form-signin">
		<form action="<%=request.getContextPath()%>/LoginController"
			method="post" class="needs-validation" novalidate>

			<img class="mb-4" src="assets/brand/person-circle.svg" alt=""
				width="80" height="80">

			<h1 class="h3 mb-3 fw-normal">Por favor faça login</h1>

			<input type="hidden" value="<%=request.getParameter("url")%>"
				name="url">
			<div class="form-floating">
				<input name="login" type="text" class="form-control"
					id="floatingInput" placeholder="your-user" required> <label
					for="floatingInput">Login</label>
			</div>

			<div class="form-floating">
				<input name="senha" type="password" class="form-control"
					id="floatingPassword" placeholder="Password" required> <label
					for="floatingPassword">Senha</label>
			</div>

			<div class="checkbox mb-3">
				<label> <input type="checkbox" value="remember-me">
					Lembrar-me
				</label>
			</div>

			<button class="w-100 btn btn-lg btn-light" type="submit">Enviar</button>
			<p class="mt-2 text-danger">${msg}</p>
			<p class="mt-3 mb-3 text-muted">&copy; 2021</p>
		</form>
	</main>

	<script src="assets/js/signin.js"></script>
</body>
</html>