package bean;

import java.io.Serializable;

public class TestListStudent implements Serializable {

	//科目名
	private String subjectName;

	//科目コード
	private String subjectCd;

	//番号
	private int num;

	//得点
	private int point;


//ゲッター、セッター
	public String getSubjectNme(){
		return subjectName;
	}
	public void setSbjectName(String subjectName){
		this.subjectName=subjectName;
	}
	public String getSubjectCd(){
		return subjectCd;
	}
	public void setSbjectCd(String subjectCd){
		this.subjectCd=subjectCd;
	}
	public int getNum(){
		return num;
	}
	public void setNum(int num){
		this.num=num;
	}
	public int getpoint(){
		return point;
	}
	public void setPoint(int point){
		this.point=point;
	}
	}
	
	
	
