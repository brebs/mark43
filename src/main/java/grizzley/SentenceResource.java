package grizzley;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sentences")
public class SentenceResource {

	@POST
	@Path("/avg_len")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findAverageSentenceLength(Text json) {
		String result = "{\" avg_len \" : " + json.avgSentenceLength() + "}";
		return Response.status(201).entity(result).build();		
	}
}
