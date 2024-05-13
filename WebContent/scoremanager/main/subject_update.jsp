<%--科目情報変更JSP--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>
</head>
<body>

</body>
</html>



<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
			<form action="SubjectUpdateExecute.action" method="get">
				<div class="row mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-16">
						<label class="form-label" for="subject-code-text">科目コード</label>
						<input class="form-control-plaintext ps-3" type="text" name="code" id="subject-code-text" readonly value="${code}">
					</div>

					<div class="col-16">
						<label class="form-label" for="subject-name-text">科目名</label>
						<input class="form-control" type="text" placeholder="科目名を入力してください" name="name" id="subject-name-text" maxlength="30" required value="${name}">
					</div>


					<div class="col-2 text-center col-1 mt-3">
						<button class="btn btn-secondary px-1" id="end-button" name="end">変更</button>
					</div>
					<a href="SubjectList.action" class="mt-3">戻る</a>
				</div>
			</form>
		</section>
	</c:param>
</c:import>