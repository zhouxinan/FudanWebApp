package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.User;

public class Dao {
	private static String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://127.0.0.1:3306/note?useUnicode=true&amp;characterEncoding=UTF-8&amp;";
	
	//your username and password
	String dbUsername = "root"; 
	String dbPassword = "123456";
	
	private static Dao dao;
	
	private Dao(){};
	
	public static Dao getInstance(){
		if(dao == null){
			dao = new Dao();
		}
		
		return dao;
	}

	static {
		try {
			Class.forName(driver).newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public User login(String username, String password){
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from user where user.username='"+username+"'");
			if(results.next()){
				String oldPassword = results.getString("password");
				if(oldPassword.equals(password)){
					User user = new User();
					user.setUserID(results.getInt("userID"));
					user.setUsername(username);
					return user;
				}else{
					return null;
				}
			}
			
			sm.executeUpdate("insert into user(username, password) values('"+username+"', '"+password+"')");
			results.close();
			results = sm.executeQuery("select * from user where user.username='"+username+"'");
			User user = new User();
			if(results.next()){
				user.setUserID(results.getInt("userID"));
				user.setUsername(username);
			}
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if( sm != null){
					sm.close();
				}
				sm.close();
				if(con != null){
					con.close();	
				}
				if(results != null){
					results.close();
				}
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			

		}
		return null;
	}
	

}
