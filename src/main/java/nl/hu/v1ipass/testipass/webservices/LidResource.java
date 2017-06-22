package nl.hu.v1ipass.testipass.webservices;

import java.sql.SQLException;
import java.util.*;

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

@Path("/leden")
public class LidResource {
	
	// Dit is een algemene methode om makkelijk Json objecten te maken en voorkomt redundante code,
	// echter vond ik dit niet fijn en heb ik het alsnog in elke methoe apart gezet.
	private JsonObjectBuilder lidToJson(Lid lid){
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("name", lid.getName())
        	.add("lidNummer", lid.getLidNummer());
        return job;
    }
	
	// Deze methode haalt de username van degene die inlogt op om zo achter de betreffende rol te komen voor de autorisatie.
	@GET
	@Path("{username}")
	@Produces("application/json")
	public String getRoleByUsername(@PathParam("username") String username) {
		LidService service = ServiceProvider.getLidService();
		JsonObjectBuilder job = Json.createObjectBuilder();
		
		String role = service.getRole(username);
		
		job.add("role", role);
		return job.build().toString();		
	}
}
