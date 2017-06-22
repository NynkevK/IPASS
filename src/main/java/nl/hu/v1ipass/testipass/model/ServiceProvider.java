package nl.hu.v1ipass.testipass.model;

public class ServiceProvider {
	// Hier worden alle services aangesproken voor een connectie, met deze connectie roept een resource de DAO aan
	private static LidService lidService = new LidService();
	private static GerechtService gerechtService = new GerechtService();
	private static AanmeldingService aanmeldingService = new AanmeldingService();

	public static LidService getLidService() {
		return lidService;
	}
	
	public static GerechtService getGerechtService() {
		return gerechtService;
	}
	
	public static AanmeldingService getAanmeldingService() {
		return aanmeldingService;
	}
}
