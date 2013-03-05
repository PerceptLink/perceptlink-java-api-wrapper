package runtime;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Date;

/**

A class to contain the data for an Engagement; implements ApiEngagement

@author Scott Riley, PerceptLink.com
 */
public class ApiEngagementRecord implements ApiEngagement
{

	private ArrayList<ApiItemRecord> _itemset = new ArrayList<ApiItemRecord>();
	
	private ApiBuilder _identity = new ApiBuilder();
	private ApiBuilder _context = new ApiBuilder();
	private ApiBuilder _features = new ApiBuilder();
	
	private String _engagement_type;
	private double _engagement_weight;
	private Date _date;
	private final int _char_limit = 128;
	
	/**
	 *
	 * @param occurred -- A Date instance containing the date and time of the Engagement
	 */
	public ApiEngagementRecord( Date occurred ) 
	{
	
		this._date = occurred;
		this._context.builder("group", null);
		
	}
	
	/**
	
	A method to build up the Identity element in the Engagement
	
	@param name -- name of the variable
	@param value -- nalue of the variable
	 */
	@Override
	public void identityBuilder(String name, Object value)
	{
		
		this._identity.builder(name, value);
		
	}
	
	/**
	A method to build up the Context element in the Engagement
	
	@param name -- name of the variable
	@param value -- nalue of the variable
	 */
	@Override
	public void contextBuilder(String name, Object value)
	{
		
		this._context.builder(name, value);
		
	}
	
	/**
	A method to build up the Features element in the Engagement
	
	@param name -- name of the variable
	@param value -- nalue of the variable 
	*/
	@Override
	public void featureBuilder(String name, Object value)
	{
		
		this._features.builder(name, value);
		
	}
	
	/**
	 A method to build up the Itemset list in the Engagement
	
	@param item -- the ApiRecordItem instance to add

	 */
	@Override
	public void itemsetBuilder( ApiItemRecord item)
	{
			
		this._itemset.add(item);
		
	}
	
	/**
	 A method to specify the engagement type and weight 
	* @param engagementType -- type of the Engagement (e.g., "purchase")
	@param engagementWeight -- the weight of the Engagement (often in money units); must be >= 0.0 
	 */
	
	@Override
	public void setEngagement(String engagementType, double engagementWeight)
	{
		
		if (engagementWeight < 0)
		{
			engagementWeight = 0.0;
			
		}
		
		if (engagementType.length() > this._char_limit)
		{
			engagementType = engagementType.substring(0, _char_limit+1);
			
		}
		
		this._engagement_type = engagementType;
		this._engagement_weight = engagementWeight;
		
	}
	
	LinkedHashMap<String,Object> getContext()
	{
		
		return this._context.output();
	}
	
	LinkedHashMap<String,Object> getIdentity()
	{
		
		return this._identity.output();
	}
	
	LinkedHashMap<String,Object> getFeatures()
	{
		
		return this._features.output();
	}
	
	ArrayList<ApiItemRecord> getItemset()
	{
		
		return this._itemset;
	}
	
	
	Date getDate()
	{
	
		return this._date;

	}
	
	String getEngagementType()
	{
		
		return this._engagement_type;
	}
	
	double getEngagementWeight()
	{
		
		return this._engagement_weight;
	}
	
	
}