package de.mosst.skeletons.gae.resteasy.testtools;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.junit.Assert;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import static org.junit.Assert.*;


public class JsonRestInterfaceTool {
	
	private HashMap<String, String> testData;
	private HashMap<String, HTTPMethode> testMethods;
	private Dispatcher dispatcher;
	
	
	public void addRestClasses(Class<?> ...restClasses) {
		initDispatcher(restClasses);
	}
	
	public void setPathToJsonFiles(URL dirWithJsonFiles) throws FileNotFoundException {
		testDatenEinlesen(dirWithJsonFiles);
    }
	
	public void run() throws URISyntaxException, JSONException {
	
		assertFalse("No JSON files exist!", getURLs().isEmpty());
		
		for (String url : getURLs()) {
			HTTPMethode methode = getMethode(url);
			System.out.println("Methode: " + methode + " | URL: " + url );
			if(methode == HTTPMethode.GET){
				testGET(url);
			}
			else if(methode == HTTPMethode.POST){
				testPOST(url);
			}
		}
	}

	private void testPOST(String url) throws URISyntaxException, JSONException {
	    MockHttpRequest request = MockHttpRequest.post(url);
	    request.contentType(MediaType.APPLICATION_JSON);
	    request.content(getBody(url).getBytes());
	    
	    MockHttpResponse response = new MockHttpResponse();

	    dispatcher.invoke(request, response);

	    Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

    }

	private void testGET(String url) throws URISyntaxException, JSONException {
	    MockHttpRequest request = MockHttpRequest.get(url);
	    MockHttpResponse response = new MockHttpResponse();

	    dispatcher.invoke(request, response);

	    Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
	    
	    String sollJson = new JSONObject(getBody(url)).toString();
	    String istJson = new JSONObject(response.getContentAsString()).toString();
	    
	    Assert.assertEquals(sollJson, istJson);
    }

	private void initDispatcher(Class<?>[] restClasses) {
		
		dispatcher = MockDispatcherFactory.createDispatcher();

		for (Class<?> clazz : restClasses) {
			POJOResourceFactory noDefaults = new POJOResourceFactory(clazz);
			dispatcher.getRegistry().addResourceFactory(noDefaults);
		}
	}

	private void testDatenEinlesen(URL dirWithJsonFiles) throws FileNotFoundException {
		File dir = new File(dirWithJsonFiles.getFile());

		File[] listFiles = dir.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".json");
			}
		});

		testData = new HashMap<String, String>();
		testMethods = new HashMap<String, HTTPMethode>();

		for (File file : listFiles) {
			String name = file.getName().replace(".json", "");
			name = name.replaceAll("_", "/");
			name = calculateHTTPMethode(name);
			@SuppressWarnings("resource")
            String value = new Scanner(file).useDelimiter("\\A").next();
			testData.put(name, value);
		}
    }

	private String calculateHTTPMethode(String name) {
	    for(HTTPMethode meth : HTTPMethode.values()){
	    	if(name.startsWith(meth.name())){
	    		name = name.replaceFirst(meth.name(), "");
	    		testMethods.put(name, meth);
	    		return name;
	    	}
	    }
	    return name;
    }

	private Set<String> getURLs() {
		return testData.keySet();
    }

	private String getBody(String url) {
		return testData.get(url);
    }
	
	private enum HTTPMethode {GET, POST, DELETE, PUT}

	private HTTPMethode getMethode(String url) {
		return testMethods.get(url);
    }

}
