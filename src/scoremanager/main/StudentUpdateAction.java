package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateAction extends Action{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher =(Teacher)session.getAttribute("user");

		String studentNo=""; // 受信した学生番号
		StudentDao sDao = new StudentDao(); // 学生Dao
		Student thisStudent=null; // 生徒情報受け取り用
		ClassNumDao cNumDao = new ClassNumDao(); // クラス番号Dao
		List<String> list=cNumDao.filter(teacher.getSchool());

		// リクエストパラメータの取得
		studentNo=req.getParameter("no");

		// 学生情報の取得
		thisStudent=sDao.get(studentNo);

		// リクエストパラメータに情報をセット
		req.setAttribute("ent_year", thisStudent.getEntYear());
		req.setAttribute("no", studentNo);
		req.setAttribute("name", thisStudent.getName());
		req.setAttribute("attend", thisStudent.isAttend());
		req.setAttribute("class_num", thisStudent.getClassNum());
		req.setAttribute("class_num_set", list);

		// JSPへフォワード
		req.getRequestDispatcher("student_update.jsp").forward(req, res);
	}
}
