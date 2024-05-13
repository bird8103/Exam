package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action{
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher =(Teacher)session.getAttribute("user");
        // フォームから送信された科目コードを取得
        String cd = req.getParameter("selected_subject_code");

        // 科目Daoを初期化
        SubjectDao subjectDao = new SubjectDao();

        // 科目を取得
        Subject subject = subjectDao.get(cd, teacher.getSchool());

        // 科目が存在すれば削除を試みる
        if (subject != null) {
            // 科目を削除
            boolean success = subjectDao.delete(subject);

            // 成功したかどうかをリクエスト属性にセット
            req.setAttribute("subjectDeleted", success);

            // 成功または失敗に応じた遷移先のView名を返す
            if (success) {
                req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res); // 科目の削除に成功した場合のView名
            } else {
                req.getRequestDispatcher("error.jsp").forward(req, res); // 科目の削除に失敗した場合のView名
            }
        } else {
            // 科目が見つからない場合のエラーハンドリング
            // エラーページにリダイレクトなどを行う
        }
    }
}
