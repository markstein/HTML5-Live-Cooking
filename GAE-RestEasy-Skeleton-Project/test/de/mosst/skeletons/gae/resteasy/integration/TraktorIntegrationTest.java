package de.mosst.skeletons.gae.resteasy.integration;

import java.net.URL;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import de.mosst.skeletons.gae.resteasy.testtools.IntegrationTestTool;

import static org.junit.Assert.*;

public class TraktorIntegrationTest extends IntegrationTestTool {

	@Test
	public void testCRUDWorkflow() throws Exception {
		
		//###### CREATE
		JSONObject jsonObject = callGETreturnJSON("/backend/traktor/create/Don5");

		String id = (String) jsonObject.get("id");
		Object marke = jsonObject.get("marke");

		assertNotNull(id);
		assertEquals("Don5", marke);
		
		
		//###### UPDATE
		WebRequest request = new WebRequest(new URL(BASE_URL + "/backend/traktor/update"), HttpMethod.POST); 
		request.setAdditionalHeader("Content-Type", MediaType.APPLICATION_JSON);
		request.setRequestBody("{\"id\":\""+id+"\",\"marke\":\"Niva\"}");
		
		int statusCode = webClient.getPage(request).getWebResponse().getStatusCode(); 
		
		assertEquals(200, statusCode);
		
		//###### LOAD
		jsonObject = callGETreturnJSON("/backend/traktor/load/" + id);
		String updatedId = (String) jsonObject.get("id");
		String updatedMarke = (String) jsonObject.get("marke");
		
		assertEquals(id, updatedId);
		assertEquals("Niva", updatedMarke);
		
		
		//###### DELETE
		WebRequest delRequest = new WebRequest(new URL(BASE_URL + "/backend/traktor/delete/" + id), HttpMethod.DELETE);
		Page page = webClient.getPage(delRequest);
		
		assertEquals(200, page.getWebResponse().getStatusCode());
		
		
		//###### Format GAE - No Access Interceptor
		try {
			WebRequest formatRequest = new WebRequest(new URL(BASE_URL + "/backend/traktor/formatGAE/"), HttpMethod.DELETE);
			webClient.getPage(formatRequest);
		} catch(FailingHttpStatusCodeException e){
			assertEquals(401, e.getStatusCode());
		}
	}



}
