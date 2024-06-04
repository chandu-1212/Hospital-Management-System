package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagement {
	private static final String url="jdbc:mysql://localhost:3306/Hospital"; 
	private static final String username="root"; 
	private static final String password="root123"; 
	
	public static void main (String[] args) throws ClassNotFoundException   {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Scanner scanner = new Scanner(System.in)) {
			try {
				Connection connection=DriverManager.getConnection(url,username,password);
				Patients patient = new Patients(connection, scanner);
				Doctor doctor = new Doctor(connection);
				while(true) {
					System.out.println("HOSPITAL MANAGEMENT SYSTEM");
					System.out.println("1. Add Patient");
					System.out.println("2. View Patient");
					System.out.println("3. View Doctors");
					System.out.println("4. Book Appointment");
					System.out.println("5. Exit");
					System.out.println("Enter your choice:");
					int choice=scanner.nextInt();
					switch(choice) {
					case 1:
						//add patient
						patient.addPatients();
						System.out.println();
						break;
					case 2:
						//view patient
						patient.viewPatients();
						System.out.println();
						break;

					case 3:
						//view doctors
						doctor.viewDoctor();
						System.out.println();
						break;
					
					case 4:
						//view patient;
						bookAppointment(patient, doctor, connection, scanner);
						System.out.println();
						break;
					case 5:
						//Book Appointment
						return;
						
					default:
							System.out.println("Enter valid choice!!!");
							break;
					}
					
				}
			}catch(SQLException e) {
				System.out.println(e);
			}
		}
	}
	

	public static  void bookAppointment(Patients patient, Doctor doctor, Connection connection, Scanner scanner) {
	    System.out.println("Enter patient ID:");
	    int patientId = scanner.nextInt();
	    System.out.println("Enter doctor ID:");
	    int doctorId = scanner.nextInt();
	    System.out.println("Enter the appointment date (YYYY-MM-DD)");
	    String appointmentDate = scanner.next();

	    // Assuming you have methods to check if patient and doctor exist by ID in their respective classes.
	    // Adjust the method names as per your implementation.
	    if (patient.getPatientsById(patientId) && doctor.getDoctorById(doctorId)) {
	        if (checkDoctorAvailability(doctorId, appointmentDate, connection)) {
	            String appointmentQuery = "INSERT INTO appointments(patients_id, doctors_id, appointment) VALUES(?, ?, ?)";
	            try {
	                PreparedStatement pStmt = connection.prepareStatement(appointmentQuery);
	                pStmt.setInt(1, patientId);
	                pStmt.setInt(2, doctorId);
	                pStmt.setString(3, appointmentDate);
	                int rowsAffected = pStmt.executeUpdate();
	                if (rowsAffected > 0) {
	                    System.out.println("Appointment Booked!");
	                } else {
	                    System.out.println("Failed to book Appointment !");
	                }
	            } catch (SQLException e) {
	                System.out.println(e);
	            }
	        } else {
	            System.out.println("Doctor not available on this date!!");
	        }
	    } else {
	        System.out.println("Either doctor or patient doesn't exist!!");
	    }
	}

	
	
	public static  boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
		String query = "SELECT COUNT(*) FROM appointments WHERE doctors_id=? AND appointment =?";
	   try{
		  PreparedStatement pStmt= connection.prepareStatement(query);
		  pStmt.setInt(1,doctorId);
		  pStmt.setString(2,appointmentDate);
		  ResultSet rs=pStmt.executeQuery();
		  if(rs.next()) {
			  int count = rs.getInt(1);
			  if(count==0) {
				  return true;
				  
			  }else {
				  return false;
			  }
		  }
	   }catch(SQLException e) {
			System.out.println(e);
		}
	return false;
	   }
	

}
