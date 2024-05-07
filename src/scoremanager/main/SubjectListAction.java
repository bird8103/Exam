package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(); // セッション
        Teacher teacher = (Teacher) session.getAttribute("user");

        String subjectName = ""; // 入力された科目名前
        String subjectCd = ""; // 入力された科目コード
        String isAttendStr = ""; // 入力された在学フラグ
        int subjectNN = 0; // 入学年度
        boolean isAttend = false; // 在学フラグ
        List<Subject> subjects = null; // 学生リスト
        LocalDate todaysDate = LocalDate.now(); //LocalDateインスタンス取得
        int year = todaysDate.getYear(); // 現在の年を取得
        SubjectDao sDao = new SubjectDao(); // 学生Dao
        ClassNumDao cNumDao = new ClassNumDao(); // クラス番号Dao
        Map<String, String> errors = new HashMap<>(); // エラーメッセージ

        // リクエストパラメータの取得
        subjectName = req.getParameter("f1");
        subjectCd = req.getParameter("f2");
        isAttendStr = req.getParameter("f3");

        // クラス番号取得
        List<String> list = cNumDao.filter(teacher.getSchool());

        // DBからデータ取得
        if (subjectName != null && !subjectCd.equals("0")) {
            // 入学年度とクラス番号を指定
            subjects = sDao.filter(teacher.getSchool());
        } else if (subjectNN == 0 && subjectCd == null || subjectNN == 0 && subjectCd.equals("0")) {
            // 指定無しの場合。全学生情報を取得
            subjects = sDao.filter(teacher.getSchool());
        } else {
            errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
            req.setAttribute("errors", errors);
            // 全学生情報を取得
            subjects = sDao.filter(teacher.getSchool());
        }

        // ビジネスロジック
        // 入学年度のInt型への変換など
        if (subjectName != null) {
            // 数値に変換
            subjectNN = Integer.parseInt(subjectName);
        }
        // リストを初期化
        List<Integer> subjectNameSet = new ArrayList<>();
        // 10年前から1年後までの年をリストに追加
        for (int i = year - 10; i < year + 1; i++) {
            subjectNameSet.add(i);
        }

        // 学生一覧で表示するために必要なデータをリクエストにセット
        // レスポンス値セット
        req.setAttribute("f1", subjectName); // 入学年度
        req.setAttribute("f2", subjectCd); // クラス番号
        // 在学フラグが送信されていた場合
        if (isAttendStr != null && isAttendStr.equals("t")) {
            // 在学フラグを立てる
            isAttend = true;
            req.setAttribute("f3", isAttendStr); // 在学フラグ
        }
        req.setAttribute("subjects", subjects); // 学生リスト
        req.setAttribute("subject_cd_set", list);
        req.setAttribute("subject_name_set", subjectNameSet);

        // JSPへフォワード
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}
