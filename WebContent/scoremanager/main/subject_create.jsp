<%-- 科目登録画面JSP --%>
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

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts">
        <script>
            function validateSubjectCode() {
                var subjectCode = document.getElementById('subject-code-text').value;
                var errorMessage = document.getElementById('subject-code-error');
                if (subjectCode.length < 3) {
                    errorMessage.innerHTML = '<span class="error-message" style="color: orange;">科目コードは3文字以上で入力してください。</span>';
                    return false; // フォームの送信をキャンセル
                } else {
                    errorMessage.innerHTML = ''; // エラーメッセージをクリア
                    return true; // フォームの送信を許可
                }
            }
        </script>
    </c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
            <form action="SubjectCreateExecute.action" method="get" onsubmit="return validateSubjectCode()">
                <div class="row mx-3 mb-3 py-2 align-items-center rounded" id="filter">

                    <c:if test="${errors.get('f1')!=null}">
                        <div class="col-16"><font color="FFD500">${errors.get("f1")}</font></div>
                    </c:if>
                    <div class="col-16">
                        <label class="form-label" for="subjects-cd-text">科目コード</label>
                        <input class="form-control" type="text" placeholder="科目コードを入力してください" name="cd" id="subject-code-text" maxlength="10" required <c:if test="${no!=null}">value="${no}"</c:if>>
                    </div>
                    <div class="col-16 error-message" id="subject-cd-error"></div> <%-- エラーメッセージを表示する要素 --%>

                    <c:if test="${errors.get('f2')!=null}">
                        <div class="col-16"><font color="FFD500">${errors.get("f2")}</font></div>
                    </c:if>
                    <div class="col-16">
                        <label class="form-label" for="subject-name-text">科目名</label>
                        <input class="form-control" type="text" placeholder="科目名を入力してください" name="name" id="subject-name-text" maxlength="30" required <c:if test="${name!=null}">value="${name}"</c:if>>
                    </div>

                    <div class="col-2 text-center col-1 mt-3">
                        <button class="btn btn-secondary px-1" id="end-button" name="end">登録</button>

                    </div>
                    <a href="SubjectList.action" class="mt-3">戻る</a>
                </div>
            </form>
            <%-- 重複エラーメッセージの表示 --%>
            <c:if test="${errors.get('duplicate')!=null}">
                <div class="col-16"><font color="FFD500">${errors.get("duplicate")}</font></div>
            </c:if>
        </section>
    </c:param>
</c:import>

</body>
</html>
