package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;

public class ClassNumDao extends Dao{

	private List<String> postFilter(ResultSet rSet) throws Exception{
		// リストを初期化
		List<String> list=new ArrayList<>();
		try {
			// リザルトセットを全権走査
			while(rSet.next()){
				//リストに追加
				list.add(rSet.getString("class_num"));
			}
		}
		catch (SQLException | NullPointerException e){
			e.printStackTrace();
		}
		return list;
	}

	public List<String> filter(School school) throws Exception{
		// リストを初期化
		List<String> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement =null;
		// リザルトセット
		ResultSet rSet=null;

		try{
			// プリペアードステートメントにSQLをセット
			statement=connection.prepareStatement("select class_num from class_num where school_cd=?  order by class_num asc");
			// プリペアードステートメントに学校番号をバインド
			statement.setString(1, school.getCd());
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
