package nl.hu.v1ipass.ipass.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.hu.v1ipass.ipass.persistence.*;

public class LidService {
	LidDAO lidDAO = new LidDAO();
	
	public LidService() {}
	
	public List<Lid> getLedeneten() throws SQLException {
		return lidDAO.alleLeden();
	}
		
	public List<Lid> getLedenDatum(String datum) {
		return lidDAO.getLedenByDate(datum);
	}
}
