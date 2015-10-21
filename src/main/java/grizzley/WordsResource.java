package grizzley;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/words")
public class WordsResource {

	@POST
	@Path("/avg_len")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findAverageWordLength(Text json) {
		String result = createJSON("avg_len", Float.toString(json.avgWord()));
		return Response.status(201).entity(result).build();		
	}
	
	@POST
	@Path("/most_com")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findMostCommonWord(Text json) {
		String result = createJSON("most_com", json.mostCommon());
		return Response.status(201).entity(result).build();		
	}
	
	/*
	 * Returns a json with a key as the pathname and value as the return value
	 */
	private String createJSON(String type, String value) {
		if ((value != null) && (!type.equals("avg_len"))) {
			value = "\"" + value + "\"";
		}
		
		return "{\"" + type + "\" : " + value + "}";
	}
}