package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher =(Teacher)session.getAttribute("user");

		String pointNumStr="";
		int pointNum=0;
		Map<String,String> errors = new HashMap<>(); // エラーメッセージ
//		List<Test>  testScore = null;


//		入力された値が正しくない場合[0～100の範囲で入力してください]と表示 -

		List<String> PointNumSet=new ArrayList<>();
		if (pointNum > 100 || pointNum < 0){
			errors.put("point","0～100の範囲で入力してください");
			req.setAttribute("errors", errors);
		}
		else if(pointNum != 0){

		}

//		入力された値をDBに保存する



//		jspを表示
		req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);;
	}

}
