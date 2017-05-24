package com.nag;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.Connection;

public class Jdbc {
	
	public static void main(String[] args) throws SQLException {
	
		Connection dbConnection = null;
		Statement statement = null;
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver"); // Step 1
			dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mytestdb","root",""); // Step 2
			statement = dbConnection.createStatement(); // Step 3
			statement.executeUpdate("CREATE TABLE myuser2(id int,name varchar(20))");
			statement.executeUpdate("INSERT INTO myuser2 values(101,'Jhon')");
			statement.executeUpdate("INSERT INTO myuser2 values(102,'Shan')");
			statement.executeUpdate("INSERT INTO myuser2 values(103,'Watson')");
			statement.executeUpdate("UPDATE myuser2 SET name='Mike' where id=101");
			statement.executeUpdate("DELETE from myuser2 where id = 102");
			
			
			PreparedStatement preparedStatement = dbConnection.prepareStatement("insert into myuser2 values(?,?)");
			
			dbConnection.setAutoCommit(false);
			
			preparedStatement.setInt(1,104);
			preparedStatement.setString(2,"Ted");
			preparedStatement.executeUpdate();
			
			preparedStatement.setInt(1,105);
			preparedStatement.setString(2, "Ross");
			preparedStatement.addBatch();
			
			preparedStatement.setInt(1, 106);
			preparedStatement.setString(2, "Rose");
			preparedStatement.addBatch();
			preparedStatement.executeBatch();
			
			dbConnection.commit();
			
			ResultSet rs = statement.executeQuery("SELECT * from myuser2");
			while(rs.next()){
				System.out.println("User id is : "+rs.getString("id")+" name is :"+rs.getString(2));
			}
			
		} 
		catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			if(statement != null){
				statement.close();
			}
			if(dbConnection != null ){
				dbConnection.close();
			}
		}
	}
}
