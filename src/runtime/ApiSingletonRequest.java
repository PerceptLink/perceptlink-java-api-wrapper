
package runtime;


import java.util.LinkedHashMap;


/**
 A class to create an instance of an ApiSingletonRequest, used for delimiting recommendation or allocation requests; implements ApiEntityBuilder 
 @author Scott Riley, PerceptLink.com
 */
public class ApiSingletonRequest implements ApiEntityBuilder
{

	LinkedHashMap<String,Object> _criteria = new LinkedHashMap<String,Object>();
	
	public ApiSingletonRequest() {}
	
	/**
	
	A method to add a name-value pair to the instance
	
	@param name -- name of the elememt
	@param value -- value of the element
	*/
		@Override
	public void builder( String name, Object value)
	{
		
		this._criteria.put( name , value);
		
	}
		
	/**

	A method to obtain the data for this instance	
		
	@return LinkedHashMap instance containing the data for this instance
	*/
	
	@Override
	public LinkedHashMap<String,Object> output()
	{
		
		return this._criteria;
		
	}
	
	
}
