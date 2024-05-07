package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import tool.Action;

public class LogoutAction extends Action{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher =(Teacher)session.getAttribute("user");

		// 認証済みフラグをfalseにする
		teacher.setAuthenticated(false);
		// セッション属性の全削除
		session.invalidate();

		// フォワード
		req.getRequestDispatcher("logout.jsp").forward(req, res);
	}
}
