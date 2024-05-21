package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import dao.TestListSubjectDao;
import tool.Action;
import tool.Util;

public class TestListAction extends Action{

	public void execute(HttpServletRequest req, HttpServletResponse res)throws Exception {
		String f = null;
		f = req.getParameter("f");
		Util util = new Util();
		Teacher teacher = util.getUser(req);
		ClassNumDao cDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		List<String> class_num_list = null;
		class_num_list = cDao.filter(teacher.getSchool());
		List<Subject> subject_set = null;
		subject_set = subjectDao.filter(teacher.getSchool());
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i < year + 11; i++){
			entYearSet.add(i);
		}
		req.setAttribute("class_num_set", class_num_list);
		req.setAttribute("subject_set", subject_set);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("f", f);
		if(f != null){
			if(f.equals("sj")) {
				setTestListSubject(req, res);
			} else if(f.equals("st")){
				setTestListStudent(req, res);
			}
		}
		req.getRequestDispatcher("test_list.jsp").forward(req, res);
	}

	private void setTestListSubject(HttpServletRequest req,HttpServletResponse res)throws Exception{
		int entYear = 0;
		String classNum = null;
		String subjectCd  = null;
		SubjectDao subjectDao = new SubjectDao();
		entYear = Integer.parseInt(req.getParameter("f1"));
		classNum = req.getParameter("f2");
		subjectCd = req.getParameter("f3");
		Util util = new Util();
		Teacher teacher = util.getUser(req);
		req.setAttribute("ent_year", entYear);
		req.setAttribute("classNum", classNum);
		req.setAttribute("cd", subjectCd);
		if (entYear != 0 && classNum != null && subjectCd != null){
			List<TestListSubject> list = new ArrayList<>();
			TestListSubjectDao tDao = new TestListSubjectDao();
			Subject subject = new Subject();
			subject = subjectDao.get(subjectCd, teacher.getSchool());
			School school = teacher.getSchool();
			list = tDao.filter(entYear, classNum, subject, school );
			req.setAttribute("subject_name", subject.getName());
			req.setAttribute("results", list);
		}else{
			req.setAttribute("subjectError", "入学年度とクラスと科目を選択してください");
			return;
		}
		req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
		return;
	}

	private void setTestListStudent(HttpServletRequest req,HttpServletResponse res)throws Exception{
		String studentNo = null;
		studentNo = req.getParameter("f4");
		req.setAttribute("f4", studentNo);
		if (studentNo != null){
			List<TestListStudent> list = new ArrayList<>();
			TestListStudentDao tDao = new TestListStudentDao();
			StudentDao sDao = new StudentDao();
			Student student = sDao.get(studentNo);
			list = tDao.filter(student);
			req.setAttribute("student", student);
			req.setAttribute("results", list);
		}else{
			return;
		}
		req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
		return;
	}


}