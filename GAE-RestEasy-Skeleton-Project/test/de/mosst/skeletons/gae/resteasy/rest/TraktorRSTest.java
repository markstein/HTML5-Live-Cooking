package de.mosst.skeletons.gae.resteasy.rest;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import de.mosst.skeletons.gae.resteasy.model.Traktor;
import de.mosst.skeletons.gae.resteasy.model.TraktorDAO;
import static com.google.appengine.api.datastore.FetchOptions.Builder.*;

import static org.junit.Assert.*;

public class TraktorRSTest {
	
	protected final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	protected static Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
	
	@BeforeClass
	public static void classSetUp(){
		POJOResourceFactory traktorResourceFactory = new POJOResourceFactory(TraktorRS.class);
		dispatcher.getRegistry().addResourceFactory(traktorResourceFactory);
	}

	@Before
	public void setUp() throws Exception {
		helper.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}
	

	@Test
	public void testCreate() throws Exception {
		// setup
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		MockHttpRequest request = MockHttpRequest.get("/traktor/create/Niva");
		MockHttpResponse response = new MockHttpResponse();
		
		//precondition
		assertEquals(0, ds.prepare(new Query("Traktor")).countEntities(withLimit(10)));
		
		// run
		dispatcher.invoke(request, response);
		

		//verification
		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
		
		assertEquals(1, ds.prepare(new Query("Traktor")).countEntities(withLimit(10)));

		JSONObject jsonObject = new JSONObject(response.getContentAsString());

		assertEquals(20, ((String)jsonObject.get("id")).length());
		assertEquals("Niva", jsonObject.get("marke"));

	}

	@Test
	public void testUpdate() throws Exception {
		
		//setup
		Traktor traktor = TraktorDAO.INSTANCE.create("Don");
		MockHttpRequest request = MockHttpRequest.post("/traktor/update");
		request.contentType(MediaType.APPLICATION_JSON);
		
		String content = "{\"id\":\""+ traktor.id +"\",\"marke\":\"Niva\"}";
		
		request.content(content.getBytes());
		MockHttpResponse response = new MockHttpResponse();

		
		//run
		dispatcher.invoke(request, response);

		
		//verification
		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
		
		JSONObject jsonObject = new JSONObject(response.getContentAsString());

		assertEquals(traktor.id, jsonObject.get("id"));
		assertEquals("Niva", jsonObject.get("marke"));
	}


}
