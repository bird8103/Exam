package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher =(Teacher)session.getAttribute("user");

		String entYearStr=""; // 入力された入学年度
		String studentNo=""; // 入力された学生番号
		String studentName=""; // 入力された氏名
		String classNum=""; // 入力されたクラス番号
		int entYear=0; // 入学年度
		boolean isAttend=false; // 入学フラグ
		Student makeStudent=new Student(); // 送信用生徒情報
		StudentDao sDao = new StudentDao(); // 学生Dao

		// リクエストパラメータの取得
		entYearStr=req.getParameter("ent_year");
		studentNo=req.getParameter("no");
		studentName=req.getParameter("name");
		classNum=req.getParameter("class_num");

		// 入学年度の同期
		if (entYearStr!=null){
			entYear=Integer.parseInt(entYearStr);
		}
		// 入学フラグの同期
		if (req.getParameter("si_attend")!=null){
			isAttend=true;
		}

		// 送信用の生徒情報の作成
		makeStudent.setNo(studentNo);
		makeStudent.setName(studentName);
		makeStudent.setEntYear(entYear);
		makeStudent.setClassNum(classNum);
		makeStudent.setAttend(isAttend);
		makeStudent.setSchool(teacher.getSchool());

		// 送信
		boolean end = sDao.save(makeStudent);

		if(end){req.getRequestDispatcher("student_update_done.jsp").forward(req, res);}
		else{
			System.out.println("★登録に失敗しました");
			req.setAttribute("no", studentNo);
			req.getRequestDispatcher("StudentUpdate.action").forward(req, res);
		}

	}
}

