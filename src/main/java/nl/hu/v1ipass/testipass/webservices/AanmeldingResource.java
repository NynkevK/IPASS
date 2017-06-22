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
	LidDAO lidDAO = new LidDAO();
	GerechtDAO gerechtDAO = new GerechtDAO();
	AanmeldingService service = ServiceProvider.getAanmeldingService();
	
	private JsonObjectBuilder aanmeldingToJson(Aanmelding aanmelding) {
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("lidnummer", aanmelding.getEter().getLidNummer());
		job.add("lidnaam", aanmelding.getEter().getName());
		job.add("gerechtnaam", aanmelding.getGerecht().getName());
		
		return job;
	}
	
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
	
	@POST
	@Produces("application/json")
	public String addAanmelding(InputStream is) throws SQLException {		
		JsonObject object = Json.createReader(is).readObject();
		System.out.println(object);
		
		String lidnummer = object.getString("lidnummer");
		String gerechtId = object.getString("gerechtId");
		String date = object.getString("datum");
		
		Gerecht gerechtTest = gerechtDAO.getIdByNaamDatum(gerechtId, date);
		
		int lidnummerInt = Integer.parseInt(lidnummer);
		int gerechtIdInt = gerechtTest.getId();
		
		Lid eter = lidDAO.getLidByNummer(lidnummerInt);
		Gerecht keuze = gerechtDAO.getNaamById(gerechtIdInt);
		
		Aanmelding newAanmelding = new Aanmelding(eter, keuze);
		
		System.out.println(eter);
		System.out.println(keuze);
		
		service.save(newAanmelding);
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("lidnummer", lidnummer);
		job.add("gerechtId", gerechtId);
		
		return job.build().toString();
	}
}
