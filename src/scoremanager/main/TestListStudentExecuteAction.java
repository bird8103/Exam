package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		String no = req.getParameter("f4");
		String f = req.getParameter("f");
		TestListStudentDao TLSD = new TestListStudentDao();
		Student Student = new Student();
		Student.setNo(no);
		List<TestListStudent> result = TLSD.filter(Student);
		List<ArrayList> A = new ArrayList<>();
		StudentDao StDao = new StudentDao();
		Student number = StDao.get(no);
		Map<String,String> errors =new HashMap<>();
		List<Subject> subject_set = null;
		SubjectDao SubDao = new SubjectDao();
		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		ClassNumDao cDao = new ClassNumDao();
		List<String> class_num_list = null;
		class_num_list = cDao.filter(teacher.getSchool());
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i < year + 11; i++){
			entYearSet.add(i);
		}
		subject_set = SubDao.filter(teacher.getSchool());
		req.setAttribute("class_num_set", class_num_list);
		req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("subject_set", subject_set);
		req.setAttribute("subject", A);
		req.setAttribute("result", result);
		req.setAttribute("f4", no);
		req.setAttribute("error", "false");
		try{
			req.setAttribute("student_name", number.getName());
			req.setAttribute("student_no", number.getNo());
			if (result == null){
				req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
			}
		req.setAttribute("student_class", number.getClass());
		req.setAttribute("student_no", number.getNo());
		req.setAttribute("student_no", number.getNo());
			req.setAttribute("f", f);
			req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
		}catch(Exception e){
			req.setAttribute("error", "true");
			req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
		}
	}


}