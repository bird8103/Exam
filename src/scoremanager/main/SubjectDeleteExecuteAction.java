package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
<<<<<<< HEAD

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // フォームから送信された科目コードを取得
        String cd = req.getParameter("cd");

        // 学校情報を取得（仮定）
        School school = new School(); // ここに適切な学校情報の取得処理を記述

        // 科目Daoを初期化
        SubjectDao subjectDao = new SubjectDao();

        // 科目を取得
        Subject subject = subjectDao.get(cd, school);

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
=======
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

		String subjectNo=""; //送付された科目番号の受け取り先
		Subject thisSubject = new Subject();
		SubjectDao subDao = new SubjectDao();
		boolean end = false;

		// 科目情報取得
		thisSubject=subDao.get(subjectNo, teacher.getSchool());

		// リクエストパラメータの取得
		subjectNo=req.getParameter("selected_subject_cd");
		end=subDao.delete(thisSubject);

		// フォワード
		if(end){req.getRequestDispatcher("subjects_delete_done.jsp").forward(req, res);}
		else{
			System.out.println("★削除に失敗しました");
			req.getRequestDispatcher("menu.jsp").forward(req, res);
		}
	}
>>>>>>> branch 'master' of https://github.com/bird8103/Exam.git
}
