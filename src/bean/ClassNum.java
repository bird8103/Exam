package bean;

import java.io.Serializable;

public class ClassNum implements Serializable {
	/**
	 * クラス番号:String
	 */
	private String classNum;
	/**
	 * 学校コード:School
	 */
	private School school;

	public String getClassNum(){
		return classNum;
	}
	public void setClassNum(String classNum){
		this.classNum=classNum;
	}
	public School getSchool(){
		return school;
	}
	public void setSchool(School school){
		this.school=school;
	}

}








