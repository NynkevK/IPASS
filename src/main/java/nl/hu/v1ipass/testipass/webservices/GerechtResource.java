package nl.hu.v1ipass.testipass.webservices;

import java.sql.SQLException;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import nl.hu.v1ipass.testipass.model.*;
import nl.hu.v1ipass.testipass.persistence.*;

@Path("/gerechten")
public class GerechtResource {
	
	// Dit is een algemene methode om makkelijk Json objecten te maken en voorkomt redundante code,
	// echter vond ik dit niet fijn en heb ik het alsnog in elke methoe apart gezet.
	private JsonObjectBuilder gerechtToJson(Gerecht gerecht){
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("datum", gerecht.getDatum())
        	.add("name", gerecht.getName());
        return job;
	}
	
	// Deze methodde haalt alle gerechten van een bepaalde dag op uit de database
	@GET
	@Path("{datum}")
	@Produces("application/json")
	public String getAanmeldingen(@PathParam("datum")String datum) {
		GerechtService service = ServiceProvider.getGerechtService();		
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for (Gerecht g : service.getGerechtByDate(datum)){ 
			jab.add(gerechtToJson(g));
			System.out.println(g);
		}
		
		JsonArray array = jab.build();
		return array.toString();
	}
}
