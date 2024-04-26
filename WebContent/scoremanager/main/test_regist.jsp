<%--成績管理一覧JSP--%>
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
			<div class="my-2 text-end px-4">
			</div>
<!-- 完全一致型の検索 =＞ null無し-->
			<form method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
				<!-- 以下全てselectメニュー -->
					<div class="col-2">
						<label class="form-label" for="student-f1-select">入学年度</label>
						<select class="form-select" id="student-f1-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<%--現在のyear と選択されていたF1が一致していた場合 selectedを追記 --%>
								<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2">
						<label class="form-label" for="student-f2-select">クラス</label>
						<select class="form-select" id="student-f2-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set}">
<<<<<<< HEAD
								<%--現在のnumと選択されていたF2が一致していた場合 selectedを追記 ---%>
=======
								<%--現在のnumと選択されていたF2が一致していた場合 selectedを追記 --%>
>>>>>>> branch 'me' of https://github.com/bird8103/Exam.git
								<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="form-select" for="student-f3-select">科目</label>
							<%--パラメーターf3が存在している場合 checkedを追記--%>
						<select class="form-select" id="student-f3-select" name="f3">
							<option value="0">--------</option>
							<c:forEach var="subject.cd" items="${subject_set}">
								<%--現在のsubject.cdと選択されていたF3が一致していた場合 selectedを追記--%>
								<option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.cd}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2">
						<label class="form-label" for="student-f4-select">回数</label>
						<select class="form-select" id="student-f4-select" name="f4">
							<option value="0">--------</option>
							<c:forEach var="num" items="${num_of_time_set}">
								<%--現在のnumと選択されていたF4が一致していた場合 selectedを追記--%>
								<option value="${num}" <c:if test="${num==f4}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("all")}</div>
				</div>
			</form>
<!-- 検索結果 -->
<!-- 			<c:choose>
					<c:when test="${dep == true}">
						<div>科目 :${f3} : (:${f4}回)</div>
						<table class="table table-hover">
							<tr>
								<th>入学年度</th>
								<th>学生番号</th>
								<th>氏名</th>
								<th>クラス </th>
								<th><label class="form-label" for="test-num">点数</label></th>
							</tr>
							<c:forEach var="student" items="${students_score}">
							<tr>
								<td>${student.entYear}</td>
								<td>${student.no}</td>
								<td>${student.name}</td>
								<td>${student.classNum}</td>
								<td>
									<div class="col-6">
										<input class="form-control" type="text" name="point_${num}" id="test-num" maxlength="3" required >
									</div>
									<c:if test="${errors.get(\"point\")!}"><div class="col-16"><font color="FFD500">${errors.get("point")}</font></div></c:if>
								</td>
							</tr>
						</c:forEach>
					</table>
					<div class="col-2 text-center">
					<button class="btn btn-secondary" id="exe-button"><a href="TestRegistExecute.action">保存して終了</a></button>
					</div>
				</c:when>
			</c:choose> -->
		</section>
	</c:param>
</c:import>