package de.mosst.skeletons.gae.resteasy.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import static com.google.appengine.api.datastore.FetchOptions.Builder.*;

import static org.junit.Assert.*;

public class TraktorDAOTest {
	
	protected final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(), new LocalMemcacheServiceTestConfig());
	
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
		//setup
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		MemcacheService cache = MemcacheServiceFactory.getMemcacheService("Traktor");

		//precondition
		assertEquals(0, datastore.prepare(new Query("Traktor")).countEntities(withLimit(10)));
		assertEquals(0, cache.getStatistics().getItemCount());
		
		//run
		Traktor traktor = TraktorDAO.INSTANCE.create("Niva");
		
		//verify
		assertEquals(1, datastore.prepare(new Query("Traktor")).countEntities(withLimit(10)));
		assertEquals(1, cache.getStatistics().getItemCount());
		
		assertEquals("Niva", traktor.marke);
	}

	@Test
	public void testUpdate() throws Exception {
		//setup
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		MemcacheService cache = MemcacheServiceFactory.getMemcacheService("Traktor");
		
		Traktor traktor = TraktorDAO.INSTANCE.create("Don");
		
		//precondition
		assertEquals(1, datastore.prepare(new Query("Traktor")).countEntities(withLimit(10)));
		assertEquals(1, cache.getStatistics().getItemCount());
		
		//run
		Traktor traktorUpdate = TraktorDAO.INSTANCE.update(new Traktor(traktor.id, "Niva"));
		
		//verify
		assertEquals(1, datastore.prepare(new Query("Traktor")).countEntities(withLimit(10)));
		assertEquals(1, cache.getStatistics().getItemCount());
		
		assertEquals(traktorUpdate.id, traktorUpdate.id);
		assertEquals("Niva", traktorUpdate.marke);
	}
	
	@Test
	public void testLoadFromDataStore_EmptyCache() throws Exception {
		//setup
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		MemcacheService cache = MemcacheServiceFactory.getMemcacheService("Traktor");
		
		Traktor traktor = TraktorDAO.INSTANCE.create("Niva");
		
		cache.clearAll();
		
		//precondition
		assertEquals(1, datastore.prepare(new Query("Traktor")).countEntities(withLimit(10)));
		assertEquals(0, cache.getStatistics().getItemCount());
		
		//run
		Traktor traktorLoad = TraktorDAO.INSTANCE.load(traktor.id);
		
		//verify
		assertEquals(1, datastore.prepare(new Query("Traktor")).countEntities(withLimit(10)));
		assertEquals(1, cache.getStatistics().getItemCount());
		
		assertEquals(traktorLoad.id, traktorLoad.id);
		assertEquals("Niva", traktorLoad.marke);
	}
	
	@Test
	public void testDelete() throws Exception {
		//setup
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		MemcacheService cache = MemcacheServiceFactory.getMemcacheService("Traktor");
		
		Traktor traktor = TraktorDAO.INSTANCE.create("Niva");
		
		//precondition
		assertEquals(1, datastore.prepare(new Query("Traktor")).countEntities(withLimit(10)));
		assertEquals(1, cache.getStatistics().getItemCount());
		
		//run
		TraktorDAO.INSTANCE.delete(traktor.id);
		
		//verify
		assertEquals(0, datastore.prepare(new Query("Traktor")).countEntities(withLimit(10)));
		assertEquals(0, cache.getStatistics().getItemCount());
	}
	

}
