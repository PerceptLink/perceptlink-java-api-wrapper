/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime;

import java.util.LinkedHashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.LinkedHashMap;

/**

 @author sar
 */
public class ApiItemRecordTest
{
	String tID;
	ApiItemRecord tSubject;
	LinkedHashMap<String,Object> tMap;
	
	public ApiItemRecordTest()
	{
		
		this.tID = "abc";
		this.tSubject = new ApiItemRecord( tID );
		this.tMap = new LinkedHashMap<String,Object>();
		
		String tString = "abc";
		Object tObj = new Object();
		this.tMap.put(tString, tObj);
		this.tSubject.builder(tString, tObj);
		
		tString = "def";
		tObj = new Object();
		this.tMap.put(tString, tObj);
		this.tSubject.builder(tString, tObj);
		
		tString = "ghi";
		tObj = new Object();
		this.tMap.put(tString, tObj);
		this.tSubject.builder(tString, tObj);
		
		
		
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
	 * Test of getItemId method, of class ApiItemRecord.
	 */
	@Test
	public void testGetItemId()
	{
		System.out.println("getItemId");
		assertEquals( this.tID, this.tSubject.getItemId());
		
	}

	/**
	 * Test of getItemValues method, of class ApiItemRecord.
	 */
	@Test
	public void testGetItemValues()
	{
		System.out.println("getItemValues");
		
		assertEquals(this.tMap, this.tSubject.output());
		assertNotSame(this.tMap, this.tSubject.output());
		
	}
}
