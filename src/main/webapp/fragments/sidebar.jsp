<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

  <%@ page import="br.com.robson.models.Usuario" %>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

      <% Usuario usuarioLogado=(Usuario) request.getSession().getAttribute("usuarioLogado"); %>

        <nav class="pcoded-navbar">
          <div class="sidebar_toggle">
            <a href="#"><i class="icon-close icons"></i></a>
          </div>

          <div class="pcoded-inner-navbar main-menu">
            <div class="">
              <div class="main-menu-header">
                <c:if test="${usuarioLogado.foto != null && usuarioLogado.foto != ''}">
                  <img class="img-80 img-radius" alt="User-Profile-Image" src="${usuarioLogado.foto}">
                </c:if>
                <c:if test="${usuarioLogado.foto == null || usuarioLogado.foto == ''}">
                  <img class="img-80 img-radius" src="<%=request.getContextPath()%>/assets/images/avatar-blank.jpg"
                    alt="User-Profile-Image">
                </c:if>
                <div class="user-details">
                  <span id="more-details">
                    <%=usuarioLogado.getLogin()%>
                      <i class="fa fa-caret-down"></i>
                  </span>
                </div>
              </div>

              <div class="main-menu-content">
                <ul>
                  <li class="more-details"><a href="user-profile.html">
                      <i class="ti-user"></i>View Profile</a>
                    <a href="#!">
                      <i class="ti-settings"></i>Settings
                    </a>
                    <a href="<%=request.getContextPath()%>/LoginController?acao=logout">
                      <i class="ti-layout-sidebar-left"></i>Logout
                    </a>
                  </li>
                </ul>
              </div>
            </div>
            <ul class="pcoded-item pcoded-left-item mt-5">
              <li class="active mb-3">
                <a href="<%=request.getContextPath()%>/home" class="waves-effect waves-dark">
                  <span class="pcoded-micon">
                    <i class="ti-home"></i><b>D</b>
                  </span> <span class="pcoded-mtext" data-i18n="nav.dash.main">Dashboard</span>
                  <span class="pcoded-mcaret"></span>
                </a>
              </li>
              <li class="pcoded-hasmenu">
                <a href="javascript:void(0)" class="waves-effect waves-dark">
                  <span class="pcoded-micon">
                    <i class="ti-layout-grid2-alt"></i>
                  </span>
                  <span class="pcoded-mtext" data-i18n="nav.basic-components.main">Cadastros</span>
                  <span class="pcoded-mcaret"></span>
                </a>
                <ul class="pcoded-submenu">
                  <c:if test="<%=usuarioLogado.isUserAdmin()%>">
                    <li class=" ">
                      <a href="<%=request.getContextPath()%>/UsuarioController" class="waves-effect waves-dark">
                        <span class="pcoded-micon">
                          <i class="ti-angle-right"></i></span>
                        <span class="pcoded-mtext" data-i18n="nav.basic-components.alert">Usuário</span>
                        <span class="pcoded-mcaret"></span>
                      </a>
                    </li>
                  </c:if>
                </ul>
              </li>
            </ul>
            <div class="pcoded-navigation-label" data-i18n="nav.category.forms">Relatórios</div>
            <ul class="pcoded-item pcoded-left-item">
              <li>
                <a href="form-elements-component.html" class="waves-effect waves-dark">
                  <span class="pcoded-micon"><i class="ti-layers"></i><b>FC</b></span>
                  <span class="pcoded-mtext" data-i18n="nav.form-components.main">Usuários</span>
                  <span class="pcoded-mcaret"></span>
                </a>
              </li>
            </ul>
          </div>
        </nav>