package nl.hu.v1ipass.testipass.model;

import java.sql.SQLException;
import java.util.*;

import nl.hu.v1ipass.testipass.persistence.*;

public class AanmeldingService {
	AanmeldingDAO aanmeldingDAO = new AanmeldingDAO();
	
	public AanmeldingService() {}
	
	public List<Aanmelding> getAanmeldingen() {
		return aanmeldingDAO.getAlleAanmeldingen();
	}
	
	public List<Aanmelding> getAanmeldingDate(String datum) {
		return aanmeldingDAO.getAanmeldingByDate(datum);
	}
	
	public Aanmelding addAanmelding(int lidnummer, int gerechtid) throws SQLException {
		return aanmeldingDAO.save(lidnummer, gerechtid);
	}
}
