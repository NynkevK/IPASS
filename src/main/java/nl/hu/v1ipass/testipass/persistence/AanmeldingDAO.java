package nl.hu.v1ipass.testipass.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.v1ipass.testipass.model.*;

public class AanmeldingDAO extends BaseDAO {
	private LidDAO lidDAO = new LidDAO();
	private GerechtDAO gerechtDAO = new GerechtDAO();
	
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
	
	public List<Aanmelding> getAanmeldingByDate (String datum) {
		List<Aanmelding> results = new ArrayList<Aanmelding>();
		
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT lid.lidnummer, lid.naam AS lidnaam, gerecht.naam AS gerechtnaam FROM lid, gerecht, aanmelding WHERE lid.lidnummer = aanmelding.lidnummer AND gerecht.id = aanmelding.gerechtid AND gerecht.datum = '" + datum + "';");
			
			while(rs.next()) {
				int lidnummer = rs.getInt("lidnummer");
				String lidnaam = rs.getString("lidnaam");
				String gerechtId = rs.getString("gerechtnaam");
				
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
	
	public List<Aanmelding> getAlleAanmeldingen () {
		return alleAanmeldingen("SELECT * FROM aanmelding;");
	}

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
