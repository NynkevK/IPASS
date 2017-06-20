package nl.hu.v1ipass.ipass.webservices;

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

import nl.hu.v1ipass.ipass.model.*;

@Path("/leden")
public class LidResource {
	
	private JsonObjectBuilder lidToJson(Lid lid){
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("name", lid.getName())
        	.add("lidNummer", lid.getLidNummer());
        return job;
    }
	
//	@GET
//	@Produces("application/json")
//	public String getLeden(@FormParam("datum") String datum) {
//		LidService service = ServiceProvider.getLidService();
//		List<Lid> alleLeden = new ArrayList<Lid>();
//	}
}
