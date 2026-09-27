package com.vit.jdbcdemo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

public class CallableDemo {
	private static Connection con=null;
	private static final String  URL="jdbc:postgresql://localhost:5432/academics";
	private static final String USER = "postgres";
    private static final String PASSWORD = "admin";
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			CallableStatement cs =
			        con.prepareCall("CALL proc_get_course_name(?, ?)");
			cs.setString(1, "CSE1009");
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.execute();
			String courseName = cs.getString(2);
			System.out.println(courseName);		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
