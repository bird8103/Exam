package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // フォームから送信された科目情報を取得
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");
        // 学校コードも取得する必要があるかもしれません

        // 科目インスタンスを作成して値をセット
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        // 学校コードのセットも必要かもしれません

        // SubjectDaoを使って科目を保存
        SubjectDao subjectDao = new SubjectDao();
        boolean success = subjectDao.save(subject);

        // 成功したかどうかをリクエスト属性にセット
        req.setAttribute("subjectCreated", success);

        // 成功または失敗に応じた遷移先のView名を返す
        if (success) {
            req.getRequestDispatcher("subject_create_success.jsp").forward(req, res); // 科目の作成に成功した場合のView名
        } else {
            req.getRequestDispatcher("subject_create_failure.jsp").forward(req, res); // 科目の作成に失敗した場合のView名
        }
    }
}
