package hr.fer.zemris.gallery.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import com.google.gson.Gson;


/**
 * Class that retrieves data about pictures.
 * 
 * @author Zvonimir Šimunović
 *
 */
@Path("/pictures")
public class PictureInfo {

	/**
	 * Retrieves all tags for images.
	 * 
	 * @return all available tags.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTags() {
		String[] tags = PicturesData.getAllTags();
		if (tags == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		JSONObject result = new JSONObject();
		result.put("tags", tags);

		return Response.status(Status.OK).entity(result.toString()).build();
	}

	/**
	 * Return picture data for the pictures with the given {@code tag}.
	 * 
	 * @param tag tag we want our pictures to have.
	 * @return picture data for the pictures with the given {@code tag}.
	 */
	@Path("{tag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPicturesWithTag(@PathParam("tag") String tag) {

		List<Picture> pictures = PicturesData.getAllPicturesWithTag(tag);
		
		if (pictures == null || pictures.isEmpty()) {
			return Response.status(Status.NOT_FOUND).build();
		}

		Gson gson = new Gson();
		String jsonText = gson.toJson(pictures);

		return Response.status(Status.OK).entity(jsonText).build();
	}
	
	/**
	 * Return picture data for the pictures with the given {@code name}.
	 * 
	 * @param name name we want our pictures to have.
	 * @return picture data for the pictures with the given {@code name}.
	 */
	@Path("/picture/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPicturesInfo(@PathParam("name") String name) {

		Picture picture = PicturesData.getPictureFromName(name);
		
		if (picture == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		Gson gson = new Gson();
		String jsonText = gson.toJson(picture);

		return Response.status(Status.OK).entity(jsonText).build();
	}

}
