package bean;

import java.io.Serializable;

public class Test implements Serializable {

	//生徒名
	private Student student;

	//クラスコード
	private String classNum;

	//科目コード
	private Subject subject;

	//学校コード
	private School school;

	//番号
	private int no;

	//得点
	private int point;

	//ゲッター、セッター
	public Student getStudent(){
		return student;
	}
	public void setStudent(Student student){
		this.student=student;
	}
	public String getClassNum(){
		return classNum;
	}
	public void setClassNum(String classNum){
		this.classNum=classNum;
	}
	public Subject getSubject(){
		return subject;
	}
	public void setSubject(Subject subject){
		this.subject=subject;
	}
	public School getSchool(){
		return school;
	}
	public void setSchool(School school){
		this.school=school;
	}
	public int getNo(){
		return no;
	}
	public void setNo(int no){
		this.no=no;
	}
	public int getPoint(){
		return point;
	}
	public void setPoint(int point){
		this.point=point;
	}
}