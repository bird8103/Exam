package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateAction extends Action{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher =(Teacher)session.getAttribute("user");



		SubjectDao subDao = new SubjectDao(); // 科目Dao
		List<Subject> list = subDao.filter(teacher.getSchool());

		// リストを初期化
		List<Integer> subjectNameSet=new ArrayList<>();
//		// 10年前から1年後までの年をリストに追加
//		for (int i = year-10;i<year+1;i++){
//			entYearSet.add(i);
//		}

		// データをリクエストにセット
		req.setAttribute("subject_cd_set", list);
		req.setAttribute("subject_name_set", subjectNameSet);

		// JSPへフォワード
		req.getRequestDispatcher("subject_create.jsp").forward(req, res);
	}
}
