package nl.hu.v1ipass.ipass.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.v1ipass.ipass.model.*;

public class GerechtDAO extends BaseDAO{
	private LidDAO lidDAO = new LidDAO();
	
	public Gerecht save(Gerecht gerecht){
        String query = "INSERT INTO gerecht(name, datum, lidNummer) VALUES ('"
                + gerecht.getName() + "', '"
                + gerecht.getDatum() + "', '"
                + gerecht.getEter() + "', '";
         
        try (Connection con = super.getConnection()){
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
             
        } catch (SQLException e) {
            e.printStackTrace();
        }
         
        return (gerecht);
    }
	
	public Gerecht update (Gerecht gerecht) {
		String query = "UPDATE gerecht set name='"+ gerecht.getName()+
                "', datum='" +gerecht.getDatum()+
                "' WHERE lidnummer='"+ gerecht.getLidNummer() + "'";
         
        try (Connection con = super.getConnection()){
            Statement stmt = con.createStatement();
            int aff = stmt.executeUpdate(query);
            System.out.println("Row(s) affected: "+aff);
        } catch (SQLException e) {
            e.printStackTrace();
        }
         
        return gerecht;
	}
	
	public boolean delete(Gerecht gerecht){
        boolean result = false;
        boolean gerechtExists = lidDAO.getLidByNummer(gerecht.getLidNummer()) != null;
         
        if (gerechtExists){
            String queryGerecht = "DELETE FROM gerecht WHERE lidnummer = " + gerecht.getLidNummer() + " AND datum = " + gerecht.getDatum();
            
            try (Connection con = getConnection()) {
                 
                Statement stmt = con.createStatement();
                if (stmt.executeUpdate(queryGerecht) == 1) { // 1 row updated!
                    result = true;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }             
        }
         
        return result;
    }
	
	public List<Gerecht> getAlleGerechten (String query) {		
		List<Gerecht> results = new ArrayList<Gerecht>();
		
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				String naam = rs.getString("naam");
				int nummer = rs.getInt("lidnummer");
				String datum = rs.getString("datum");
				
				Lid eter = lidDAO.getLidByNummer(nummer);
				
				Gerecht newGerecht = new Gerecht(datum, naam, eter);
				
				results.add(newGerecht);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return results;
	}
	
	public List<Gerecht> alleGerechten() {
		return getAlleGerechten("SELECT * FROM gerecht;");
	}
	
	public List<Gerecht> getGerechtenByDate (String datum) {
		return getAlleGerechten("SELECT * FROM gerecht WHERE datum = " + datum + ";");
	}
}
