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
		return alleAanmeldingen("SELECT lid.lidnummer, lid.naam, gerecht.naam FROM lid, gerecht, aanmelding WHERE lid.lidnummer = aanmelding.lidnummer AND gerecht.id = aanmelding.gerechtid AND gerecht.datum = '" + datum + "';");
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
	
	
}
