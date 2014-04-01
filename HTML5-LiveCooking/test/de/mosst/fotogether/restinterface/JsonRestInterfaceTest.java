package de.mosst.fotogether.restinterface;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.mosst.fotogether.rest.AlbumRS;
import de.mosst.fotogether.testtools.GAEDataMocker;
import de.mosst.fotogether.testtools.JsonRestInterfaceTool;
import de.mosst.fotogether.util.RandomSequenzeUtil;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ RandomSequenzeUtil.class })
public class JsonRestInterfaceTest extends GAEDataMocker{
	
	
	private static final String MOCK_SECURE_ID = "0123456789";

	@Test
    public void testAll() throws Exception {
		//setup
		
		mockStatic(RandomSequenzeUtil.class);
		when(RandomSequenzeUtil.createRandomCharSequenze(Mockito.anyInt())).thenReturn(MOCK_SECURE_ID);
		
		JsonRestInterfaceTool tool = new JsonRestInterfaceTool();

		tool.addRestClasses(AlbumRS.class);
		tool.setPathToJsonFiles(JsonRestInterfaceTest.class.getResource("testdata"));
		
		//run
		tool.run();
    }
}
