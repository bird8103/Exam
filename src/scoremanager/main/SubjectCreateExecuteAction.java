//package scoremanager.main;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import bean.Teacher;
//import dao.SubjectDao;
//import tool.Action;
//
//public class SubjectCreateExecuteAction extends Action{
//	@Override
//	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
//		HttpSession session = req.getSession(); // セッション
//		Teacher teacher =(Teacher)session.getAttribute("user");
//
//		String subjectName=""; // 入力された科目名
//		String subjectCd=""; // 入力された科目コード
//		String studentName=""; // 入力された氏名
//		String classNum=""; // 入力されたクラス番号
//		int subjectNN=0; // 入学年度
//		Student makeSubject=new Subject(); // 送信用生徒情報
//		Student isSubject=null; // 確認用生徒情報
//		SubjectDao sDao = new SubjectDao(); // 学生Dao
//		Map<String,String> errors = new HashMap<>(); // エラーメッセージ
//
//
//		// リクエストパラメータの取得
//		subjectName=req.getParameter("subject_name");
//		subjectCd=req.getParameter("cd");
////		studentName=req.getParameter("name");
////		classNum=req.getParameter("class_num");
//
//		// 入学年度の同期
//		if (subjectName!=null){
//			subjectNN=Integer.parseInt(subjectName);
//		}
//
//		// 学生番号重複確認
//		isSubject=sDao.get(subjectCd);
//
//
//		if (isSubject!=null && subjectName.equals("0")){
//			System.out.println("★リスト未選択かつ学生番号重複");
//			errors.put("f1","入学年度を選択してください");
//			errors.put("f2","学生番号が重複しています");
//			req.setAttribute("errors", errors);
//			req.setAttribute("no", subjectCd);
//			req.setAttribute("name", subjectName);
//			req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
//		}
//		else if (subjectName.equals("0")){
//			System.out.println("★リスト未選択");
//			errors.put("f1","入学年度を選択してください");
//			req.setAttribute("errors", errors);
//			req.setAttribute("no", subjectCd);
//			req.setAttribute("name", studentName);
//			req.getRequestDispatcher("StudentCreate.action").forward(req, res);
//		}
//		else if (isSubject!=null){
//			System.out.println("★学生番号重複");
//			errors.put("f2","学生番号が重複しています");
//			req.setAttribute("errors", errors);
//			req.setAttribute("no", subjectCd);
//			req.setAttribute("name", studentName);
//			req.getRequestDispatcher("StudentCreate.action").forward(req, res);
//		}
//		else if (isSubject==null){
//			System.out.println("★リスト選択OK、学生番号重複無し");
//			// 送信用の生徒情報の作成
//			makeSubject.setNo(subjectCd);
//			makeSubject.setName(studentName);
//			makeSubject.setEntYear(subjectNN);
//			makeSubject.setClassNum(classNum);
//			makeSubject.setAttend(true);
//			makeSubject.setSchool(teacher.getSchool());
//			// 送信
//			boolean end = sDao.save(makeSubject);
//
//			if(end){req.getRequestDispatcher("student_create_done.jsp").forward(req, res);}
//			else{
//				System.out.println("★登録に失敗しました");
//				req.setAttribute("no", subjectCd);
//				req.setAttribute("name", studentName);
//				req.getRequestDispatcher("SCreate.action").forward(req, res);
//			}
//
//		}
//	}
//}



package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(); // セッション
        Teacher teacher = (Teacher) session.getAttribute("user");

        String subjectName = ""; // 入力された科目名
        String subjectCd = ""; // 入力された科目コード
        Subject makeSubject = new Subject(); // 送信用科目情報
        SubjectDao sDao = new SubjectDao(); // 科目Dao
        Map<String, String> errors = new HashMap<>(); // エラーメッセージ

        // リクエストパラメータの取得
        subjectName = req.getParameter("subject_name");
        subjectCd = req.getParameter("cd");

        // 科目コード重複確認
        Subject isSubject = sDao.get(subjectCd, null);

        // エラーチェック
        if (isSubject != null && subjectName.equals("0")) {
            System.out.println("★リスト未選択かつ科目コード重複");
            errors.put("f1", "科目名を入力してください");
            errors.put("f2", "科目コードが重複しています");
            req.setAttribute("errors", errors);
            req.setAttribute("no", subjectCd);
            req.setAttribute("name", subjectName);
            req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
        } else if (subjectName.equals("0")) {
            System.out.println("★リスト未選択");
            errors.put("f1", "科目名を入力してください");
            req.setAttribute("errors", errors);
            req.setAttribute("no", subjectCd);
            req.setAttribute("name", subjectName);
            req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
        } else if (isSubject != null) {
            System.out.println("★科目コード重複");
            errors.put("f2", "科目コードが重複しています");
            req.setAttribute("errors", errors);
            req.setAttribute("no", subjectCd);
            req.setAttribute("name", subjectName);
            req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
        } else if (isSubject == null) {
            System.out.println("★リスト選択OK、科目コード重複無し");
            // 送信用の科目情報の作成
            makeSubject.setCd(subjectCd);
            makeSubject.setName(subjectName);
            makeSubject.setSchool(teacher.getSchool());
            // 送信
            boolean end = sDao.save(makeSubject);

            if (end) {
                req.getRequestDispatcher("student_create_done.jsp").forward(req, res);
            } else {
                System.out.println("★登録に失敗しました");
                req.setAttribute("no", subjectCd);
                req.setAttribute("name", subjectName);
                req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
            }
        }
    }
}











