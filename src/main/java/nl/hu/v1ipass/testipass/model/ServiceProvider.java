package nl.hu.v1ipass.testipass.model;

public class ServiceProvider {
	private static LidService lidService = new LidService();
	private static GerechtService gerechtService = new GerechtService();
//	private static CommissieService commissieService = new CommissieService();
	private static AanmeldingService aanmeldingService = new AanmeldingService();

	public static LidService getLidService() {
		return lidService;
	}
	
	public static GerechtService getGerechtService() {
		return gerechtService;
	}
	
//	public static CommissieService getCommissieService() {
//		return commissieService;
//	}
	
	public static AanmeldingService getAanmeldingService() {
		return aanmeldingService;
	}
}
