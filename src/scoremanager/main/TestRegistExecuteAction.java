package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Test;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@SuppressWarnings({ "null", "unchecked" })
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
//		HttpSession session = req.getSession(); // セッション
//		Teacher teacher =(Teacher)session.getAttribute("user");

		String pointNumStr="";
		int pointNum=0;
		Map<String,String> errors = new HashMap<>();// エラーメッセージ
		TestDao testDao = new TestDao();
//		StudentDao studentDao = new StudentDao();
		boolean pointNull = false;
//		Test test = new Test();

		List<Test>  testScore = null; //更新・追加用
		List<Test> students = null;   //受信用
        try {
//        	studentsに検索結果に応じたtestを保存
        	students = (List<Test>)req.getAttribute("test_result");
//        	1件ごとに処理
        	for (Test test : students){
            	pointNumStr = req.getParameter("point_" + test.getNo());

        		if(pointNumStr != null){
                    pointNum = Integer.parseInt(pointNumStr);//[102]
        		} else {
        			pointNull = true;
        		}

//        		入力された値が正しくない場合[0～100の範囲で入力してください]と表示
            	if (pointNum < 100 && pointNull == false || pointNum < 0 && pointNull == false){
            		errors.put("point","0～100の範囲で入力してください");
            		req.setAttribute("errors", errors);
            	} else{
            		((Test) testScore).setStudent(test.getStudent());
            		((Test) testScore).setClassNum(test.getClassNum());
            		((Test) testScore).setSubject(test.getSubject());
            		((Test) testScore).setSchool(test.getSchool());
            		((Test) testScore).setNo(test.getNo());
//            		得点が入力されていた場合のみ
            		if(pointNull == false){
                		((Test) testScore).setPoint(pointNum);
            		}
            	}
        	}

//        	入力された値のリストをまとめてDBに保存する
        	if(testDao.save(testScore) == true){
            	req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
        	} else{
              	req.getRequestDispatcher("test_regist.jsp").forward(req, res);
        	}

//			数字以外が入力された場合
        } catch (NumberFormatException numberFormatException) {
    		errors.put("point","0～100の範囲で入力してください");
    		req.setAttribute("errors", errors);
    		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    	}
	}
}