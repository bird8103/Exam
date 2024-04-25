package bean;

import java.io.Serializable;

public class Subject implements Serializable {

	//科目コード
	private String cd;

	//科目名
	private String name;

	//学校コード
	private School school;


	//ゲッター、セッター
	public String getCd(){
		return cd;
	}
	public void setcd(String cd){
		this.cd=cd;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	public School getSchool(){
		return school;
	}
	public void setSchool(School school){
		this.school=school;
	}
	}