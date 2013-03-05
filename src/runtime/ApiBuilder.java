
package runtime;

import java.util.LinkedHashMap;

/**
 A class to contain the various elements' dictionaries and provide a common interface; implements ApiEntityBuilder

 @author Scott Riley, PerceptLink.com
 
*/

public class ApiBuilder implements ApiEntityBuilder
{

	LinkedHashMap<String,Object> _data;
	private int _char_limit = 128;
	
	/**
	Constructor instantiates a LinkedHashMap, which 
	serves as the 'dictionary'
	*/
		public ApiBuilder() 
	{
		this._data = new LinkedHashMap<String,Object>();
	}
	
	/**
	A method to receive the name-value pairs for the element dictionary.
	
	@param name - name of the element
	@param value -- value of the element 
	*/	
	@Override
	public void builder(String name, Object value)
	{
		this._data.put( name, this.preprocessObject(value));
	}

	/**
	A method to provide the entire dictionary.
	
	@return a LinkedHashMap<String,Object> instance containing the name-value pairs
	*/		
	@Override
	public LinkedHashMap<String, Object> output()
	{
		return this._data;
	}
	
	/*
	
	We use the following method to process the given Object
	to conform to the API specifications.  Currently, 
	just one specification:  if a String, the Object can
	be no more than 128 characters in length
	
	*/
	private Object preprocessObject( Object v )
	{
		
		if (v instanceof String)
		{
			String n_o = (String) v;
			
			if (n_o.length() > this._char_limit)
			{
				
				n_o = n_o.substring(0, this._char_limit);
				v = (Object) n_o;
				
			}
			
		}
		
		return v;
		
	}

}
