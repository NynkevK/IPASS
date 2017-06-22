package nl.hu.v1ipass.testipass.model;

// Gerecht POJO
public class Gerecht {
	private int id;
	private String datum;
	private String name;
	
	public Gerecht (String datum, String name) {
		this.datum = datum;
		this.name = name;
	}

	public Gerecht(String naam) {
		this.name = naam;
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
}
