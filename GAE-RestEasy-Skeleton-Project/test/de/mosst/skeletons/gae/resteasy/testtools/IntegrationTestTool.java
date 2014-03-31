package de.mosst.skeletons.gae.resteasy.testtools;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class IntegrationTestTool {
	
	protected static final String BASE_URL = "http://localhost:8888";
	protected static final int OK = 200;
	protected static final int NO_ENTITY = 404;

	protected WebClient webClient;
	

	@Before
	public void setUp() throws Exception{
		WebClient webClient = new WebClient(); 
		WebRequest request = new WebRequest(new URL(BASE_URL + "/index.html"), HttpMethod.GET);
		
		try{
			webClient.getPage(request);
		} catch (Exception e) {
			System.out.println("#########   Hast du vergessen GAE zu starten?! #########");
			throw e;
		}
		this.webClient = new WebClient(); 
	}
	
	@After
	public void tearDown() throws Exception{
		this.webClient.closeAllWindows();
	}
	

	protected String getContent(Page page) throws IOException {
	    return page.getWebResponse().getContentAsString();
    }
	
	protected JSONObject callGETreturnJSON(String restUrl) throws MalformedURLException, IOException, JSONException {
	    WebRequest request = new WebRequest(new URL(BASE_URL + restUrl), HttpMethod.GET);
		Page page = webClient.getPage(request);

		String content = getContent(page);
		System.out.println("Content: " + content);

		JSONObject jsonObject = new JSONObject(content);
	    return jsonObject;
    }

}
