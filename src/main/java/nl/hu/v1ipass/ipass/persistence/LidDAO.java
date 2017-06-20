package nl.hu.v1ipass.ipass.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import nl.hu.v1ipass.ipass.model.Lid;

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
				String username = rs.getString("username");
				String password = rs.getString("password");
				String role = rs.getString("role");
				
				Lid newLid = new Lid(naam, nummer, username, password, role);
				
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
		List<Lid> results = getAlleLeden("SELECT * FROM lid WHERE lidnummer = " + nummer + "");
        if(results.size() == 0){
            return null;
        } else {
            return getAlleLeden("SELECT * FROM lid WHERE lidnummer = " + nummer + "").get(0);
        }
	}
	
	public List<Lid> getLedenByDate(String datum) {
		return getAlleLeden("SELECT * FROM lid WHERE datum = " + datum + ";");
	}
}
