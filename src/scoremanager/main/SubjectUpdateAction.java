package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(); // セッション
        Teacher teacher = (Teacher) session.getAttribute("user");

        String subjectCode = ""; // 受信した科目コード
        SubjectDao subjectDao = new SubjectDao(); // 科目Dao
        Subject thisSubject = null; // 科目情報受け取り用

        // リクエストパラメータの取得
        subjectCode = req.getParameter("no");

        // 科目情報の取得
        thisSubject = subjectDao.get(subjectCode, teacher.getSchool());

        // リクエストパラメータに情報をセット
        req.setAttribute("code", subjectCode);
        req.setAttribute("name", thisSubject.getName());

        // JSPへフォワード
        req.getRequestDispatcher("subject_update.jsp").forward(req, res);
    }
}

