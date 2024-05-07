package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(); // セッション
        Teacher teacher = (Teacher) session.getAttribute("user");

        String subjectCode = ""; // 入力された科目コード
        String subjectName = ""; // 入力された科目名
        Subject makeSubject = new Subject(); // 送信用科目情報
        SubjectDao subjectDao = new SubjectDao(); // 科目Dao

        // リクエストパラメータの取得
        subjectCode = req.getParameter("code");
        subjectName = req.getParameter("name");

        // 送信用の科目情報の作成
        makeSubject.setCd(subjectCode);
        makeSubject.setName(subjectName);
        makeSubject.setSchool(teacher.getSchool());

        // 送信
        boolean success = subjectDao.save(makeSubject);

        if (success) {
            req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
        } else {
            System.out.println("★登録に失敗しました");
            req.setAttribute("code", subjectCode);
            req.getRequestDispatcher("SubjectUpdate.action").forward(req, res);
        }
    }
}
