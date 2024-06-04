package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {

	private Connection connection;
	
	public Doctor(Connection connection) {
		this.connection =connection;
		
	}
	
		
	public void viewDoctor() {
		String query="SELECT * FROM Doctors";
		try {
			PreparedStatement pStmt =connection.prepareStatement(query);
			ResultSet rs =pStmt.executeQuery();
			System.out.println("Doctor");
			System.out.println("+------------+--------------------+-----------------+");
			System.out.println("| Doctor Id  | Name               | Specialization    |");
			System.out.println("+------------+--------------------+-----------------+");
			while(rs.next()) {
				int id =rs.getInt("Id_doct");
				String name=rs.getString("name");
			    String Specialization= rs.getString("Specialization");
				System.out.printf("|%-12s|%-20s|%-17s|\n", id, name, Specialization);
				System.out.println("+------------+--------------------+-----------------+");
				
			}
		}catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	public boolean getDoctorById(int Id){
		String query = "SELECT * FROM doctors WHERE Id_doct=?";
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
