package dao;

import java.util.List;

import javax.security.auth.Subject; // コレは使わないインポートなので後で削除

//import bean.Subject;

import bean.School;

public class SubjectDao extends Dao{

	public Subject get(String cd, School school) throws Exception{

	}
	public List<Subject> filter(School school) throws Exception{

	}
	public boolean save(Subject subject) throws Exception{

	}
	public boolean delete(Subject subject) throws Exception{
		// subjectテーブルに論理削除用のフラグを記録するためのbooleanカラムを用意しておくこと
	}
}
