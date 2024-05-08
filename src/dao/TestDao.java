package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;


public class TestDao extends Dao{
	private String baseSql ="select * from test where";

	public Test get(Student student,Subject subject,School school,int no) throws Exception{
		// Testインスタンスを初期化
		Test test = new Test();
		// DBへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement =null;
		String condition = " STUDENT_NO=? and SUBJECT_CD=? and SCHOOL_CD=? and NO=?";
		try{
			// プリペアードステートメントにSQLをセット
			statement=connection.prepareStatement(baseSql+condition);
			// プリペアードステートメントに各値をバインド
			statement.setString(1, student.getNo());
			statement.setString(2, subject.getCd());
			statement.setString(3, school.getCd());
			statement.setInt(4, no);
			// プリペアードステートメントを実行
			ResultSet rSet=statement.executeQuery();

			if (rSet.next()){
				// リザルトセットが存在する場合、
				// Testインスタンスに検索結果をセット
				test.setStudent(student);
				test.setSubject(subject);
				test.setSchool(school);
				test.setNo(no);
				test.setPoint(rSet.getInt("point"));
				test.setClassNum(rSet.getString("class_num"));

			}
			else{
				// リザルトセットが存在しない場合
				// Testインスタンスにnullをセット
				test = null;
			}
		}
		catch (Exception e){
			throw e;
		}
		finally{
			// プリペアードステートメントを閉じる
			if (statement != null){
				try{
					statement.close();
				}
				catch(SQLException sqle){
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null){
				try{
					connection.close();
				}
				catch(SQLException sqle){
					throw sqle;
				}
			}
		}
		return test;
	}
	private List<Test> postFilter(ResultSet rSet, School school) throws Exception{
		// リストを初期化
		List<Test> list=new ArrayList<>();
		StudentDao stuDao = new StudentDao();
		SubjectDao subDao = new SubjectDao();
		try {
			// リザルトセットを全権走査
			while(rSet.next()){
				Test test = new Test();
				test.setStudent(stuDao.get(rSet.getString("student_no")));
				test.setSubject(subDao.get(rSet.getString("subject_cd"), school));
				test.setSchool(school);
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				test.setClassNum(rSet.getString("class_num"));
				//リストに追加
				list.add(test);
			}
		}
		catch (SQLException | NullPointerException e){
			e.printStackTrace();
		}
		return list;
	}
	public List<Test> filter(int entYear, String class_num, Subject subject, int num, School school) throws Exception{
		// リストを初期化
		List<Test> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement =null;
		// リザルトセット
		ResultSet rSet=null;
		// 条件のSQL文
		String condition = "  SUBJECT_CD=? AND SCHOOL_CD=? AND  STUDENT_NO IN (SELECT NO FROM STUDENT WHERE ENT_YEAR=? AND CLASS_NUM=?)";
		// ソートのSQL文
		String order = " order by student_no asc";

		try{
			// プリペアードステートメントにSQLをセット
			statement=connection.prepareStatement(baseSql+condition+order);
			// プリペアードステートメントに各値をバインド
			statement.setString(1, subject.getCd());
			statement.setString(2, school.getCd());
			statement.setInt(3, entYear);
			statement.setString(4, class_num);
			// プリペアードステートメントを実行
			rSet=statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(rSet,school);
		}
		catch (Exception e){
			throw e;
		}
		finally{
			// プリペアードステートメントを閉じる
			if (statement != null){
				try{
					statement.close();
				}
				catch(SQLException sqle){
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null){
				try{
					connection.close();
				}
				catch(SQLException sqle){
					throw sqle;
				}
			}
		}
		return list;
	}
	public boolean save(List<Test> list) throws Exception{
		// コネクションを確立
		Connection connection = getConnection();
		int count = 0;
		boolean success=false;
		for (Test test: list){
			success=save(test,connection);
			if (success==true){
				count+=1;
			}
		}
		if (count>=list.size()){
			// 更新件数が受け取ったリストのサイズと同じなら
			return true;
		}
		else{
			// 更新件数が受け取ったリストのサイズと同じでないなら
			return false;
		}
	}
	private boolean save(Test test, Connection connection) throws Exception{
		// プリペアードステートメント
		PreparedStatement statement =null;
		// 更新件数
		int count = 0;
		try{
			Test oldtest = get(test.getStudent(),test.getSubject(),test.getSchool(),test.getNo());
			if(oldtest == null){
				// テストが存在しなかった場合
				// プリペアードステートメントにINSERT文をセット
				statement=connection.prepareStatement("insert into test(STUDENT_NO,SUBJECT_CD,SCHOOL_CD,NO,POINT,CLASS_NUM) values(?,?,?,?,?,?)");
				statement.setString(1, test.getStudent().getNo());
				statement.setString(2, test.getSubject().getCd());
				statement.setString(3, test.getSchool().getCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());
			}
			else{
				// テストが存在した場合
				// プリペアードステートメントにUPDATE文をセット
				statement=connection.prepareStatement("update test set no=?,point=?,class_num=? where STUDENT_NO=? and SUBJECT_CD=? and SCHOOL_CD=?");
				statement.setInt(1, test.getNo());
				statement.setInt(2, test.getPoint());
				statement.setString(3, test.getClassNum());
				statement.setString(4, test.getStudent().getNo());
				statement.setString(5, test.getSubject().getCd());
				statement.setString(6, test.getSchool().getCd());
			}
			// プリペアードステートメントを実行
			// 戻り値として更新した件数が変数countに入る
			count = statement.executeUpdate();
		}
		catch (Exception e){
			throw e;
		}
		finally{
			// プリペアードステートメントを閉じる
			if (statement != null){
				try{
					statement.close();
				}
				catch(SQLException sqle){
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null){
				try{
					connection.close();
				}
				catch(SQLException sqle){
					throw sqle;
				}
			}
		}
		if (count>0){
			// 更新件数が1件以上ある場合
			return true;
		}
		else{
			// 更新件数が0件の場合
			return false;
		}
	}


}
