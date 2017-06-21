package nl.hu.v1ipass.testipass.model;

import java.util.List;
import nl.hu.v1ipass.testipass.persistence.*;

public class GerechtService {
	GerechtDAO gerechtDAO = new GerechtDAO();
	
	public GerechtService() {}
	
	public Gerecht addGerecht(Gerecht g) {
		return gerechtDAO.save(g);
	}
	
	public Gerecht updateGerecht(Gerecht g) {
		return gerechtDAO.update(g);
	}
	
	public boolean deleteGerecht(Gerecht g) {
		return gerechtDAO.delete(g);
	}
	
	public List<Gerecht> alleGerechten() {
		return gerechtDAO.alleGerechten();
	}
	
	public List<Gerecht> getGerechtByDate(String datum) {
		return gerechtDAO.getGerechtenByDate(datum);
	}
}
