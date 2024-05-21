package scoremanager.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Test;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@SuppressWarnings({"unchecked" })
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession session = req.getSession(); // セッション

		String pointNumStr="";
		int pointNum=0;
		Test test = new Test();
		Map<String,String> errors = new HashMap<>();// エラーメッセージ
		TestDao testDao = new TestDao();

		List<Test>  testScore = new ArrayList<>(); //更新・追加用

		List<Test>  testData = new ArrayList<>();
		List<Student> students = new ArrayList<>();   //受信用

        try {

        	testData = (List<Test>)session.getAttribute("test_data");
        	System.out.println("testData=" + testData);
        	students = (List<Student>)session.getAttribute("student_data");
        	System.out.println("students=" + students);

        	System.out.println("処理開始");

//        	1件ごとに処理
        	 for(int i = 0; i < testData.size(); i++){
        		 Test rTest = new Test(); //初期化
        		test = testData.get(i);      //データ取得

        		String stuNo = students.get(i).getNo(); //生徒番号取得
            	pointNumStr = req.getParameter("point_" + stuNo);

        		System.out.println("pointNum変換:" + pointNumStr);
        		System.out.println("point_" + stuNo);

        		//点数が入力されていた場合型変換
        		if(pointNumStr != ""){
            		System.out.println("intに変換");
                    pointNum = Integer.parseInt(pointNumStr);
        		}

//        		入力された値が正しくない場合[0～100の範囲で入力してください]と表示
            	if (pointNum > 100 && pointNumStr != null | pointNum < 0 && pointNumStr != null){
            		errors.put("point","0～100の範囲で入力してください");
            		req.setAttribute("errors", errors);
            	} else{
//            		得点が入力されていた場合のみ
            		if(pointNumStr != ""){
                		 rTest.setPoint(pointNum);
            		}
            		rTest.setNo(test.getNo());
            		rTest.setClassNum(test.getClassNum());
            		rTest.setSubject(test.getSubject());
            		rTest.setStudent(test.getStudent());
            		rTest.setSchool(test.getSchool());

            		System.out.println(rTest.getPoint());

            		testScore.add(rTest);
            	}
        	}

      		System.out.println("list作成完了");

//         	入力された値のリストをまとめてDBに保存する
         	if(testDao.save(testScore) == true && testScore.size() ==testData.size()){
//         		保存できた場合完了画面に遷移
         		System.out.println("DBへの保存に成功しました");
             	req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
         	} else{
//         		できなかった場合は遷移しない
         		System.out.println("DBへの保存に失敗しています");
               	req.getRequestDispatcher("TestRegist.action").forward(req, res);
         	}

//			数字以外が入力された場合
        } catch (NumberFormatException numberFormatException) {
    		errors.put("point","0～100の範囲で入力してください");
    		req.setAttribute("errors", errors);
    		System.out.println("戻る");
    		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
//    		Connection is null. (ただし更新はできる)の場合
    	} catch (SQLException sqlexe){
    		System.out.println("いつもの");
         	req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
    	}
	}
}