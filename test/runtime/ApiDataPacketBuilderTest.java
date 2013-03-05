/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.LinkedHashMap;

/**

 @author sar
 */
public class ApiDataPacketBuilderTest
{
	
	public ApiDataPacketBuilderTest()
	{
	}
	
	@BeforeClass
	public static void setUpClass()
	{
	}
	
	@AfterClass
	public static void tearDownClass()
	{
	}
	
	@Before
	public void setUp()
	{
	}
	
	@After
	public void tearDown()
	{
	}

	/**
	 * Test of buildDataPacket method, of class ApiDataPacketBuilder.
	 */
	@Test
	public void testBuildDataPacket()
	{
		System.out.println("buildDataPacket");
		ArrayList<ApiEngagementRecord> recs = new ArrayList< ApiEngagementRecord>();
		
		
		ApiDataPacketBuilder instance = new ApiDataPacketBuilder();
		ArrayList expResult = new ArrayList< ApiEngagementRecord>();
		ArrayList< LinkedHashMap<String,Object> > result = instance.buildDataPacket(recs);
		assertEquals(expResult, result);
		
		ApiEngagementRecord asr = new ApiEngagementRecord( new Date() );
		asr.contextBuilder("group", "test1");
		asr.featureBuilder("feat1", "tfeat1");
		asr.identityBuilder("ident1", "ident1");
				
		recs = new ArrayList< ApiEngagementRecord>();
		recs.add( asr );
		
		asr = new ApiEngagementRecord( new Date() );
		asr.contextBuilder("group", "test2");
		asr.featureBuilder("feat2", "tfeat2");
		asr.identityBuilder("ident2", "ident2");
		
		ApiItemRecord item = new ApiItemRecord("xyz");
		item.builder("if1", "if1_value");
		
		asr.itemsetBuilder(item);
		recs.add( asr );
		
		result = instance.buildDataPacket(recs);
		
		LinkedHashMap<String,Object> element = (LinkedHashMap<String,Object>) result.get(0).get("context");
		String tResult = (String) element.get("group");
		assertEquals(recs.get(0).getContext().get("group"), tResult );
		
		element = (LinkedHashMap<String,Object>) result.get(0).get("features");
		tResult = (String) element.get("feat1");
		assertEquals(recs.get(0).getFeatures().get("feat1"), tResult );
		
		element = (LinkedHashMap<String,Object>) result.get(0).get("identity");
		tResult = (String) element.get("ident1");
		assertEquals(recs.get(0).getIdentity().get("ident1"), tResult );
		
		assertEquals(item, recs.get(1).getItemset().get(0));
		
		
		// TODO review the generated test code and remove the default call to fail.
		//fail("The test case is a prototype.");
	}
}
