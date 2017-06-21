package nl.hu.v1ipass.testipass.model;

public class Gerecht {
	private int id;
	private String datum;
	private String name;
	private int betaling;	
	private Lid eter;
	
	public Gerecht (String datum, String name, Lid eter) {
		this.datum = datum;
		this.name = name;
		this.eter = eter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBetaling() {
		return betaling;
	}

	public void setBetaling(int betaling) {
		this.betaling = betaling;
	}
	
	public Lid getEter() {
		return eter;
	}
	
	public void setEter(Lid eter) {
		this.eter = eter;
	}
	
	public int getLidNummer() {
		return eter.getLidNummer();
	}
}
