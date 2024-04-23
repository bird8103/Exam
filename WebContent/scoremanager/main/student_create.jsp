<%--学生登録画面JSP--%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>
			<form action="StudentCreateExecute.action" method="get">
				<div class="row mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-16">
						<label class="form-label" for="student-ey-select">入学年度</label>
						<select class="form-select" id="student-ey-select" name="ent_year" required>
							<!--　　<option value="" disabled selected>--------</option>　　-->
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<option value="${year}">${year}</option>
							</c:forEach>
						</select>
					</div>
					<c:if test="${errors.get('f1')!=null}"><div class="col-16"><font color="FFD500">${errors.get("f1")}</font></div></c:if>
					<div class="col-16">
						<label class="form-label" for="student-num-text">学生番号</label>
						<input class="form-control" type="text" placeholder="学生番号を入力してください" name="no" id="student-num-text" maxlength="10" required <c:if test="${no!=null}">value="${no}"</c:if>>
					</div>
					<c:if test="${errors.get('f2')!=null}"><div class="col-16"><font color="FFD500">${errors.get("f2")}</font></div></c:if>
					<div class="col-16">
						<label class="form-label" for="student-name-text">氏名</label>
						<input class="form-control" type="text" placeholder="氏名を入力してください" name="name" id="student-name-text" maxlength="30" required <c:if test="${name!=null}">value="${name}"</c:if>>
					</div>
					<div class="col-16">
						<label class="form-label" for="student-cl-select">クラス</label>
						<select class="form-select" id="student-cl-select" name="class_num">
							<c:forEach var="num" items="${class_num_set}">
								<option value="${num}">${num}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2 text-center col-1 mt-3">
						<button class="btn btn-secondary px-1" id="end-button" name="end">登録して終了</button>
					</div>
					<a href="StudentList.action" class="mt-3">戻る</a>
				</div>
			</form>
		</section>
	</c:param>
</c:import>