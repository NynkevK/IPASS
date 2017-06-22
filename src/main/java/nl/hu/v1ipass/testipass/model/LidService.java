package nl.hu.v1ipass.testipass.model;

import java.sql.*;
import java.util.List;
import nl.hu.v1ipass.testipass.persistence.*;

public class LidService {
	// Er wordt een connectie met de DAO gemaakt, vanuit deze connectie kunnen alle methoden worden aangeroepen
	LidDAO lidDAO = new LidDAO();
	
	public LidService() {}
	
	// Alle vanuit de DAO aangeroepen methoden:
	public List<Lid> getLedeneten() throws SQLException {
		return lidDAO.alleLeden();
	}
		
	public List<Lid> getLedenDatum(String datum) {
		return lidDAO.getLedenByDate(datum);
	}
	
	public String getRole(String username) {
		return lidDAO.getRoleByUsername(username);
	}
	
	public Lid getLidByNum(int nummer) {
		return lidDAO.getLidByNummer(nummer);
	}
}
