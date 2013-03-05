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

import java.util.ArrayList;

/**

 @author sar
 */
public class ApiSingletonRequestTest
{
	
	public ApiSingletonRequestTest()
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
	 * Test of builder method, of class ApiSingletonRequest.
	 */
	@Test
	public void testCriteriaBuilder()
	{
		System.out.println("criteriaBuilder");
		
		ApiSingletonRequest instance = new ApiSingletonRequest();
		
		instance.builder("name1", "value1");
		instance.builder("name2", null);
		
		assertEquals("value1", instance._criteria.get("name1"));
		assertEquals( null , instance._criteria.get("name2"));
		
		ArrayList tArray = new ArrayList();
		
		instance.builder("name3", tArray);
		assertSame( tArray , instance._criteria.get("name3"));
		
		instance.builder("name4", tArray.clone());
		assertNotSame( tArray , instance._criteria.get("name4"));
		assertEquals( tArray, instance._criteria.get("name4") );
		
		// TODO review the generated test code and remove the default call to fail.
		//fail("The test case is a prototype.");
	}

	
}
