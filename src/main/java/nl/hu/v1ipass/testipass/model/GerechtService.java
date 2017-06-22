package nl.hu.v1ipass.testipass.model;

import java.util.List;
import nl.hu.v1ipass.testipass.persistence.*;

public class GerechtService {
	// Er wordt een connectie met de DAO gemaakt, vanuit deze connectie kunnen alle methoden worden aangeroepen
	GerechtDAO gerechtDAO = new GerechtDAO();
	
	public GerechtService() {}
	
	// Alle vanuit de DAO aangeroepen methoden:
	public Gerecht addGerecht(Gerecht g) {
		return gerechtDAO.save(g);
	}
	
	public List<Gerecht> alleGerechten() {
		return gerechtDAO.alleGerechten();
	}
	
	public List<Gerecht> getGerechtByDate(String datum) {
		return gerechtDAO.getGerechtenByDate(datum);
	}
	
	public Gerecht getByNaamDate (String naam, String datum) {
		return gerechtDAO.getIdByNaamDatum(naam, datum);
	}
	
	public Gerecht getNaamById(int id){
		return gerechtDAO.getNaamById(id);
	}
}
