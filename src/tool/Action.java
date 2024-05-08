package tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//あ
public abstract class Action {

	public abstract void execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception;

}
