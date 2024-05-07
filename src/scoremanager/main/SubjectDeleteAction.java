package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(); // セッション
        Teacher teacher = (Teacher) session.getAttribute("user");

        String subjectCode = req.getParameter("code"); // 削除する科目コード

        SubjectDao subjectDao = new SubjectDao(); // 科目Dao

        // 削除対象の科目情報を取得
        Subject deleteSubject = subjectDao.get(subjectCode, teacher.getSchool());

        if (deleteSubject != null) {
            // 科目が存在する場合は削除を試みる
            boolean success = subjectDao.delete(deleteSubject);

            if (success) {
                req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
            } else {
                System.out.println("削除に失敗しました");
                req.setAttribute("code", subjectCode);
                req.getRequestDispatcher("SubjectList.action").forward(req, res);
            }
        } else {
            // 科目が見つからない場合
            System.out.println("指定された科目が見つかりません");
            req.setAttribute("code", subjectCode);
            req.getRequestDispatcher("SubjectList.action").forward(req, res);
        }
    }
}
