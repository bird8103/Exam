












// ★エラーが起こる場合★   StudentDaoの機能は論理削除方式のdeleteメソッドが存在する関係上、論理削除フラグのカラムを使用します。論理削除フラグがまだ存在しないなら、次のSQL文をH2で実行してから動作させてください→ ALTER TABLE SUBJECT ADD EXISTING boolean DEFAULT TRUE      ;










package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao{

	public Subject get(String cd, School school) throws Exception{
		// 科目インスタンスを初期化
		Subject subject = new Subject();
		// DBへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement =null;

		try{
			// プリペアードステートメントにSQLをセット
			statement=connection.prepareStatement("select * from subject where cd=? and school_cd=?");
			// プリペアードステートメントに科目IDをバインド
			statement.setString(1, cd);
			statement.setString(2, school.getCd());
			// プリペアードステートメントを実行
			ResultSet rSet=statement.executeQuery();
			// 学校Daoを初期化

			if (rSet.next()){
				// リザルトセットが存在する場合、
				// 科目インスタンスに検索結果をセット
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				subject.setSchool(school);
			}
			else{
				// リザルトセットが存在しない場合
				// 科目インスタンスにnullをセット
				subject = null;
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
		return subject;
	}
	public List<Subject> filter(School school) throws Exception{
		// リストを初期化
		List<Subject> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement =null;
		// リザルトセット
		ResultSet rSet=null;

		try{
			// プリペアードステートメントにSQLをセット
			statement=connection.prepareStatement("select * from subject where school_cd=? and existing=true order by cd asc");
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			// プリペアードステートメントを実行
			rSet=statement.executeQuery();
			// リストへの格納処理を実行
			while(rSet.next()){
				Subject subject = new Subject();
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				subject.setSchool(school);
				//リストに追加
				list.add(subject);
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
		return list;
	}
	public boolean save(Subject subject) throws Exception{
		// DBへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement =null;
		// 科目からSchoolを取得
		School subjectschool = subject.getSchool();
		// 更新件数
		int count = 0;
		try{
			// データベースから科目を取得
			Subject subjectexists = get(subject.getCd(),subjectschool);
			if (subjectexists==null){
				// 科目が存在しなかった場合
				// プリペアードステートメントにINSERT文をセット
				statement=connection.prepareStatement("insert into subject(school_cd,cd,name,existing) values(?,?,?,?)");
				// プリペアードステートメントに各値をバインド
				statement.setString(1, subjectschool.getCd());
				statement.setString(2,subject.getCd());
				statement.setString(3,subject.getName());
				statement.setBoolean(4, true);
			}
			else{
				// 科目が存在した場合
				// プリペアードステートメントにUPDATE文をセット
				statement=connection.prepareStatement("update subject set name=? where cd=?");
				statement.setString(1,subject.getName());
				statement.setString(2,subject.getCd());
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
	public boolean delete(Subject subject) throws Exception{
		// DBへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement =null;
		// 科目からSchoolを取得
		School subjectschool = subject.getSchool();
		// 更新件数
		int count = 0;
		try{
			// データベースから科目を取得
			Subject subjectexists = get(subject.getCd(),subjectschool);
			if (subjectexists!=null){
				// 科目が存在した場合
				// プリペアードステートメントに論理削除文をセット
				statement=connection.prepareStatement("update subject set existing=? where cd=?");
				statement.setBoolean(1,false);
				statement.setString(2,subject.getCd());
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





















