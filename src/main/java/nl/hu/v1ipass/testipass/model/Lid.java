package nl.hu.v1ipass.testipass.model;

public class Lid {
	private String name;
	private int lidNummer;
	private String username;
	private String password;
	private String role;
	
//	private int telefoonNummer;
//	private String emailAdres;
//	private String adres;
//	private int jaar;
//	private String jaarclub;
	
	public Lid(String name, int lidNummer, String username, String password, String role) {
		super();
		this.name = name;
		this.lidNummer = lidNummer;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLidNummer() {
		return lidNummer;
	}

	public void setLidNummer(int lidNummer) {
		this.lidNummer = lidNummer;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
