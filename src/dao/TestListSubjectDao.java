package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;

public class TestListSubjectDao extends Dao{
	// 試作：SELECT DISTINCT A.STUDENT_NO,A.SUBJECT_CD,A.SCHOOL_CD,B.NO,B.POINT,C.NO AS NO2,C.POINT AS POINT2,A.CLASS_NUM FROM TEST AS A RIGHT JOIN (SELECT * FROM TEST WHERE NO=1 AND STUDENT_NO IN (SELECT NO FROM STUDENT WHERE ENT_YEAR=2023 AND CLASS_NUM='202') AND SUBJECT_CD='A02') AS B ON A.STUDENT_NO=B.STUDENT_NO AND A.SUBJECT_CD=B.SUBJECT_CD AND A.SCHOOL_CD=B.SCHOOL_CD LEFT JOIN (SELECT * FROM TEST WHERE NO=2 AND STUDENT_NO IN (SELECT NO FROM STUDENT WHERE ENT_YEAR=2023 AND CLASS_NUM='202') AND SUBJECT_CD='A02') AS C ON A.STUDENT_NO=C.STUDENT_NO AND A.SUBJECT_CD=C.SUBJECT_CD AND A.SCHOOL_CD=C.SCHOOL_CD ORDER BY STUDENT_NO ASC
	private String baseSql ="SELECT DISTINCT A.STUDENT_NO,A.SUBJECT_CD,A.SCHOOL_CD,B.NO,B.POINT,C.NO AS NO2,C.POINT AS POINT2,A.CLASS_NUM FROM TEST AS A LEFT JOIN (SELECT * FROM TEST WHERE NO=1) AS B ON A.STUDENT_NO=B.STUDENT_NO AND A.SUBJECT_CD=B.SUBJECT_CD AND A.SCHOOL_CD=B.SCHOOL_CD LEFT JOIN (SELECT * FROM TEST WHERE NO=2) AS C ON A.STUDENT_NO=C.STUDENT_NO AND A.SUBJECT_CD=C.SUBJECT_CD AND A.SCHOOL_CD=C.SCHOOL_CD WHERE A.STUDENT_NO IN (SELECT NO FROM STUDENT WHERE ENT_YEAR=? AND CLASS_NUM=?) AND A.SUBJECT_CD=? AND A.SCHOOL_CD=? ORDER BY A.STUDENT_NO ASC";
	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception{
		// リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		StudentDao stuDao = new StudentDao();
		try{
			// リザルトセットを全権走査
			while(rSet.next()){
				TestListSubject testlistsubject = new TestListSubject();
				Map<Integer,Integer> points = new HashMap<>();
				testlistsubject.setEntYear(stuDao.get(rSet.getString("student_no")).getEntYear());
				testlistsubject.setStudentNo(rSet.getString("student_no"));
				testlistsubject.setStudentName(stuDao.get(rSet.getString("student_no")).getName());
				testlistsubject.setClassNum(rSet.getString("class_num"));
				points.put(rSet.getInt("no"), rSet.getInt("point"));
				points.put(rSet.getInt("no2"), rSet.getInt("point2"));
				testlistsubject.setPoints(points);
				//リストに追加
				list.add(testlistsubject);
			}
		}
		catch (SQLException | NullPointerException e){
			e.printStackTrace();
		}
		return list;
	}
	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception{
		// リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement =null;
		// リザルトセット
		ResultSet rSet=null;
		try{
			// プリペアードステートメントにSQLをセット
			statement=connection.prepareStatement(baseSql);
			// プリペアードステートメントに値をバインド
			statement.setInt(1, entYear);
			statement.setString(2, classNum);
			statement.setString(3, subject.getCd());
			statement.setString(4, school.getCd());
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
