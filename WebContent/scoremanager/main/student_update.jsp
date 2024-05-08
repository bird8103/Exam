<%--学生情報変更JSP--%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
			<form action="StudentUpdateExecute.action" method="get">
				<div class="row mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-16">
						<label class="form-label" for="student-entyear-text">入学年度</label>
						<input class="form-control-plaintext ps-3" type="text" name="ent_year" id="student-entyear-text" readonly value="${ent_year}">
					</div>
					<div class="col-16">
						<label class="form-label" for="student-no-text">学生番号</label>
						<input class="form-control-plaintext ps-3" type="text" name="no" id="student-no-text" readonly value="${no}">
					</div>
					<div class="col-16">
						<label class="form-label" for="student-name-text">氏名</label>
						<input class="form-control" type="text" placeholder="氏名を入力してください" name="name" id="student-name-text" maxlength="30" required value="${name}">
					</div>
					<div class="col-16">
						<label class="form-label" for="student-cl-select">クラス</label>
						<select class="form-select" id="student-cl-select" name="class_num">
							<c:forEach var="num" items="${class_num_set}">
								<option value="${num}" <c:if test="${num==class_num}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-check col-16">
					  <label class="form-check-label" for="attend_check">
					    在学中
					  </label>
					  <input class="form-check-input" type="checkbox" value="1" name="si_attend" id="attend_check"  <c:if test="${attend}">checked</c:if>>
					</div>
					<div class="col-2 text-center col-1 mt-3">
						<button class="btn btn-secondary px-1" id="end-button" name="end">変更</button>
					</div>
					<a href="subject_list.jsp" class="mt-3">戻る</a>
				</div>
			</form>
		</section>
	</c:param>
</c:import>