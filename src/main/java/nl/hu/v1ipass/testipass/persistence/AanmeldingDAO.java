package nl.hu.v1ipass.testipass.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.v1ipass.testipass.model.*;

public class AanmeldingDAO extends BaseDAO {
	// Connectie met andere benodigde DAO's
	private LidDAO lidDAO = new LidDAO();
	private GerechtDAO gerechtDAO = new GerechtDAO();
	
	// Methode om aanmeldingen op te halen door middel van de ingevoerde query
	public List<Aanmelding> alleAanmeldingen (String query) {
		List<Aanmelding> results = new ArrayList<Aanmelding>();
		
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				int aanmeldingId = rs.getInt("aanmeldingid");
				int nummer = rs.getInt("lidnummer");
				int gerechtId = rs.getInt("gerechtid");
				
				Lid eter = lidDAO.getLidByNummer(nummer);
				Gerecht gerecht = gerechtDAO.getNaamById(gerechtId);

				Aanmelding newAanmelding = new Aanmelding(aanmeldingId, eter, gerecht);
				
				results.add(newAanmelding);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return results;
	}
	
	// Alle aanmeldingen die horen bij een bepaalde datum worden hier opgehaald
	public List<Aanmelding> getAanmeldingByDate (String datum) {
		List<Aanmelding> results = new ArrayList<Aanmelding>();
		
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT lid.lidnummer, lid.naam AS lidnaam, gerecht.naam AS gerechtnaam FROM lid, gerecht, aanmelding WHERE lid.lidnummer = aanmelding.lidnummer AND gerecht.id = aanmelding.gerechtid AND gerecht.datum = '" + datum + "';");
			
			while(rs.next()) {
				int lidnummer = rs.getInt("lidnummer");
				String lidnaam = rs.getString("lidnaam");
				String gerechtId = rs.getString("gerechtnaam");
				
				// Print statements voor controle en debugging en testing
				System.out.println("AanmeldingDAO:");
				System.out.println(lidnummer);
				System.out.println(lidnaam);
				System.out.println(gerechtId);
				
				Lid eter = lidDAO.getLidByNummer(lidnummer);
				Gerecht gerecht = new Gerecht(gerechtId);

				Aanmelding newAanmelding = new Aanmelding(lidnummer, eter, gerecht);
				
				results.add(newAanmelding);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return results;
	}
	
	// Alle aanmeldingen ophalen uit de database zoals te lezen in de query
	public List<Aanmelding> getAlleAanmeldingen () {
		return alleAanmeldingen("SELECT * FROM aanmelding;");
	}

	// Een methode om een nieuwe aanmelding toe te voegen aan de database, echter deze wordt niet meer gebruikt
	public Aanmelding save(int lidnummer, int gerechtid) throws SQLException {
		String query = "INSERT INTO aanmelding (\"lidnummer\", \"gerechtid\" VALUES ( '" + lidnummer + "', '" + gerechtid + "');";
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Lid eter = lidDAO.getLidByNummer(lidnummer);
		Gerecht gerecht = gerechtDAO.getNaamById(gerechtid);
		
		Aanmelding newAanmelding = new Aanmelding(eter, gerecht);
		
		return newAanmelding;
	}
	
	// De methode die wordt aangeroepen bij de POST om een nieuwe aanmelding aan de database toe te voegen.
	// Echter wordt er ergens naar null verwezen en komt hier een NullPointerEsception uit, waardoor de POST methode
	// niet werkt en een HTTP 500 error geeft.
	public boolean addAanmelding (Aanmelding a) {
		
		System.out.println(a);
		System.out.println(a.getGerecht());
		
		int eter = a.getEter().getLidNummer();
		int id = a.getGerecht().getId();
		
		System.out.println("addAanmelding:");
		System.out.println(eter);
		System.out.println(id);
		
		 String query = "INSERT INTO aanmelding (\"lidnummer\", \"gerechtid\") VALUES (" + eter + ", " + id + ");";
	         
	        try (Connection con = super.getConnection()){
	            Statement stmt = con.createStatement();
	            stmt.executeUpdate(query);
	            
	            return true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	         
	        return false;
	}
	
}
