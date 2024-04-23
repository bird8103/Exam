package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao{
	private String baseSql ="select * from student where school_cd=?";

	public Student get(String no) throws Exception{
		// 学生インスタンスを初期化
		Student student = new Student();
		// DBへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement =null;

		try{
			// プリペアードステートメントにSQLをセット
			statement=connection.prepareStatement("select * from student where no=?");
			// プリペアードステートメントに学校番号をバインド
			statement.setString(1, no);
			// プリペアードステートメントを実行
			ResultSet rSet=statement.executeQuery();
			// 学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();

			if (rSet.next()){
				// リザルトセットが存在する場合、
				// 学生インスタンスに検索結果をセット
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_attend"));
				// 学校フィールドには学校コードで検索した学校インスタンスをセット
				student.setSchool(schoolDao.get(rSet.getString("school_cd")));
			}
			else{
				// リザルトセットが存在しない場合
				// 学生インスタンスにnullをセット
				student = null;
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
		return student;
	}

	private List<Student> postFilter(ResultSet rSet, School school) throws Exception{
		// リストを初期化
		List<Student> list=new ArrayList<>();
		try {
			// リザルトセットを全権走査
			while(rSet.next()){
				Student student =new Student();
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_attend"));
				student.setSchool(school);
				//リストに追加
				list.add(student);
			}
		}
		catch (SQLException | NullPointerException e){
			e.printStackTrace();
		}
		return list;
	}

	public List<Student> filter(School school,int entYear, String classNum, boolean isAttend) throws Exception{
		// リストを初期化
		List<Student> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement =null;
		// リザルトセット
		ResultSet rSet=null;
		// 条件のSQL文
		String condition = " and ent_year=? and class_num=?";
		// ソートのSQL文
		String order = " order by no asc";
		//在学フラグのSQL文
		String conditionIsAttend="";
		if (isAttend){
			conditionIsAttend = " and is_attend=true";
		}
		try{
			// プリペアードステートメントにSQLをセット
			statement=connection.prepareStatement(baseSql+condition+conditionIsAttend+order);
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			// プリペアードステートメントに入学年度をバインド
			statement.setInt(2, entYear);
			// プリペアードステートメントにクラス番号をバインド
			statement.setString(3, classNum);
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

	public List<Student> filter(School school,int entYear, boolean isAttend) throws Exception{
		// リストを初期化
		List<Student> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement =null;
		// リザルトセット
		ResultSet rSet=null;
		// 条件のSQL文
		String condition = " and ent_year=?";
		// ソートのSQL文
		String order = " order by no asc";
		//在学フラグのSQL文
		String conditionIsAttend="";
		if (isAttend){
			conditionIsAttend = " and is_attend=true";
		}
		try{
			// プリペアードステートメントにSQLをセット
			statement=connection.prepareStatement(baseSql+condition+conditionIsAttend+order);
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			// プリペアードステートメントに入学年度をバインド
			statement.setInt(2, entYear);
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

	public List<Student> filter(School school, boolean isAttend) throws Exception{
		// リストを初期化
		List<Student> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement =null;
		// リザルトセット
		ResultSet rSet=null;
		// ソートのSQL文
		String order = " order by no asc";
		//在学フラグのSQL文
		String conditionIsAttend="";
		if (isAttend){
			conditionIsAttend = " and is_attend=true";
		}
		try{
			// プリペアードステートメントにSQLをセット
			statement=connection.prepareStatement(baseSql+conditionIsAttend+order);
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
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

	// 学生インスタンスをデータベースに保存するメソッド
	// 学生登録もしくは学生更新の処理で利用する
	// データが存在する場合は更新し、存在しない場合は登録する
	public boolean save(Student student) throws Exception{
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement =null;
		// 更新件数
		int count = 0;

		try{
			// データベースから学生を取得
			Student old = get(student.getNo());
			if(old == null){
				// 学生が存在しなかった場合
				// プリペアードステートメントにINSERT文をセット
				statement=connection.prepareStatement("insert into student(no,name,ent_year,class_num,is_attend,school_cd) values(?,?,?,?,?,?)");
				// プリペアードステートメントに各値をバインド
				statement.setString(1, student.getNo());
				statement.setString(2, student.getName());
				statement.setInt(3, student.getEntYear());
				statement.setString(4, student.getClassNum());
				statement.setBoolean(5, student.isAttend());
				statement.setString(6, student.getSchool().getCd());
			}
			else{
				// 学生が存在した場合
				// プリペアードステートメントにUPDATE文をセット
				statement=connection.prepareStatement("update student set name=?,ent_year=?,class_num=?,is_attend=? where no=?");
				// プリペアードステートメントに各値をバインド
				statement.setString(1, student.getName());
				statement.setInt(2, student.getEntYear());
				statement.setString(3, student.getClassNum());
				statement.setBoolean(4, student.isAttend());
				statement.setString(5, student.getNo());
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
