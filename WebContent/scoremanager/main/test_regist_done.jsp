<<<<<<< HEAD
<%--成績管理完了画面JSP--%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
			<div class="row mx-3 mb-3 py-2 align-items-center rounded" id="filter">
				<div class="col-16 bg-success bg-opacity-50">
					<p class="text-center">登録が完了しました</p>
				</div>
			</div>
			<a href="TestRegist.action" class="me-5">戻る</a>
			<a href="TestList.action">成績参照</a>
		</section>
	</c:param>
=======
<%--成績管理完了画面JSP--%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
			<div class="row mx-3 mb-3 py-2 align-items-center rounded" id="filter">
				<div class="col-16 bg-success bg-opacity-50">
					<p class="text-center">登録が完了しました</p>
				</div>
			</div>
			<a href="TestRegist.action" class="me-5">戻る</a>
			<a href="TestList.action">成績参照</a>
		</section>
	</c:param>
>>>>>>> branch 'me' of https://github.com/bird8103/Exam.git
</c:import>