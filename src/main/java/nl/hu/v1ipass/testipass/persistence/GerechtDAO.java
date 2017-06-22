package nl.hu.v1ipass.testipass.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.v1ipass.testipass.model.*;

public class GerechtDAO extends BaseDAO{
	// Connectie met de andere benodigde DAO
	private LidDAO lidDAO = new LidDAO();
	
	// Met deze methode wordt een nieuw gerecht aan de database toegevoegd. Deze methode wordt niet gebruikt
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
	
	// Deze methode haalt alle gerechten op uit de database die vallen onder de ingevoerde query
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
	
	// Deze methode haal alles van alle gerechten uit de database, zoals te lezen in de query.
	public List<Gerecht> alleGerechten() {
		return getAlleGerechten("SELECT * FROM gerecht;");
	}
	
	// Met deze methode kan je alle gerechten uit de database halen die horen bij een bepaalde datum.
	// Deze methode wordt gebruikt om alle aanmeldingen te laten zien als de admin hierom vraagt.
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
	
	// Deze methode geeft de naam van een gerecht bij zijn id
	public Gerecht getNaamById(int id) {
		List<Gerecht> results = getAlleGerechten("SELECT naam FROM gerecht WHERE id = " + id + ";");
		if (results.size() == 0) {
			return null;
		} else {
			return getAlleGerechten("SELECT naam FROM gerecht WHERE id = " + id + ";").get(0);
		}
	}
	
	// Deze methode geeft de gerechtId van een gerecht met een bepaalde naam en bijbehorende datum.
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
