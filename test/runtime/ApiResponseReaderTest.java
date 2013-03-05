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

import java.util.LinkedHashMap;
import java.util.ArrayList;

import org.json.simple.*;

/**

 @author sar
 */
public class ApiResponseReaderTest
{
		
	public ApiResponseReaderTest()
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
	 * Test of getDataElements method, of class ApiResponseReader.
	 */
	@Test
	public void testGetDataElements()
	{
		System.out.println("getDataElements");
		
		int code = 100;
		String message = "OK";
		
		LinkedHashMap<String,Object> top = new LinkedHashMap<String,Object>();
		LinkedHashMap<String,Object> result = new LinkedHashMap<String,Object>();
		LinkedHashMap<String,Object> data = new LinkedHashMap<String,Object>();		
		
		result.put("code", code);
		result.put("message", message);
		top.put("result", result);
		
		ArrayList< LinkedHashMap<String,Object> > dataList = new ArrayList< LinkedHashMap<String,Object> >();
		LinkedHashMap<String,Object> data1 = new LinkedHashMap<String,Object>();
		data1.put("n1", "1");
		data1.put("n2", "11");
		data1.put("n3", "111");
		
		dataList.add(data1);
		
		data.put("list", dataList);
		top.put( "data" , data);
		
		String jsonString = JSONValue.toJSONString( top );
		
		ArrayList< LinkedHashMap<String,Object> > found = ApiResponseReader.getDataElements( jsonString );
				
		assertEquals(dataList, found);
		
		
	}
	
	@Test
	public void testResultCodeAndMessage()
	{
		
		int code = 100;
		String message = "OK";
		
		LinkedHashMap<String,Object> top = new LinkedHashMap<String,Object>();
		LinkedHashMap<String,Object> result = new LinkedHashMap<String,Object>();
				
		
		result.put("code", code);
		result.put("message", message);
		top.put("result", result);
		
		String jsonString = JSONValue.toJSONString( top );
		
		
		int rc = ApiResponseReader.getResultCode( jsonString);
		String rm = ApiResponseReader.getResultMessage( jsonString);
		
		assertEquals(code, rc);
		assertEquals(message, rm);
		
		
	}
}
