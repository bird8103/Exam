<%--科目削除変更JSP--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 得点管理システム</title>
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
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>
            <form action="SubjectDeleteExecute.action" method="get">
                <div class="row mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                    <div class="col-16">
                        <!-- 確認メッセージ -->
                        <label for="confirmation-message"></label>
                        <p id="confirmation-message">${selected_subject_name}  ${selected_subject_code} を削除してもよろしいですか？？</p>
                    </div>
                    <div class="col-2 text-center col-1 mt-3">
                        <!-- btn-danger クラスを追加 -->
                        <button class="btn btn-secondary px-1 btn-danger" id="end-button" name="selected_subject_code" value="${selected_subject_code}">削除</button>
                    </div>
                    <a href="SubjectList.action" class="mt-3">戻る</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>
