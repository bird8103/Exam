package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;


public class SubjectDeleteAction extends Action{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher =(Teacher)session.getAttribute("user");

		String subjectNo=""; //送付された科目番号の受け取り先
		Subject thisSubject = new Subject();
		SubjectDao subDao = new SubjectDao();

		// リクエストパラメータの取得
		subjectNo=req.getParameter("no");

		// 科目情報取得
		thisSubject=subDao.get(subjectNo, teacher.getSchool());

		// リクエストパラメータのセット
		req.setAttribute("selected_subject_name", thisSubject.getName());
		req.setAttribute("selected_subject_cd", thisSubject.getCd());
		// フォワード
		req.getRequestDispatcher("subjects_delete.jsp").forward(req, res);

	}
}
