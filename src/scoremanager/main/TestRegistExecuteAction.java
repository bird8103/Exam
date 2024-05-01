package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import bean.Test;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher =(Teacher)session.getAttribute("user");

		String pointNumStr="";
		int pointNum=0;
		Map<String,String> errors = new HashMap<>();// エラーメッセージ
//		TestDao testDao = new TestDao();

		List<Test>  testScore = null;
		List<String> PointNumSet=new ArrayList<>();


        try {
        	pointNumStr = req.getParameter("test-num");
            pointNum = Integer.parseInt(pointNumStr);//[102]
//    		入力された値が正しくない場合[0～100の範囲で入力してください]と表示

    		if (pointNum < 100 || pointNum < 0){
    			errors.put("point","0～100の範囲で入力してください");
    			req.setAttribute("errors", errors);
    		}
//    		else if(pointNum = null){
//
//    		}
    		else{

//    			入力された値をDBに保存する
//    			testDao.save(testScore);
    		}

//    		jspを表示
    		req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);

        } catch (NumberFormatException numberFormatException) {
    		errors.put("point","0～100の範囲で入力してください");
    		req.setAttribute("errors", errors);

    	}
	}
}