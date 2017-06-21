package nl.hu.v1ipass.testipass.model;

public class Aanmelding {
	private int aanmeldingId;
	private Lid eter;
	private Gerecht gerechtKeuze;
		
	public Aanmelding(int aanmeldingId, Lid eter, Gerecht gerecht) {
		super();
		this.aanmeldingId = aanmeldingId;
		this.eter = eter;
		this.gerechtKeuze = gerecht;
	}
	
	public Aanmelding(Lid eter, Gerecht gerecht) {
		this.eter = eter;
		this.gerechtKeuze = gerecht;
	}
	
	public int getAanmeldingId() {
		return aanmeldingId;
	}
	
	public void setAanmeldingId(int aanmeldingId) {
		this.aanmeldingId = aanmeldingId;
	}
	
	public Lid getEter() {
		return eter;
	}
	
	public void setEter(Lid eter) {
		this.eter = eter;
	}
	
	public Gerecht getGerecht() {
		return gerechtKeuze;
	}
	
	public void setGerechtId(Gerecht gerecht) {
		this.gerechtKeuze = gerecht;
	}	
}
