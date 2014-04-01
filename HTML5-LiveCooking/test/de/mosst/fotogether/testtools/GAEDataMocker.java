package de.mosst.fotogether.testtools;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;


public class GAEDataMocker {
	

	
	protected final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(), 
			new LocalMemcacheServiceTestConfig());
	
	@Before
	public void setUp() throws Exception {
		helper.setUp();

		
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

}
