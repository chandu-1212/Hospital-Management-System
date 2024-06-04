package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patients {

	private Connection connection;
	private Scanner scanner;
	public Patients(Connection connection, Scanner scanner) {
		this.connection =connection;
		this.scanner =scanner;
	}
	
	public void addPatients() {
		System.out.println("Enter the patient name");
		String name=scanner.next();
		System.out.println("Enter the patient age ");
		int age=scanner.nextInt();
		System.out.println("Enter the patient gender");
		String gender=scanner.next();
		
		try {
			String query = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";
			PreparedStatement pStmt = connection.prepareStatement(query);
			pStmt.setString(1,name);
			pStmt.setInt(2,age);
			pStmt.setString(3,gender);
			int affectedRows =pStmt.executeUpdate();
			if(affectedRows>0) {
				System.out.println("Patient Added Successfully !!");
				
			}else {
				System.out.println("Failed to add Patient!!");
			}
					
		}catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	
	public void viewPatients() {
		String query="SELECT * FROM patients";
		try {
			PreparedStatement pStmt =connection.prepareStatement(query);
			ResultSet rs =pStmt.executeQuery();
			System.out.println("Patients");
			System.out.println("+------------+--------------------+-------+----------+");
			System.out.println("| Patient Id | Name               | Age   | Gender   |");
			System.out.println("+------------+--------------------+-------+----------+");
			while(rs.next()) {
				int id =rs.getInt("Iid_pat");
				String name=rs.getString("name");
				int age=rs.getInt("age");
				String gender= rs.getString("gender");
				System.out.printf("|%-12s|%-20s|%-7s|%-10s|\n", id, name, age, gender);
				System.out.println("+------------+--------------------+-------+----------+");
				
			}
		}catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	public boolean getPatientsById(int Id){
		String query = "Select * from patients where Iid_pat=?";
		try {
			PreparedStatement pStmt=connection.prepareStatement(query);
			pStmt.setInt(1, Id);
			ResultSet rs=pStmt.executeQuery();
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
		}catch(SQLException e) {
			System.out.println(e);
		}
		return false;
		
	}
}
