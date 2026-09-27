package com.vit.jdbcdemo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PrepareStmtDemo {
	private static Connection con=null;
	private static final String  URL="jdbc:postgresql://localhost:5432/academics";
	private static final String USER = "postgres";
    private static final String PASSWORD = "admin";
    

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			PreparedStatement pStmt= con.prepareStatement
					("insert into public.\"Course\" values (?,?,?,?,?)");
			//Assume inserting two records after data capture
			java.util.Scanner sc = new java.util.Scanner(System.in);
			System.out.println("Enter number of records you want to insert.");
			int num_records = sc.nextInt();
			for(int i=0;i<num_records;i++) {
				System.out.println("Enter Course Code");
				String courseCode=sc.next();
				System.out.println("Enter Course Name");
				String courseName=sc.next();
				System.out.println("Enter Credits");
				int courseCredits=sc.nextInt();
				System.out.println("Enter School");
				String school=sc.next();
				System.out.println("Enter Faculty");
				String faculty=sc.next();
				pStmt.setString(1, courseCode);
				pStmt.setString(2, courseName);
				pStmt.setInt(3, courseCredits);
				pStmt.setString(4, school);
				pStmt.setString(5, faculty);
				int resultRows=pStmt.executeUpdate();
				System.out.println(resultRows+" updated successfully");
				
			}
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
