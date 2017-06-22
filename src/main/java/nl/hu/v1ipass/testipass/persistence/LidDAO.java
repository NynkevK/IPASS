package nl.hu.v1ipass.testipass.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import nl.hu.v1ipass.testipass.model.Lid;

public class LidDAO extends BaseDAO{	
	public String findRoleForUsernameAndPassword (String username, String password) {
		 String role = null;
	     String query = "SELECT role FROM lid WHERE username = ? AND password = ?";
	     
	     try (Connection con = super.getConnection()) {
	             
	         PreparedStatement pstmt = con.prepareStatement(query);
	         pstmt.setString(1, username);
	         pstmt.setString(2, password);
	          
	         ResultSet rs = pstmt.executeQuery();
	         if (rs.next()){
	             role = rs.getString("role");
	         }
	          
	     } catch (SQLException sqle) {
	         sqle.printStackTrace();
	     }
	         
	     return role;
	}
	
	public List<Lid> getAlleLeden (String query) {		
		List<Lid> results = new ArrayList<Lid>();
		
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				String naam = rs.getString("name");
				int nummer = rs.getInt("lidNummer");
				
				Lid newLid = new Lid(naam, nummer);
				
				results.add(newLid);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return results;
	}
	
	public List<Lid> alleLeden() {
		return getAlleLeden("SELECT * FROM lid;");
	}
	
	public Lid getLidByNummer(int nummer) {		
		Lid result = null;
		
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM lid WHERE lidnummer = " + nummer + ";");
			
			while(rs.next()) {
				String naam = rs.getString("naam");
				int lidnummer = rs.getInt("lidnummer");
				
				System.out.println("LidDAO:");
				System.out.println(naam);
				System.out.println(lidnummer);
				
				result = new Lid(naam, lidnummer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("LidDAO result:");
		System.out.println(result);
		return result;
        
	}
	
	public String getRoleByUsername(String username){
		String result = "";
		
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT role FROM lid WHERE username = '" + username + "';");
			
			while(rs.next()) {
				String role = rs.getString("role");
				
				result = role;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public List<Lid> getLedenByDate(String datum) {
		return getAlleLeden("SELECT lidnummer FROM gerecht WHERE datum = " + datum + ";");
	}
}
