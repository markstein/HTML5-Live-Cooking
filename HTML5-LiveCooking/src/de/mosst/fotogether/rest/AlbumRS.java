package de.mosst.fotogether.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.appengine.api.datastore.EntityNotFoundException;

import de.mosst.fotogether.annotation.NoAccess;
import de.mosst.fotogether.model.Album;
import de.mosst.fotogether.model.AlbumDAO;

@Path("/album")
public class AlbumRS {

	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/create/{name}")
	public Album create(@PathParam("name") String name){
		return AlbumDAO.getInstance().create(name);
	}
	
	@POST
	@Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	@Path("/update")
	public Album update(Album album){
		return AlbumDAO.getInstance().update(album);
	}
	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/load/{id}")
	public Album load(@PathParam("id") String id) throws EntityNotFoundException{
		return AlbumDAO.getInstance().load(id);
	}
	
	@DELETE
	@Produces("application/json; charset=UTF-8")
	@Path("/delete/{id}")
	public boolean delete(@PathParam("id") String id){
		return AlbumDAO.getInstance().delete(id);
	}

	@NoAccess
	@DELETE
	@Produces("application/json; charset=UTF-8")
	@Path("/formatGAE")
	public void formatGAE(){
		
	}
}
