package de.mosst.fotogether.integration;

import java.net.URL;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import de.mosst.fotogether.testtools.IntegrationTestTool;

import static org.junit.Assert.*;

public class AlbumIntegrationTest extends IntegrationTestTool {

	@Test
	public void testFullWorkfow_Create_Update_Load_Delete() throws Exception {
		
		//###### CREATE
		JSONObject jsonObject = callGETreturnJSON("/backend/album/create/Harz");

		String id = (String) jsonObject.get("id");
		String name = (String) jsonObject.get("name");
		JSONArray images = (JSONArray) jsonObject.get("images");

		assertNotNull(id);
		assertNotNull("Harz", name);
		assertEquals(0, images.length());
		
		
		//###### UPDATE
		WebRequest request = new WebRequest(new URL(BASE_URL + "/backend/album/update"), HttpMethod.POST); 
		request.setAdditionalHeader("Content-Type", MediaType.APPLICATION_JSON);
		request.setRequestBody("{\"id\":\""+id+"\",\"name\":\"Harz-Brocken\"}");
		
		webClient.getPage(request); 
		
		
		//###### LOAD
		jsonObject = callGETreturnJSON("/backend/album/load/" + id);
		String updatedId = (String) jsonObject.get("id");
		String updatedName = (String) jsonObject.get("name");
		
		assertEquals(id, updatedId);
		assertEquals("Harz-Brocken", updatedName);
		
		
		//###### DELETE
		WebRequest delRequest = new WebRequest(new URL(BASE_URL + "/backend/album/delete/" + id), HttpMethod.DELETE);
		Page page = webClient.getPage(delRequest);
		String content = getContent(page);
		
		assertEquals("true", content);
		
		
	}



}
