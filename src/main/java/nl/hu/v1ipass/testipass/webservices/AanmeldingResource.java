package nl.hu.v1ipass.testipass.webservices;

import java.io.InputStream;
import java.sql.SQLException;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import nl.hu.v1ipass.testipass.model.*;
import nl.hu.v1ipass.testipass.persistence.AanmeldingDAO;
import nl.hu.v1ipass.testipass.persistence.GerechtDAO;
import nl.hu.v1ipass.testipass.persistence.LidDAO;

@Path("/aanmeldingen")
public class AanmeldingResource {
	// Hier wordt de connectie met de verschillende services, en dus met hun DAO's gemaakt om te kunnen gebruiken binnen de klasse.
	private LidService lidService = ServiceProvider.getLidService();
	private GerechtService gerechtService = ServiceProvider.getGerechtService();
	private AanmeldingService aanmeldingService = ServiceProvider.getAanmeldingService();
	
	// Dit is een algemene methode om makkelijk Json objecten te maken en voorkomt redundante code,
	// echter vond ik dit niet fijn en heb ik het alsnog in elke methoe apart gezet.
	private JsonObjectBuilder aanmeldingToJson(Aanmelding aanmelding) {
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("lidnummer", aanmelding.getEter().getLidNummer());
		job.add("lidnaam", aanmelding.getEter().getName());
		job.add("gerechtnaam", aanmelding.getGerecht().getName());
		
		return job;
	}
	
	// Deze GET methode haalt alle aanmeldingen voor een bepaald datum op via de service en dus ook de DAO.
	@GET
	@Path("{datum}")
	@Produces("application/json")
	public String getAanmeldingen(@PathParam("datum")String datum) {
		AanmeldingService service = new AanmeldingService();		
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for (Aanmelding a : service.getAanmeldingDate(datum)){ 
			jab.add(aanmeldingToJson(a));
		}
		
		JsonArray array = jab.build();
		return array.toString();
	}
	
	// Deze methode moet ervoor zorgen dat een aanmelding in de database terechtkomt,
	// Echter geeft deze een NullPointerException vanaf gerechtTest
	@POST
	@Produces("application/json")
	public String addAanmelding(InputStream is) throws SQLException {		
		JsonObject object = Json.createReader(is).readObject();
		System.out.println(object);
		
		String lidnummer = object.getString("lidnummer");
		String gerechtId = object.getString("gerechtId");
		String date = object.getString("datum");
		
		System.out.println(lidnummer + " Dit is het lidnummer");
		
		Gerecht gerechtTest = gerechtService.getByNaamDate(gerechtId, date);
		
		int lidnummerInt = Integer.parseInt(lidnummer);
		int gerechtIdInt = gerechtTest.getId();
		
		System.out.println(gerechtIdInt + "Hier werkt het nog");
		
		Lid eter = lidService.getLidByNum(lidnummerInt);
		Gerecht keuze = gerechtService.getNaamById(gerechtIdInt);
		
		Aanmelding newAanmelding = new Aanmelding(eter, keuze);
		
		System.out.println(eter	+ " Dit is de eter");
		System.out.println(keuze + " Dit is de keuze");
		
		if (aanmeldingService.save(newAanmelding) == true) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("lidnummer", lidnummer);
			job.add("gerechtId", gerechtId);
			
			return job.build().toString();
		} else {
			return null;
		}
	}
}
