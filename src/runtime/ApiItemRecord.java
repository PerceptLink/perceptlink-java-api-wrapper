package runtime;

import java.util.LinkedHashMap;


/**
	A class to contain the data for an Item; implements ApiEntityBuilder

	@author Scott Riley, PerceptLink.com
 */
public class ApiItemRecord implements ApiEntityBuilder
{

	String _item_id = null;
			
	LinkedHashMap<String,Object> _itemfeatures = new LinkedHashMap<String,Object>();
	
	/**
	 *
	 * @param item_id - A string containing the Item's id  
	 */
	public ApiItemRecord( String item_id )
	{
		
		this._item_id = item_id;
		
	}
	
	/**
	 
	 
		A method to add elements that describe the Item's features
	
	  @param name -- name of the added element
	  @param value -- value of the added element
	 */
	
	@Override
	public void builder( String name, Object value)
	{
		this._itemfeatures.put(name, value);
		
	}
	
	String getItemId()
	{
		
		return this._item_id;
		
	}
	
	/**
	
	A method to get the data for the Item
	
	@return LinkedHashMap instance containing the Item's name-value pairs 
	
	
	*/
	
	public LinkedHashMap<String,Object> output()
	{
		
		return this._itemfeatures;
	}
	
	
}
