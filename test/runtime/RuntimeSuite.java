/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**

 @author sar
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{
	runtime.ApiResponseReaderTest.class, runtime.ApiItemRecordTest.class, runtime.ApiEngagementRecordTest.class, runtime.ApiDataPacketBuilderTest.class, runtime.ApiSessionTest.class, runtime.ApiSingletonRequestTest.class, 
})
public class RuntimeSuite
{

	@BeforeClass
	public static void setUpClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}
	
}
