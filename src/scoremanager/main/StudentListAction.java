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
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentListAction extends Action{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher =(Teacher)session.getAttribute("user");

		String entYearStr=""; // 入力された入学年度
		String classNum=""; // 入力されたクラス番号
		String isAttendStr=""; // 入力された在学フラグ
		int entYear=0; // 入学年度
		boolean isAttend=false; // 在学フラグ
		List<Student> students =null; // 学生リスト
		LocalDate todaysDate = LocalDate.now(); //LocalDateインスタンス取得
		int year =todaysDate.getYear(); // 現在の年を取得
		StudentDao sDao = new StudentDao(); // 学生Dao
		ClassNumDao cNumDao = new ClassNumDao(); // クラス番号Dao
		Map<String,String> errors = new HashMap<>(); // エラーメッセージ

		// リクエストパラメータの取得
		entYearStr=req.getParameter("f1");
		classNum=req.getParameter("f2");
		isAttendStr=req.getParameter("f3");

		// 入学年度の同期
		if (entYearStr!=null){
			entYear=Integer.parseInt(entYearStr);
		}
		// 在学フラグの同期
		if (isAttendStr!=null && isAttendStr.equals("t")){
			isAttend=true;
		}

		// クラス番号取得
		List<String> list=cNumDao.filter(teacher.getSchool());


		// DBからデータ取得
		if (entYear != 0 && !classNum.equals("0")){
			// 入学年度とクラス番号を指定
			students = sDao.filter(teacher.getSchool(),entYear,classNum,isAttend);
		}
		else if (entYear != 0 && classNum.equals("0")){
			// 入学年度のみ指定
			students = sDao.filter(teacher.getSchool(),entYear,isAttend);
		}
		else if (entYear==0 && classNum ==null || entYear==0 &&classNum.equals("0")){
			// 指定無しの場合。全学生情報を取得
			students=sDao.filter(teacher.getSchool(),isAttend);
		}
		else{
			errors.put("f1","クラスを指定する場合は入学年度も指定してください");
			req.setAttribute("errors", errors);
			// 全学生情報を取得
			students=sDao.filter(teacher.getSchool(),isAttend);
		}

		// ビジネスロジック
		// 入学年度のInt型への変換など
		if (entYearStr != null){
			// 数値に変換
			entYear=Integer.parseInt(entYearStr);
		}
		// リストを初期化
		List<Integer> entYearSet=new ArrayList<>();
		// 10年前から1年後までの年をリストに追加
		for (int i = year-10;i<year+1;i++){
			entYearSet.add(i);
		}

		// 学生一覧で表示するために必要なデータをリクエストにセット
		// レスポンス値セット
		req.setAttribute("f1", entYear); // 入学年度
		req.setAttribute("f2", classNum); // クラス番号
		// 在学フラグが送信されていた場合
		if (isAttendStr != null){
			// 在学フラグを立てる
			isAttend=true;
			req.setAttribute("f3", isAttendStr); // 在学フラグ
		}
		req.setAttribute("students", students); // 学生リスト
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);

		// JSPへフォワード
		req.getRequestDispatcher("student_list.jsp").forward(req, res);





	}
}
