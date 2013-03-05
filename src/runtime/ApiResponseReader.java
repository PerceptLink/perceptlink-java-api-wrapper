package runtime;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ContainerFactory;

/**
 *
 * @author Scott Riley, PerceptLink.com
 */
class ApiResponseReader 
{
	
	private ApiResponseReader()	{ }
	
	/**
	 *
	 * @param json
	 * @return
	 */
	public static int getResultCode(String json)
	{
								
		Object rs = ApiResponseReader.parseResultMessage( json, "result", "code" );
		
		if (rs == null)
		{
			return 600;
			
		}
		
		return ( (Long) rs).intValue();
		
	}
	
	/**
	 *
	 * @param json
	 * @return
	 */
	public static String getResultMessage(String json)
	{
		 
		return (String) ApiResponseReader.parseResultMessage( json, "result", "message" );
		
	}
	
	public static ArrayList< LinkedHashMap<String,Object> > getDataElements( String json)
	{
		
		Object rs = ApiResponseReader.parseResultMessage( json, "data", "list" );
		
		if (rs == null)
		{
			return new ArrayList< LinkedHashMap<String,Object > >();
			
		}
		
		return (ArrayList< LinkedHashMap<String,Object> >) rs;
		
		
		
	}
	
	private static Object parseResultMessage(String jsonString, String top, String name)
	{
		
	
		JSONParser jp = new JSONParser(  );
			
		try 
		{
				
			Object nobj = jp.parse(jsonString, ApiResponseReader.getContainerFactory());
			LinkedHashMap<String,Object> map = (LinkedHashMap<String,Object>) nobj;
					
			if (map.containsKey(top) == true)
			{
				
				LinkedHashMap<String,Object> map2 = (LinkedHashMap<String,Object>) map.get(top);
			
				if (map2.containsKey(name) == true)
				{

					return map2.get( name );
				}
				
				
			}
			
			return null;
			
		}
		
		catch(Exception e)
		{
			
			System.out.println( "Failure on JSON parsing: ");
			//e.printStackTrace();
		}
		
		return null;
		
	}
	
	static ContainerFactory getContainerFactory()
	{
		
		ContainerFactory cf = new ContainerFactory()
		{
			@Override
			public LinkedHashMap<String,Object> createObjectContainer()
			{

				return new LinkedHashMap<String,Object>();

			}


			@Override
			public ArrayList<Object> creatArrayContainer()
			{
				return new ArrayList<Object>();
			}

		};
	
		return cf;
		
	}
	

}
