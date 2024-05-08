package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;


public class TestListStudentDao extends Dao{
	private String baseSql ="select * from test where";
	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception{
		// リストを初期化
		List<TestListStudent> list = new ArrayList<>();
		SubjectDao subDao = new SubjectDao();
		SchoolDao schDao = new SchoolDao();
		try{
			// リザルトセットを全権走査
			while(rSet.next()){
				TestListStudent testliststudent = new TestListStudent();
				testliststudent.setSbjectName(subDao.get(rSet.getString("subject_cd"), schDao.get(rSet.getString("school_cd"))).getName());
				testliststudent.setSbjectCd(rSet.getString("subject_cd"));
				testliststudent.setNum(rSet.getInt("no"));
				testliststudent.setPoint(rSet.getInt("point"));
				//リストに追加
				list.add(testliststudent);
			}
		}
		catch (SQLException | NullPointerException e){
			e.printStackTrace();
		}
		return list;
	}
	public List<TestListStudent> filter(Student student) throws Exception{
		// リストを初期化
		List<TestListStudent> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement =null;
		// リザルトセット
		ResultSet rSet=null;
		// 条件のSQL文
		String condition = "  student_no=?";
		// ソートのSQL文
		String order = " order by subject_cd asc,no asc";
		try{
			// プリペアードステートメントにSQLをセット
			statement=connection.prepareStatement(baseSql+condition+order);
			// プリペアードステートメントに値をバインド
			statement.setString(1, student.getNo());
			// プリペアードステートメントを実行
			rSet=statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(rSet);
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
}
