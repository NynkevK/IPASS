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
	private LidDAO lidDAO = new LidDAO();
	
	private JsonObjectBuilder gerechtToJson(Gerecht gerecht){
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("id", gerecht.getId())
        	.add("datum", gerecht.getDatum())
        	.add("name", gerecht.getName())
        	.add("betaling", gerecht.getBetaling());
        return job;
	}
	
	@POST
	@RolesAllowed({"user", "admin"})
	public Response addEter (@FormParam("lidNummer") int lidNummer, @FormParam("name") String gerecht, @FormParam("datum") String datum) {
		GerechtService service = ServiceProvider.getGerechtService();
        
        int lidNummerInt = (lidNummer);
        String gerechtString = (gerecht);
        String datumString = (datum);	
        
        Lid eter = lidDAO.getLidByNummer(lidNummerInt);
         
        Gerecht newGerecht = new Gerecht(datumString, gerechtString, eter);
        
        if(service.addGerecht(newGerecht) == null){
            Gerecht returnGerecht = service.addGerecht(newGerecht);
            String a = gerechtToJson(returnGerecht).build().toString();
            return Response.ok(a).build();
        } else {
            return Response.status(Response.Status.FOUND).build();
        }
	}
}
