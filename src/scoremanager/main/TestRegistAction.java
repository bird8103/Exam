package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher =(Teacher)session.getAttribute("user");

		String entYearStr ="";
		String classNum ="";
		String subject ="";
		String numOfTime="";

		int entYear=0;
		LocalDate todaysDate = LocalDate.now(); //LocalDateインスタンス取得
		int year =todaysDate.getYear(); // 現在の年を取得
		ClassNumDao cNumDao = new ClassNumDao(); // クラス番号Dao
//		SubjectDao subjectDao = new SubjectDao(); //科目Dao
//		TestDao testDao = new TestDao();
		StudentDao studentDao = new StudentDao();

		// リクエストパラメータの取得 要るかわからん
		entYearStr=req.getParameter("f1");
		classNum=req.getParameter("f2");
		subject=req.getParameter("f3");
		numOfTime=req.getParameter("f4");


		// 入学年度の同期
		if (entYearStr!=null){
			entYear=Integer.parseInt(entYearStr);
		}

		List<String> list=cNumDao.filter(teacher.getSchool());
//		List<String> subjectList=subjectDao.filter(teacher.getSchool());
//		List<String> numOfTimeSet=testDao.filter(teacher.getSchool(), subject);

		// リストを初期化
		List<Integer> entYearSet=new ArrayList<>();
		// 10年前から1年後までの年をリストに追加
		for (int i = year-10;i<year+1;i++){
			entYearSet.add(i);
		}

		// データをリクエストにセット
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);
//		req.setAttribute("subject_set", subjectList);
//		req.setAttribute("num_of_time", numOfTimeSet);

		// JSPへフォワード
		req.getRequestDispatcher("student_regist.jsp").forward(req, res);
	}

	private void TestRequestData(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String entYearStr ="";
		String classNum ="";
		String subject ="";
		String numOfTime="";
		int entYear=0;
		boolean deployment = false;
		Map<String,String> errors = new HashMap<>(); // エラーメッセージ

//		入力値の確認
		// リクエストパラメータの取得 要るかわからん
		entYearStr=req.getParameter("f1");
		classNum=req.getParameter("f2");
		subject=req.getParameter("f3");
		numOfTime=req.getParameter("f4");
		entYear=Integer.parseInt(entYearStr);

//		入学年度、クラス、科目、回数のいずれかが未入力の場合[入学年度とクラスと科目と回数を選択してください]と表示
		if (entYearStr != null && !classNum.equals("0") && subject != null && !numOfTime.equals("0")){
		// 成績管理一覧で表示するために必要なデータをリクエストにセット
		// レスポンス値セット
		req.setAttribute("f1", entYear); // 入学年度
		req.setAttribute("f2", classNum); // クラス番号
		req.setAttribute("f3", subject); // 科目
		req.setAttribute("f4", numOfTime); // 回数
		deployment = true;
		}
		else {
			errors.put("all", "入学年度とクラスと科目と回数を選択してください");
			req.setAttribute("errors", errors);
		}
		req.setAttribute("dep", deployment);
	}
}
