<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="br.com.robson.utils.Util" %>

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
                              <h5>Filtrar por data de nascimento</h5>
                            </div>
                            <div class="card-block">
                              <form
                                id="formRelatorioUser"
                                class="form-material"
                                method="get"
                                action="<%=request.getContextPath()%>/RelatorioController"
                              >
                                <div
                                  class="d-md-flex justify-content-between align-items-end"
                                >
                                  <div
                                    class="form-group form-default col-md-4 px-0 pr-md-3"
                                  >
                                    <input
                                      type="text"
                                      name="dataInicial"
                                      id="dataInicial"
                                      class="form-control"
                                      value="${dataInicial}"
                                    />
                                    <span class="form-bar"></span>
                                    <label class="float-label"
                                      >Data Inicial</label
                                    >
                                  </div>
                                  <div
                                    class="form-group form-default col-md-4 px-0 pr-md-3"
                                  >
                                    <input
                                      type="text"
                                      name="dataFinal"
                                      id="dataFinal"
                                      class="form-control"
                                      value="${dataFinal}"
                                    />
                                    <span class="form-bar"></span>
                                    <label class="float-label"
                                      >Data Final</label
                                    >
                                  </div>
                                  <div
                                    class="form-group form-default px-0 col-md-3"
                                  >
                                    <button
                                      type="button"
                                      onclick="gerarGrafico();"
                                      class="btn btn-block btn-sm waves-effect waves-light btn-primary"
                                    >
                                      Gerar Gráfico
                                    </button>
                                  </div>
                                </div>
                                <span class="text-success" id="msg"
                                  >${msg}</span
                                >
                              </form>
                            </div>
                          </div>
                        </div>

                        <!-- Listagem -->
                        <div class="col-sm-12">
                          <div class="card">
                            <div
                              class="card-header d-flex justify-content-between"
                            >
                              <h5>Gráficos</h5>
                            </div>
                            <div class="card-block">
                              <div>
                                <canvas id="myChart"></canvas>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
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

    <script>
      var myChart = new Chart($("#myChart"));

      function gerarGrafico() {
        var urlAction = document.getElementById("formRelatorioUser").action;

        $.ajax({
          method: "get",
          url: urlAction,
          data: {
            acao: "graficoSalario",
          },
          success: function (response) {
			var json = JSON.parse(response)
            myChart.destroy();

            myChart = new Chart($("#myChart"), {
              type: "line",
              data: {
                labels: json.perfis,
                datasets: [
                  {
                    label: "Gráfico de média salarial por perfil",
                    backgroundColor: "rgb(255, 99, 132)",
                    borderColor: "rgb(255, 99, 132)",
                    data: json.salarios,
                  },
                ],
              },
              options: {},
            });
          },
        }).fail(function (xhr, status, errorThrown) {
          alert("Erro ao gerar gráfico média salarial: " + xhr.responseText);
        });

      }
    </script>
  </body>
</html>
