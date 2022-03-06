<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="br.com.robson.models.Usuario"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");
%>

<nav class="navbar header-navbar pcoded-header">
	<div class="navbar-wrapper">
		<div class="navbar-logo">
			<a class="mobile-menu waves-effect waves-light" id="mobile-collapse"
				href="#!"> <i class="ti-menu"></i>
			</a>
			<div class="mobile-search waves-effect waves-light">
				<div class="header-search">
					<div class="main-search morphsearch-search">
						<div class="input-group">
							<span class="input-group-addon search-close"><i
								class="ti-close"></i></span> <input type="text" class="form-control"
								placeholder="Enter Keyword"> <span
								class="input-group-addon search-btn"><i class="ti-search"></i></span>
						</div>
					</div>
				</div>
			</div>
			<a href="index.html"> <img class="img-fluid"
				src="<%=request.getContextPath()%>/assets/images/logo.png"
				alt="Theme-Logo" />
			</a> <a class="mobile-options waves-effect waves-light"> <i
				class="ti-more"></i>
			</a>
		</div>

		<div class="navbar-container container-fluid">
			<ul class="nav-left">
				<li>
					<div class="sidebar_toggle">
						<a href="javascript:void(0)"><i class="ti-menu"></i></a>
					</div>
				</li>
				<li class="header-search">
					<div class="main-search morphsearch-search">
						<div class="input-group">
							<span class="input-group-addon search-close"><i
								class="ti-close"></i></span> <input type="text" class="form-control">
							<span class="input-group-addon search-btn"><i
								class="ti-search"></i></span>
						</div>
					</div>
				</li>
				<li><a href="#!" onclick="javascript:toggleFullScreen()"
					class="waves-effect waves-light"> <i class="ti-fullscreen"></i>
				</a></li>
			</ul>
			<ul class="nav-right">
				<li class="header-notification"><a href="#!"
					class="waves-effect waves-light"> <i class="ti-bell"></i> <span
						class="badge bg-c-red"></span>
				</a>
					<ul class="show-notification">
						<li>
							<h6>Notifications</h6> <label class="label label-danger">New</label>
						</li>
						<li class="waves-effect waves-light">
							<div class="media">
								<c:if test="${usuarioLogado.foto != null && usuarioLogado.foto != ''}">
									<img class="d-flex align-self-center img-radius" src="${usuarioLogado.foto}" alt="Generic placeholder image">
								</c:if>
								<c:if test="${usuarioLogado.foto == null || usuarioLogado.foto == ''}">
									<img class="d-flex align-self-center img-radius" src="<%=request.getContextPath()%>/assets/images/avatar-blank.jpg" alt="Generic placeholder image">
								</c:if>
								<div class="media-body">
									<h5 class="notification-user"><%=usuarioLogado.getLogin()%></h5>
									<p class="notification-msg">Lorem ipsum dolor sit amet,
										consectetuer elit.</p>
									<span class="notification-time">30 minutes ago</span>
								</div>
							</div>
						</li>
						<li class="waves-effect waves-light">
							<div class="media">
								<img class="d-flex align-self-center img-radius"
									src="<%=request.getContextPath()%>/assets/images/avatar-4.jpg"
									alt="Generic placeholder image">
								<div class="media-body">
									<h5 class="notification-user">Joseph William</h5>
									<p class="notification-msg">Lorem ipsum dolor sit amet,
										consectetuer elit.</p>
									<span class="notification-time">30 minutes ago</span>
								</div>
							</div>
						</li>
						<li class="waves-effect waves-light">
							<div class="media">
								<img class="d-flex align-self-center img-radius"
									src="<%=request.getContextPath()%>/assets/images/avatar-3.jpg"
									alt="Generic placeholder image">
								<div class="media-body">
									<h5 class="notification-user">Sara Soudein</h5>
									<p class="notification-msg">Lorem ipsum dolor sit amet,
										consectetuer elit.</p>
									<span class="notification-time">30 minutes ago</span>
								</div>
							</div>
						</li>
					</ul></li>
				<li class="user-profile header-notification"><a href="#!"
					class="waves-effect waves-light">
					<c:if test="${usuarioLogado.foto != null && usuarioLogado.foto != ''}">
						<img src="${usuarioLogado.foto}" class="img-radius" alt="User-Profile-Image">
					</c:if>
					<c:if test="${usuarioLogado.foto == null || usuarioLogado.foto == ''}">
						<img src="<%=request.getContextPath()%>/assets/images/avatar-blank.jpg" class="img-radius" alt="User-Profile-Image">
					</c:if>
					<span><%=usuarioLogado.getLogin()%></span>
					<i class="ti-angle-down"></i>
				</a>
					<ul class="show-notification profile-notification">
						<li class="waves-effect waves-light"><a href="#!"> <i
								class="ti-settings"></i> Settings
						</a></li>
						<li class="waves-effect waves-light"><a
							href="user-profile.html"> <i class="ti-user"></i> Profile
						</a></li>
						<li class="waves-effect waves-light"><a
							href="email-inbox.html"> <i class="ti-email"></i> My Messages
						</a></li>
						<li class="waves-effect waves-light"><a
							href="auth-lock-screen.html"> <i class="ti-lock"></i> Lock
								Screen
						</a></li>
						<li class="waves-effect waves-light"><a
							href="<%=request.getContextPath()%>/LoginController?acao=logout">
								<i class="ti-layout-sidebar-left"></i> Logout
						</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</nav>