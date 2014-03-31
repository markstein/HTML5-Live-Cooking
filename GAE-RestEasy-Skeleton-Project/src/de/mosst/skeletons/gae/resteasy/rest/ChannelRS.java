package de.mosst.skeletons.gae.resteasy.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

@Path("/channel")
public class ChannelRS {
	
	private ChannelService channelService = ChannelServiceFactory.getChannelService();
	
	@GET
	@Path("send/{name}/{message}")
	public void send(@PathParam("name") String name, @PathParam("message") String message){
		channelService.sendMessage(new ChannelMessage(name, message));
	}

}
