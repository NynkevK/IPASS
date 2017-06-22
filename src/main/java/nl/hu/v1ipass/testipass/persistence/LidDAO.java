package nl.hu.v1ipass.testipass.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import nl.hu.v1ipass.testipass.model.Lid;

public class LidDAO extends BaseDAO{	
	
	//Deze methode wordt gebruikt bij het inloggen. De username en wachtwoord worden opgehaald uit de
	// database om te kunnen controleren in de resource en het filter.
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
	
	// Met deze methode kan je alle leden die voldoen aan de ingevulde query uit de database halen.
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
	
	// Deze methode haalt alles van alle leden op uit de database, zoals te lezen in de query.
	public List<Lid> alleLeden() {
		return getAlleLeden("SELECT * FROM lid;");
	}
	
	// Deze methode geeft het Lid dat hoort bij een bepaald nummer, om zo de naam oid eruit de kunnen selecteren.
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
	
	// Deze methode haalt de rol van een bepaald user op. Deze methode wordt gebruikt als 
	// alternatieve authenticatie, omdat het "@RolesAllowed" principe niet werkte.
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
	
	// Deze methode geeft alle leden die zich hebben opgegeven voor het eten.
	// Dit is echter een verouderde methode die niet meer wordt gebruikt, ook omdat de database
	// anders is geworden.
	public List<Lid> getLedenByDate(String datum) {
		return getAlleLeden("SELECT lidnummer FROM gerecht WHERE datum = " + datum + ";");
	}
}
