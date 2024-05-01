package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher =(Teacher)session.getAttribute("user");

		LocalDate todaysDate = LocalDate.now(); //LocalDateインスタンス取得
		int year =todaysDate.getYear(); // 現在の年を取得
		ClassNumDao cNumDao = new ClassNumDao(); // クラス番号Dao
		SubjectDao subjectDao = new SubjectDao(); //科目Dao
//		TestDao testDao = new TestDao();

		List<String> list=cNumDao.filter(teacher.getSchool());
		List<Subject> subjectList=subjectDao.filter(teacher.getSchool());
//		List<String> numSet=testDao.filter(teacher.getSchool(), subject);

		// リストを初期化
		List<Integer> entYearSet=new ArrayList<>();
		// 10年前から1年後までの年をリストに追加
		for (int i = year-10;i<year+1;i++){
			entYearSet.add(i);
		}

		// データをリクエストにセット
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("subject_set", subjectList);
//		req.setAttribute("num_set", numSet);

		// JSPへフォワード
		req.getRequestDispatcher("student_regist.jsp").forward(req, res);
		TestRequestData(req, res);
	}

	private void TestRequestData(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher =(Teacher)session.getAttribute("user");

		String entYearStr ="";
		String classNum ="";
		String subject ="";
		String numOfTime="";
		int entYear=0;
		boolean deployment = false;
		List<Test> test = null;
		List<Student> students = null;
//		TestDao testDao = new testDao();
		StudentDao studentDao = new StudentDao();
		Map<String,String> errors = new HashMap<>(); // エラーメッセージ

//		入力値の確認
		// リクエストパラメータの取得 要るかわからん
		entYearStr=req.getParameter("f1");
		classNum=req.getParameter("f2");
		subject=req.getParameter("f3");
		numOfTime=req.getParameter("f4");
		entYear=Integer.parseInt(entYearStr);

		if (entYearStr != null && !classNum.equals("0") && subject != null && !numOfTime.equals("0")){
		// 成績管理一覧で表示するために必要なデータを取得
//			test = testDao.filter(entYear, classNum, subject, numOfTime, teacher.getSchool());
//			students = studentDao.get();
		// レスポンス値セット
			req.setAttribute("subject_name", subject);
			req.setAttribute("test_no", numOfTime);
			req.setAttribute("students", students);
			deployment = true;
		}
		else {
//		入学年度、クラス、科目、回数のいずれかが未入力の場合[入学年度とクラスと科目と回数を選択してください]と表示
			errors.put("all", "入学年度とクラスと科目と回数を選択してください");
			req.setAttribute("errors", errors);
		}
		req.setAttribute("dep", deployment);
//		ここ暫定
		req.getRequestDispatcher("student_regist.jsp").forward(req, res);
	}
}
