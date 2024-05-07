<%--ログアウト完了画面JSP--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">ログアウト</h2>
			<div class="row mx-3 mb-3 py-2 align-items-center rounded" id="filter">
				<div class="col-16 bg-success bg-opacity-50">
					<p class="text-center">ログアウトしました</p>
				</div>
			</div>
			<a href="../login.jsp">ログイン</a>
		</section>
	</c:param>
</c:import>