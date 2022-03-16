<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


	<!-- Required Jquery -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/jquery-ui/jquery-ui.min.js "></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/popper.js/popper.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/bootstrap/js/bootstrap.min.js "></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/pages/widget/excanvas.js "></script>
	<!-- waves js -->
	<script src="<%=request.getContextPath()%>/assets/pages/waves/js/waves.min.js"></script>
	<!-- jquery slimscroll js -->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/js/jquery-slimscroll/jquery.slimscroll.js "></script>
	<!-- modernizr js -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/modernizr/modernizr.js "></script>
	<!-- slimscroll js -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/SmoothScroll.js"></script>
	<script src="<%=request.getContextPath()%>/assets/js/jquery.mCustomScrollbar.concat.min.js "></script>
	<!-- Chart js -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/chart.js/Chart.js"></script>
	<!-- amchart js -->
	<script src="https://www.amcharts.com/lib/3/amcharts.js"></script>
	<script src="<%=request.getContextPath()%>/assets/pages/widget/amchart/gauge.js"></script>
	<script src="<%=request.getContextPath()%>/assets/pages/widget/amchart/serial.js"></script>
	<script src="<%=request.getContextPath()%>/assets/pages/widget/amchart/light.js"></script>
	<script src="<%=request.getContextPath()%>/assets/pages/widget/amchart/pie.min.js"></script>
	<script src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
	<!-- menu js -->
	<script src="<%=request.getContextPath()%>/assets/js/pcoded.min.js"></script>
	<script src="<%=request.getContextPath()%>/assets/js/vertical-layout.min.js "></script>
	<!-- custom js -->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/pages/dashboard/custom-dashboard.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/script.js "></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/jquery.mask.min.js "></script>

	<!-- Validação formulários -->
	<script>
		// Example starter JavaScript for disabling form submissions if there are invalid fields
		(function () {
			'use strict';
			window.addEventListener('load', function () {
				// Fetch all the forms we want to apply custom Bootstrap validation styles to
				var forms = document.getElementsByClassName('needs-validation');
				// Loop over them and prevent submission
				var validation = Array.prototype.filter.call(forms, function (form) {
					form.addEventListener('submit', function (event) {
						if (form.checkValidity() === false) {
							event.preventDefault();
							event.stopPropagation();
						}
						form.classList.add('was-validated');
					}, false);
				});
			}, false);
		})();

		/* Confirmar exclusão */
		function confirmar(id, nome) {
			let resposta = confirm("Deseja realmente excluir: " + nome);
			if (resposta) {
				window.location.href = "?acao=deletar&id=" + id;
			}
		}

		function popularModal(response) {
			var json = JSON.parse(response);
			$('#consultaUsuariosResult > tbody > tr').remove();

			for (var p = 0; p < json.length; p++) {
				$('#consultaUsuariosResult > tbody')
					.append(
						"<tr> <td>"
						+ json[p].id
						+ "</td> <td>"
						+ json[p].nome
						+ "</td> <td>"
						+ json[p].email
						+ "</td> <td>"
						+ json[p].login
						+ "</td> <td>"
						+ json[p].perfil
						+ "</td> <td><a href=\"?acao=editar&id=" + json[p].id + "\" class=\"btn btn-sm btn-info mr-2\">Editar</a><a href=\"javascript: deleteComAjax("
						+ json[p].id
						+ ", \'"
						+ json[p].nome
						+ "\')\" class=\"btn btn-sm btn-danger\">Excluir</a></td></tr>");
			}

			$("#totalResultados").text('Resultados: ' + json.length);
		}

		/* Buscar usuários */
		function buscarUsuarios(nome) {
			if (nome != null && nome != '' && nome.trim() != '') {
				var urlAction = document.getElementById('formUser').action;//Outra forma com javaScript puro

				$.ajax({
					method: "get",
					url: urlAction,
					data: {
						nomeBusca: nome,
						acao: 'buscarUserAjax'
					},
					/* data : "nomeBusca=" + nome + '&acao=buscarUserAjax', *///Outra forma de fazer
					success: function (response) {
						popularModal(response);
					}

				}).fail(function (xhr, status, errorThrown) {
					alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
				});
			}
		}

		/* Deletar com Ajax */
		function deleteComAjax(id, nome) {

			if (confirm("Deseja realmente excluir: " + nome)) {
				var urlAction = document.getElementById('formUser').action;

				$.ajax({
					method: "get",
					url: urlAction,
					data: {
						id: id,
						acao: 'deletarAjax'
					},
					success: function (response) {
						$('#msgModal').text(response);

						/* Buscar usuários sempre que excluir*/
						let nome = $('#nomeBusca').val();
						buscarUsuarios(nome);
					}

				}).fail(function (xhr, status, errorThrown) {
					alert('Erro ao deletar usuário por id: ' + xhr.responseText);
				});
			}
		}

		$(document).ready(function () {
			//Outra opcao para limpar inputs com javascript puro
			/* function limparForm() {
				var elementos = document.getElementById("formUser").elements; Retorna os elementos html dentro do form
		
				for (p = 0; p < elementos.length; p++) {
					elementos[p].value = '';
				}
			} */

			//Outra opcao para limpar inputs com jquery	
			/* $('#btnNovo').click(function() {
				$('#formUser *').filter(':input').each(function() {
					this.value = '';
				})
			}); */

			/* Limpar formulário */
			$('#btnNovo').click(function () {
				var urlAction = document.getElementById('formUser').action;
				window.location.href = urlAction;

				/* $('#formUser :input, #formUser #msg').each(function(key, value) {
					this.value = ''; //Limpa os inputs
					
					if (this.id == 'msg') { //Limpa o span com id = msg
						this.innerHTML = '';
					}
				}) */
			});

			/* Buscar usuários ao clicar em  btnBusca*/
			$('#btnBusca').click(function () {
				let nome = $('#nomeBusca').val();
				buscarUsuarios(nome);
			});

			/* Limpar modal de consulta ao fechar*/
			$("#pesquisaUsuario").on("hidden.bs.modal", function () {
				$('#consultaUsuariosResult > tbody > tr').remove();
				$('#nomeBusca').val('');
				$("#totalResultados").text('');
				$('#msgModal').text('');
			});

			/* Troca a foto do usuario*/
			$('#fileFoto').change(function () {
				var urlAction = document.getElementById('formUser').action;
				let nome = urlAction.split(':')[2].split('/')[1];

				var preview = $('#fotoBase64')[0]; // img
				var file = $('#fileFoto').prop('files')[0]; // input file
				var reader = new FileReader();
				var msg = $('#fileMsg')[0];

				const size = (file.size / 1000).toFixed(2);

				if (size > 100 || size < 50) {
					$('#fileFoto').val('');
					preview.src = nome + '../../assets/images/avatar-blank.jpg';
					msg.innerText = 'O arquivo deve ter um tamanho entre 50-100 kB';
				} else {
					reader.onloadend = function () {
						preview.src = reader.result; // Carrega foto na tela
					};

					if (file) {
						reader.readAsDataURL(file); // Preview da imagem
					} else {
						preview.src = '';
					}

					msg.innerText = '';
				}
			});

			function limpa_formulário_cep() {
				// Limpa valores do formulário de cep.
				$("#logradouro").val("");
				$("#bairro").val("");
				$("#cidade").val("");
				$("#uf").val("");
			}

			//Quando o campo cep perde o foco.
			$("#cep").blur(function () {

				//Nova variável "cep" somente com dígitos.
				var cep = $(this).val().replace(/\D/g, '');

				//Verifica se campo cep possui valor informado.
				if (cep != "") {

					//Expressão regular para validar o CEP.
					var validacep = /^[0-9]{8}$/;

					//Valida o formato do CEP.
					if (validacep.test(cep)) {

						//Preenche os campos com "..." enquanto consulta webservice.
						$("#logradouro").val("...");
						$("#bairro").val("...");
						$("#cidade").val("...");
						$("#uf").val("...");

						//Consulta o webservice viacep.com.br/
						$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?", function (dados) {

							if (!("erro" in dados)) {
								//Atualiza os campos com os valores da consulta.
								$("#logradouro").val(dados.logradouro);
								$("#bairro").val(dados.bairro);
								$("#cidade").val(dados.localidade);
								$("#uf").val(dados.uf);
							} //end if.
							else {
								//CEP pesquisado não foi encontrado.
								limpa_formulário_cep();
								alert("CEP não encontrado.");
							}
						});
					} //end if.
					else {
						//cep é inválido.
						limpa_formulário_cep();
						alert("Formato de CEP inválido.");
					}
				} //end if.
				else {
					//cep sem valor, limpa formulário.
					limpa_formulário_cep();
				}

			});
			$('#cep').mask('00000-000');
			
			//popover bootstrap
			$('[data-toggle="popover"]').popover();
			$('.popover-dismiss').popover({
				trigger: 'focus'
			})
		});
	</script>