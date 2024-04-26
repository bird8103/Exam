<%--科目登録完了画面JSP--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-3">
            <%-- サイドバー --%>
            <ul class="nav nav-pills flex-column mb-auto px-4">
                <li class="nav-item my-3"><a href="Menu.action">メニュー</a></li>
                <li class="nav-item mb-3"><a href="StudentList.action">学生管理</a></li>
                <li class="nav-item">成績管理</li>
                <li class="nav-item mx-3 mb-3"><a href="TestRegist.action">成績登録</a></li>
                <li class="nav-item mx-3 mb-3"><a href="TestList.action">成績参照</a></li>
                <li class="nav-item mb-3"><a href="SubjectList.action">科目管理</a></li>
            </ul>
        </div>
        <div class="col-md-9">
            <%-- メインコンテンツ --%>
            <c:import url="/common/base.jsp">
                <c:param name="title">得点管理システム</c:param>
                <c:param name="scripts"></c:param>
                <c:param name="content">
                    <section class="me-4">
                        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
                        <div class="row mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                            <div class="col-16 bg-success bg-opacity-50">
                                <p class="text-center">登録が完了しました</p>
                            </div>
                        </div>
                        <a href="SubjectsCreate.action" class="me-5">戻る</a>
                        <a href="SubjectsList.action">科目一覧</a>
                    </section>
                </c:param>
            </c:import>
        </div>
    </div>
    <%-- ナビゲーション --%>
    <footer class="footer text-center mt-auto py-3">
        <div class="container">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container-fluid">

                </div>
            </nav>
        </div>
    </footer>
</div>

</body>
</html>
