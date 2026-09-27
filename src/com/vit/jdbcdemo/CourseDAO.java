package com.vit.jdbcdemo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class CourseDAO {

	private static Connection con=null;
	private static final String  URL="jdbc:postgresql://localhost:5432/academics";
	private static final String USER = "postgres";
    private static final String PASSWORD = "admin";
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			Statement stmt= con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM public.\"Course\"");
			while(rs.next()) {
				System.out.println("*****");
				System.out.println("Course Code"+rs.getString(1));
				System.out.println("Course Name"+rs.getString(2));
				System.out.println("Credits"+rs.getInt(3));
				System.out.println("School"+rs.getString(4));
				System.out.println("Faculty"+rs.getString(5));
				System.out.println("*****");
				
			}
			
			
		}
		catch(Exception e) {
			
			e.printStackTrace();
			
		}

	}

}
