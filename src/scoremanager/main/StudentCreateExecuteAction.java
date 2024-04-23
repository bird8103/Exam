package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentCreateExecuteAction extends Action{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher =(Teacher)session.getAttribute("user");

		String entYearStr=""; // 入力された入学年度
		String studentNo=""; // 入力された学生番号
		String studentName=""; // 入力された氏名
		String classNum=""; // 入力されたクラス番号
		int entYear=0; // 入学年度
		Student makeStudent=new Student(); // 送信用生徒情報
		Student isStudent=null; // 確認用生徒情報
		StudentDao sDao = new StudentDao(); // 学生Dao
		Map<String,String> errors = new HashMap<>(); // エラーメッセージ


		// リクエストパラメータの取得
		entYearStr=req.getParameter("ent_year");
		studentNo=req.getParameter("no");
		studentName=req.getParameter("name");
		classNum=req.getParameter("class_num");

		// 入学年度の同期
		if (entYearStr!=null){
			entYear=Integer.parseInt(entYearStr);
		}

		// 学生番号重複確認
		isStudent=sDao.get(studentNo);


		if (isStudent!=null && entYearStr.equals("0")){
			System.out.println("★リスト未選択かつ学生番号重複");
			errors.put("f1","入学年度を選択してください");
			errors.put("f2","学生番号が重複しています");
			req.setAttribute("errors", errors);
			req.setAttribute("no", studentNo);
			req.setAttribute("name", studentName);
			req.getRequestDispatcher("StudentCreate.action").forward(req, res);
		}
		else if (entYearStr.equals("0")){
			System.out.println("★リスト未選択");
			errors.put("f1","入学年度を選択してください");
			req.setAttribute("errors", errors);
			req.setAttribute("no", studentNo);
			req.setAttribute("name", studentName);
			req.getRequestDispatcher("StudentCreate.action").forward(req, res);
		}
		else if (isStudent!=null){
			System.out.println("★学生番号重複");
			errors.put("f2","学生番号が重複しています");
			req.setAttribute("errors", errors);
			req.setAttribute("no", studentNo);
			req.setAttribute("name", studentName);
			req.getRequestDispatcher("StudentCreate.action").forward(req, res);
		}
		else if (isStudent==null){
			System.out.println("★リスト選択OK、学生番号重複無し");
			// 送信用の生徒情報の作成
			makeStudent.setNo(studentNo);
			makeStudent.setName(studentName);
			makeStudent.setEntYear(entYear);
			makeStudent.setClassNum(classNum);
			makeStudent.setAttend(true);
			makeStudent.setSchool(teacher.getSchool());
			// 送信
			boolean end = sDao.save(makeStudent);

			if(end){req.getRequestDispatcher("student_create_done.jsp").forward(req, res);}
			else{
				System.out.println("★登録に失敗しました");
				req.setAttribute("no", studentNo);
				req.setAttribute("name", studentName);
				req.getRequestDispatcher("StudentCreate.action").forward(req, res);
			}

		}
	}
}
