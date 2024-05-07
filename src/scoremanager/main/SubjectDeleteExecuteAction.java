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
}
