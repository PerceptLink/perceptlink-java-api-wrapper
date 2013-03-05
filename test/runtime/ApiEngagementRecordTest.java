/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**

 @author sar
 */
public class ApiEngagementRecordTest
{
	
	Date tDate = new Date();
	ApiEngagementRecord tSubject = new ApiEngagementRecord( tDate );
	String specimen_name = "name";
	Object specimen_object = new Object();
	
	public ApiEngagementRecordTest()
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
	 * Test of identityBuilder method, of class ApiEngagementRecord.
	 */
	@Test
	public void testIdentityBuilder()
	{
		System.out.println("identityBuilder");
		this.tSubject.identityBuilder( this.specimen_name, this.specimen_object);
		assertEquals(this.specimen_object, this.tSubject.getIdentity().get(this.specimen_name) );
		
	}

	/**
	 * Test of contextBuilder method, of class ApiEngagementRecord.
	 */
	@Test
	public void testContextBuilder()
	{
		System.out.println("contextBuilder");
		this.tSubject.contextBuilder( this.specimen_name, this.specimen_object);
		assertEquals(this.specimen_object, this.tSubject.getContext().get(this.specimen_name) );
		
	}

	/**
	 * Test of featureBuilder method, of class ApiEngagementRecord.
	 */
	@Test
	public void testFeatureBuilder()
	{
		System.out.println("featureBuilder");
		this.tSubject.featureBuilder( this.specimen_name, this.specimen_object);
		assertEquals(this.specimen_object, this.tSubject.getFeatures().get(this.specimen_name) );
	
	}

	/**
	 * Test of itemsetBuilder method, of class ApiEngagementRecord.
	 */
	@Test
	public void testItemsetBuilder()
	{
		System.out.println("itemsetBuilder");
		
		
		String itemId = "xyz";
		ApiItemRecord tItem = new ApiItemRecord( itemId );
		
		this.tSubject.itemsetBuilder(tItem );
		
		assertSame( tItem, this.tSubject.getItemset().get(0) );
		assertEquals( itemId, this.tSubject.getItemset().get(0).getItemId() );
		
	}

	/**
	 * Test of setEngagement method, of class ApiEngagementRecord.
	 */
	@Test
	public void testSetEngagement()
	{
		System.out.println("setEngagement");
		
		
		String eName = "eName";
		double eWeight = 1.25;
		this.tSubject.setEngagement(eName, eWeight);
		
		
		assertEquals(eName, this.tSubject.getEngagementType());
		assertEquals(eWeight, this.tSubject.getEngagementWeight(), .001);
		
		eWeight = -1.25;
		this.tSubject.setEngagement(eName, eWeight);
		assertEquals(0.0, this.tSubject.getEngagementWeight(), 0);
	
	}

	/**
	 * Test of getContext method, of class ApiEngagementRecord.
	 */
	@Test
	public void testGetContext()
	{
		System.out.println("getContext");
		this.tSubject.contextBuilder( this.specimen_name, this.specimen_object);
		assertEquals(this.specimen_object, this.tSubject.getContext().get(this.specimen_name) );
		
	}

	/**
	 * Test of getIdentity method, of class ApiEngagementRecord.
	 */
	@Test
	public void testGetIdentity()
	{
		System.out.println("getIdentity");
		this.tSubject.identityBuilder( this.specimen_name, this.specimen_object);
		assertEquals(this.specimen_object, this.tSubject.getIdentity().get(this.specimen_name) );
		
	}

	
	/**
	 * Test of getItemset method, of class ApiEngagementRecord.
	 */
	@Test
	public void testGetItemset()
	{
		System.out.println("getItemset");
		
		ArrayList<ApiItemRecord> tSet = new ArrayList<ApiItemRecord>();
		
		ApiItemRecord tItem = new ApiItemRecord( "abc" );
		tSet.add(tItem);
		tSubject.getItemset().clear();
		
		tSubject.getItemset().add(tItem);
		
		tItem = new ApiItemRecord( "def" );
		tSet.add(tItem);
		tSubject.getItemset().add(tItem);
		
		ArrayList<ApiItemRecord> rSet = this.tSubject.getItemset();
		
		assertEquals(rSet, tSet);
	}

	/**
	 * Test of getDate method, of class ApiEngagementRecord.
	 */
	@Test
	public void testGetDate()
	{
		System.out.println("getDate");
		assertEquals(this.tDate, this.tSubject.getDate() );
	}

	
}
