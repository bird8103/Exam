<%--学生一覧JSP--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-10">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-8">成績参照</h2>

			<form method="get">
				<div class="row border  mx--10 mb-3 py-2 align-items-center rounded" id="filter">


<div class="col-3">科目情報</div>
				<form method="POST">
					<div class="col-2">
					<label class="form-label" for="student-fl-select">入学年度</label>
						<select class="form-select" id="student-fl-select" name="f1" required>
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<%--現在のyear と選択されていたF1が一致していた場合 selectedを追記 --%>
								<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-2">
						<label class="form-label" for="student-f2-select">クラス</label>
						<select class="form-select" id="student-f2-select" name="f2" required>
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set}">
								<%--現在のnumと選択されていた2が一致していた場合 selectedを追記--%>
								<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>




					<div class="col-2">
						<label class="form-label" for="student-f3-select">科目</label>
						<select class="form-select" id="student-f3-select" name="f3" required>
							<option value="0">--------</option>
							<c:forEach var="subject" items="${subject_set}">
								<%--現在のnumと選択されていた2が一致していた場合 selectedを追記--%>
								<option value="${subject}" <c:if test="${num==f3}">selected</c:if>>${subject}</option>
								<div class="border-bottom mx-5 mb-5 py-2 align-items-center rounded" id="filter"></div>
							</c:forEach>
						</select>




					</div>
<div class="col-2" ><input type="button" name="STU_POST_GET" value="検索"></div>

					<form method="POST">
					<div class=" border-bottom  mb-2 py-2  align-items-center rounded" id="filter"></div>


<div class="col-3">学生情報</div>
					<c:if test="${errors.get('f2')!=null}"><div class="col-3"><font color="FFD500">${errors.get("f2")}</font></div></c:if>
					<div class="col-5">
						<label class="form-label" for="student-f4-text">学生番号</label>

						<div class="col-13"><input class="form-control" type="text" placeholder="学生番号を入力して下さい"
							name="name" id="student-f4-text" maxlength="5" required
							<c:if test="${name!=null}">value="${f4}"</c:if>></div>




					</div>
<div class="col-2"><input type="submit" name="STU_GET" value="検索"></div>


</div>


					<div class="mt-2 text-warning">${errors.get("f1")}</div>


			</form>

			</section>





					<div class="mt-2 text-warning">${errors.get("f1")}</div>


			</form>
			<c:choose>
				<c:when test="${students.size()!=0}">
					<div>科目:${students.size()}</div>
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>1回</th>
							<th>2回</th>

						</tr>
						<c:forEach var="student" items="${students}">
							<tr>
								<td>${entYear}</td>
								<td>${classNum}</td>
								<td>${studentNo}</td>
								<td>${studentName}</td>
								<td>${points}</td>
								<td>${points}</td>



							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div>学生情報が存在しませんでした</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>
