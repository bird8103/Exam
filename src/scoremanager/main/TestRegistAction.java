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
import dao.TestDao;
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

		//初期化
		List<String> list=null;
		List<Subject> subjectList=null;

		try{
//			以前のsessionデータが残っていた場合念のため削除
			if(session.getAttribute("test_data") != null | session.getAttribute("student_data") != null){
				System.out.println("前の履歴を削除");
				session.removeAttribute("test_data");
				session.removeAttribute("student_data");
				session.removeAttribute("subject_name");
				session.removeAttribute("test_no");
				session.removeAttribute("f1");
				session.removeAttribute("f2");
				session.removeAttribute("f3");
				session.removeAttribute("f4");
			}

			list=cNumDao.filter(teacher.getSchool());
			subjectList=subjectDao.filter(teacher.getSchool());

			List<Integer> numSet= new ArrayList<>();
			for (int i = 1; i < 3; i++){
				numSet.add(i);
			}

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
			req.setAttribute("num_set", numSet);
			req.setAttribute("start", false);

//			このページを呼び出した時の処理
//			既に何らかが入力されている場合にそれに応じた処理を行う
			TestRequestData(req, res);

		} catch (NullPointerException e ){
			System.out.println("入力されていない項目を呼び出しています");
			req.setAttribute("start", true);
			req.getRequestDispatcher("test_regist.jsp").forward(req, res);
		}
	}

	private void TestRequestData(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher =(Teacher)session.getAttribute("user");

		String entYearStr ="";
		String classNum ="";
		String subjectName ="";
		String numOfTime="";
		int entYear=0;
		int num=0;
		boolean deployment = false;
		List<Test> testList = new ArrayList<>();
		List<Test> tests = new ArrayList<>();

		List<Student> studentList = new ArrayList<>();
		Subject subject = new Subject();
		TestDao testDao = new TestDao();
		StudentDao studentDao = new StudentDao();
		SubjectDao subjectDao = new SubjectDao();
		Map<String,String> errors = new HashMap<>(); // エラーメッセージ

//		入力値の確認
		// リクエストパラメータの取得
		entYearStr=req.getParameter("f1");
		classNum=req.getParameter("f2");
		subjectName=req.getParameter("f3");
		numOfTime=req.getParameter("f4");

//		入力値の型変換 直した
		subject=subjectDao.get(subjectName, teacher.getSchool());

		if (entYearStr != null && !classNum.equals("0") && subject != null && !numOfTime.equals("0")){
			entYear=Integer.parseInt(entYearStr);
			num=Integer.parseInt(numOfTime);
			System.out.println("変換OK");
		// 成績管理一覧で表示するために必要なデータを取得
			//得点取得
			tests = testDao.filter(entYear, classNum, subject, num, teacher.getSchool());
			System.out.println(tests);

			//生徒数分用意(在学していない生徒を含む)
			studentList = studentDao.filter(teacher.getSchool(), entYear, classNum, false);
			System.out.println("取得OK");
			System.out.println("List : " + studentList.size());

//				生徒テーブルと照らし合わせ

			for(int j = 0; j < studentList.size(); j++){
				Test test = new Test();
				test.setNo(num);
				test.setClassNum(classNum);
				test.setStudent(studentList.get(j));
				test.setSubject(subject);
				test.setSchool(teacher.getSchool());

				for(int i = 0; i < tests.size(); i++){
					Test tes = tests.get(i);
					//すでに得点テーブルに存在している場合
					if(tes.getStudent().getNo() == studentList.get(j).getNo() && tes.getPoint() >= 0 ){
						System.out.println(tes.getPoint());
						System.out.println("すでに得点が入力されています");
						test.setPoint(tes.getPoint());
					}
				}

				testList.add(test);
				System.out.println("loop : " + j + studentList.get(j).getName());
			}

			System.out.println("セット完了");

			// 値セット
			session.setAttribute("test_data", testList);
			session.setAttribute("student_data", studentList);

			session.setAttribute("subject_name", subject);
			session.setAttribute("test_no", num);

			session.setAttribute("f1", entYear);
			session.setAttribute("f2", classNum);
			session.setAttribute("f3", subjectName);
			session.setAttribute("f4", num);

			deployment = true;

		}else if (entYearStr != "" | !classNum.equals("0") | subject != null | !numOfTime.equals("0")){
//				入学年度、クラス、科目、回数のいずれかが未入力の場合[入学年度とクラスと科目と回数を選択してください]と表示
			errors.put("all", "入学年度とクラスと科目と回数を選択してください");
			req.setAttribute("errors", errors);
		}

		req.setAttribute("dep", deployment);
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}
