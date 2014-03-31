package de.mosst.skeletons.gae.resteasy.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.appengine.api.datastore.EntityNotFoundException;

import de.mosst.skeletons.gae.resteasy.annotation.NoAccess;
import de.mosst.skeletons.gae.resteasy.model.Traktor;
import de.mosst.skeletons.gae.resteasy.model.TraktorDAO;

@Path("/traktor")
public class TraktorRS {

	@GET
	@Path("/create/{marke}")
	@Produces("application/json; charset=UTF-8")
	public Traktor create(@PathParam("marke") String marke){
		return TraktorDAO.INSTANCE.create(marke);
	}
	
	@POST
	@Path("/update")
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public Traktor update(Traktor traktor){
		return TraktorDAO.INSTANCE.update(traktor);
	}
	
	@GET
	@Path("/load/{id}")
	@Produces("application/json; charset=UTF-8")
	public Traktor load(@PathParam("id") String id) throws EntityNotFoundException{
		return TraktorDAO.INSTANCE.load(id);
	}
	
	@DELETE
	@Path("/delete/{id}")
	public void delete(@PathParam("id") String id){
		TraktorDAO.INSTANCE.delete(id);
	}

	@NoAccess
	@DELETE
	@Path("/formatGAE")
	public void formatGAE(){
		
	}
}
