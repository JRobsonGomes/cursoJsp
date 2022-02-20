<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!-- Required Jquery -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/jquery/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/jquery-ui/jquery-ui.min.js "></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/popper.js/popper.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/bootstrap/js/bootstrap.min.js "></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/pages/widget/excanvas.js "></script>
<!-- waves js -->
<script
	src="<%=request.getContextPath()%>/assets/pages/waves/js/waves.min.js"></script>
<!-- jquery slimscroll js -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/jquery-slimscroll/jquery.slimscroll.js "></script>
<!-- modernizr js -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/modernizr/modernizr.js "></script>
<!-- slimscroll js -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/SmoothScroll.js"></script>
<script
	src="<%=request.getContextPath()%>/assets/js/jquery.mCustomScrollbar.concat.min.js "></script>
<!-- Chart js -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/chart.js/Chart.js"></script>
<!-- amchart js -->
<script src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script
	src="<%=request.getContextPath()%>/assets/pages/widget/amchart/gauge.js"></script>
<script
	src="<%=request.getContextPath()%>/assets/pages/widget/amchart/serial.js"></script>
<script
	src="<%=request.getContextPath()%>/assets/pages/widget/amchart/light.js"></script>
<script
	src="<%=request.getContextPath()%>/assets/pages/widget/amchart/pie.min.js"></script>
<script
	src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
<!-- menu js -->
<script src="<%=request.getContextPath()%>/assets/js/pcoded.min.js"></script>
<script
	src="<%=request.getContextPath()%>/assets/js/vertical-layout.min.js "></script>
<!-- custom js -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/pages/dashboard/custom-dashboard.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/script.js "></script>

<!-- Validação formulários -->
<script>
	// Example starter JavaScript for disabling form submissions if there are invalid fields
	(function() {
		'use strict';
		window.addEventListener('load', function() {
			// Fetch all the forms we want to apply custom Bootstrap validation styles to
			var forms = document.getElementsByClassName('needs-validation');
			// Loop over them and prevent submission
			var validation = Array.prototype.filter.call(forms, function(form) {
				form.addEventListener('submit', function(event) {
					if (form.checkValidity() === false) {
						event.preventDefault();
						event.stopPropagation();
					}
					form.classList.add('was-validated');
				}, false);
			});
		}, false);
	})();
	
	
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
	
	
	$('#btnNovo').click(function() {
		$('#formUser :input, #formUser #msg').each(function(key, value) {
			this.value = ''; //Limpa os inputs
			
			if (this.id == 'msg') { //Limpa o span com id = msg
				this.innerHTML = '';
			}
		})
	});
</script>

