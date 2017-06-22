package nl.hu.v1ipass.testipass.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.v1ipass.testipass.model.*;

public class GerechtDAO extends BaseDAO{
	private LidDAO lidDAO = new LidDAO();
	
	public Gerecht save(Gerecht gerecht){
        String query = "INSERT INTO gerecht(name, datum, lidNummer) VALUES ('"
                + gerecht.getName() + "', '"
                + gerecht.getDatum() + "', '";
         
        try (Connection con = super.getConnection()){
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
             
        } catch (SQLException e) {
            e.printStackTrace();
        }
         
        return (gerecht);
    }
	
	public List<Gerecht> getAlleGerechten (String query) {		
		List<Gerecht> results = new ArrayList<Gerecht>();
		
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				String naam = rs.getString("naam");
				String datum = rs.getString("datum");
				
				Gerecht newGerecht = new Gerecht(datum, naam);
				
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
		List<Gerecht> results = new ArrayList<Gerecht>();
		
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM gerecht WHERE datum = '" + datum + "';");
			
			while(rs.next()) {
				String naam = rs.getString("naam");
				String date = rs.getString("datum");
				
				System.out.println("GerechtDAO:");
				System.out.println(naam);
				System.out.println(date);
				
				Gerecht newGerecht = new Gerecht(date, naam);
				
				results.add(newGerecht);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return results;
	}
	
	public Gerecht getNaamById(int id) {
		List<Gerecht> results = getAlleGerechten("SELECT naam FROM gerecht WHERE id = " + id + ";");
		if (results.size() == 0) {
			return null;
		} else {
			return getAlleGerechten("SELECT naam FROM gerecht WHERE id = " + id + ";").get(0);
		}
	}
	
	public Gerecht getIdByNaamDatum(String naam, String datum) {
		Gerecht results = null;
		
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM gerecht WHERE datum = '" + datum + "' AND naam = '" + naam + "';");
			
			while(rs.next()) {
				String name = rs.getString("naam");
				String date = rs.getString("datum");
				
				System.out.println("GerechtDAO:");
				System.out.println(name);
				System.out.println(date);
				
				Gerecht newGerecht = new Gerecht(date, name);
				
				results = newGerecht;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return results;
	}
		
	}
