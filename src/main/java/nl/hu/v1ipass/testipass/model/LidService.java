package nl.hu.v1ipass.testipass.model;

import java.sql.*;
import java.util.List;
import nl.hu.v1ipass.testipass.persistence.*;

public class LidService {
	LidDAO lidDAO = new LidDAO();
	
	public LidService() {}
	
	public List<Lid> getLedeneten() throws SQLException {
		return lidDAO.alleLeden();
	}
		
	public List<Lid> getLedenDatum(String datum) {
		return lidDAO.getLedenByDate(datum);
	}
	
	public String getRole(String username) {
		return lidDAO.getRoleByUsername(username);
	}
}
