/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;

/**

 @author sar
 */
public class ApiSessionTest
{
	
	ApiSession tSubject = new ApiSession( "api_key","url");
	
	public ApiSessionTest()
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
	 * Test of addEngagementEvent method, of class ApiSession.
	 */
	@Test
	public void testAddEngagementEvent()
	{
		System.out.println("addEngagementEvent");
		
		ApiEngagementRecord asr = new ApiEngagementRecord( new Date() );
		ApiSession instance = new ApiSession( "api_key", "url");
		tSubject.addEngagementEvent(asr);
		
	
	}
	
	@Test
	public void testDispatchEngagementEvent()
	{
		
		System.out.println("postData");
		String url = "https://api.perceptlink.com/api/1/test/echo";
				
		ApiSession aso = new ApiSession( "api", url);
		
		ApiEngagementRecord aer = new ApiEngagementRecord( new Date() );
		
		aer.featureBuilder("feat1", "feature_1");
		aer.contextBuilder("context1", "context_1");
		aer.identityBuilder("identity1", "identity_1");
		
		ApiItemRecord item = new ApiItemRecord( "item100");
		
		aer.itemsetBuilder(item);
		aso.addEngagementEvent(aer);
		
		aer = new ApiEngagementRecord( new Date() );
		
		aer.featureBuilder("feat2", "feature_2");
		aer.contextBuilder("group", "sg");
		aer.identityBuilder("identity2", "identity_2");
		
		
		item = new ApiItemRecord( "item200");
		
		aer.itemsetBuilder(item);
		
		aso.addEngagementEvent(aer);
		
		aso.dispatchEngagementEvents();
		
		assertEquals(aso.getRawHTTPRequest(), aso.getRawHTTPResponse());
		
		String jsonString = aso.getRawHTTPResponse();
		ArrayList< LinkedHashMap<String,Object> > dataList = new ArrayList< LinkedHashMap<String,Object> >();
 		
		JSONParser jp = new JSONParser(  );
					
		try 
		{
			
			ContainerFactory cf = ApiResponseReader.getContainerFactory();
			
			Object nobj = jp.parse(jsonString, cf);
			LinkedHashMap<String,Object> map = (LinkedHashMap<String,Object>) nobj;
					
			if (map.containsKey("data") == true)
			{
				
				dataList = (ArrayList< LinkedHashMap<String,Object> >) map.get("data");
			
			}
			
			
			
		}
		
		catch(Exception e)
		{
			
			System.out.println( "Failure on JSON parsing: ");
			e.printStackTrace();
		}
		
		//System.out.println("DATA: " + dataList);
		
		LinkedHashMap<String,Object> features = (LinkedHashMap<String,Object>) dataList.get(0).get("features");
		
		assertEquals( features.get("feat1"), "feature_1");
		
		LinkedHashMap<String,Object> context = (LinkedHashMap<String,Object>) dataList.get(1).get("context");
		
		assertEquals( context.get("group"), "sg");
		
		ArrayList <LinkedHashMap<String,Object> > itemset = ( ArrayList< LinkedHashMap<String,Object> >) dataList.get(1).get("itemset");
		
		assertEquals( itemset.get(0).get("item_id"), "item200");
	}
	
	@Test
	public void testFetchAllocations()
	{
		
		System.out.println("fetchAllocations");
		String url = "https://api.perceptlink.com/api/1/test/ok_fetch_allocations";
				
		ApiSession aso = new ApiSession( "api", url);
		aso.getContentAllocations();
		
		ArrayList< LinkedHashMap<String,Object> > data = aso.extractData();
				
		assertEquals(data.get(0).get("item_id"), 100L) ;
		assertEquals(data.get(1).get("group"), "sg") ;
		assertEquals( (Double) data.get(2).get("allocation"), 0.46, 0.000) ;
		
	}
	
	@Test
	public void testFetchRecommendations()
	{
		
		System.out.println("fetchRecommendations");
		String url = "https://api.perceptlink.com/api/1/test/ok_fetch_recommendations";
				
		ApiSession aso = new ApiSession( "api", url);
		aso.getItemRecommendations();
		
		ArrayList< LinkedHashMap<String,Object> > data = aso.extractData();
				
		assertEquals(data.get(0).get("item_id"), 100L) ;
		ArrayList recoms = (ArrayList) data.get(0).get("recommendations");
		assertEquals(recoms.get(1), "B");
		
		assertEquals(data.get(2).get("item_id"), 300L) ;
		recoms = (ArrayList) data.get(2).get("recommendations");
		assertEquals(recoms.get(2), "Y");
		
	}
	
	@Test
	public void testFetchAllocation()
	{
		
		System.out.println("fetchAllocation");
		String url = "https://api.perceptlink.com/api/1/test/ok_fetch_allocation";
				
		ApiSession aso = new ApiSession( "api", url);
		
		ApiSingletonRequest asingle = new ApiSingletonRequest();
		long itemId = 150L;
		asingle.builder("item_id", itemId);
		
		String group = "sg";
		asingle.builder("group", group);
		
		aso.getContentAllocation( asingle );
		
		ArrayList< LinkedHashMap<String,Object> > data = aso.extractData();
				
		assertEquals(data.get(0).get("item_id"), itemId);
		assertEquals(data.get(0).get("group"), "sg");
		assertEquals( (Double) data.get(0).get("allocation"), 0.99, 0.0) ;
		
		
	}
	
	@Test
	public void testFetchRecommendation()
	{
		
		System.out.println("fetchRecommendation");
		String url = "https://api.perceptlink.com/api/1/test/ok_fetch_recommendation";
				
		ApiSession aso = new ApiSession( "api", url);
		
		ApiSingletonRequest asingle = new ApiSingletonRequest();
		long itemId = 150L;
		asingle.builder("item_id", itemId);
		
		aso.getItemRecommendation( asingle );
		
		ArrayList< LinkedHashMap<String,Object> > data = aso.extractData();
				
		assertEquals(data.get(0).get("item_id"), itemId);
		
		ArrayList recoms = (ArrayList) data.get(0).get("recommendations");
		assertEquals(recoms.get(2), "C");
		
		
	}
	
	@Test
	public void testFetchLastEngagementRecordSubmitted()
	{
		
		System.out.println("fetchLastEngagementRecordSubmitted");
		String url = "https://api.perceptlink.com/api/1/test/ok_last_record_submitted";
				
		ApiSession aso = new ApiSession( "api", url);
		LinkedHashMap<String,Object> lRecord = aso.fetchLastEngagementRecordSubmitted();
		LinkedHashMap<String,Object> context = (LinkedHashMap<String,Object>) lRecord.get("context");
		
		assertEquals( context.get("group"), "sg");
	
		
	}

	
}
