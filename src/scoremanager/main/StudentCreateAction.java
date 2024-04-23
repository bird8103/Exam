package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class StudentCreateAction extends Action{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher =(Teacher)session.getAttribute("user");

		LocalDate todaysDate = LocalDate.now(); //LocalDateインスタンス取得
		int year =todaysDate.getYear(); // 現在の年を取得

		ClassNumDao cNumDao = new ClassNumDao(); // クラス番号Dao
		List<String> list=cNumDao.filter(teacher.getSchool());

		// リストを初期化
		List<Integer> entYearSet=new ArrayList<>();
		// 10年前から1年後までの年をリストに追加
		for (int i = year-10;i<year+1;i++){
			entYearSet.add(i);
		}

		// データをリクエストにセット
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);

		// JSPへフォワード
		req.getRequestDispatcher("student_create.jsp").forward(req, res);
	}
}
