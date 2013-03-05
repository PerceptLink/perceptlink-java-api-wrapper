package runtime;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.json.simple.*;


/**
 *
 * @author Scott Riley, PerceptLink.com
 */

class ApiRequestMaker 
{

	private ApiSession _aso = null;
	private String _url;
	private String _apiKey;
	private final String _contentType = "application/json";
	private final int _defaultTimeout = 5000;
	private final String _default_user_agent = "PerceptLink JavaWrapper v1.0";
	
	public ApiRequestMaker(ApiSession aso, String url, String apiKey) 
	{
	
		this._aso = aso;
		this._url = url;
		this._apiKey = apiKey;
		
	
	}
	
	public String fetchRecommendations()
	{
		
		String request = this.buildRequest( "fetch_recommendations" );
		return this.doFetch(request);
		
	}
	
	public String fetchAllocations()
	{
		
		String request = this.buildRequest( "fetch_allocations" );
		return this.doFetch(request);
		
	}
	
	public String fetchLastEngagementRecordSubmitted()
	{
		
		String request = this.buildRequest( "last_engagement_record_submitted" );
		return this.doFetch(request);
		
	}
	
	public String fetchRecommendation( ApiSingletonRequest criteria)
	{
		
		String request = this.buildSingletonRequest( "fetch_recommendation" , criteria.output() );
		return this.doFetch(request);
		
	}
	
	public String fetchAllocation( ApiSingletonRequest criteria )
	{
		
		String request = this.buildSingletonRequest( "fetch_allocation", criteria.output() );
		return this.doFetch(request);
		
	}
	
	
	public String postData( ArrayList< LinkedHashMap<String,Object> > data)
	{
		
		String request = this.buildPostDataRequest( "post_event_data", data );
		return this.doFetch(request);
		
	}
		
	private String doFetch( String request)
	{
		ApiHttpURLFetcher uf = new ApiHttpURLFetcher( this._default_user_agent, this._defaultTimeout, "POST");
		uf.postData(this._url, this._contentType, request);
		return uf.getContent();
		
	}
	
	private String buildRequest( String type )
	{
		
				
		LinkedHashMap<String,Object> header = new LinkedHashMap<String,Object>();
		header.put("api_key", this._apiKey);
		header.put("type", type);
		
		return this.updateRawHTTPRequest( JSONValue.toJSONString( header ) );
		
	}
	
	private String buildSingletonRequest( String type, LinkedHashMap<String,Object> criteria )
	{
		
				
		LinkedHashMap<String,Object> header = new LinkedHashMap<String,Object>();
		header.put("api_key", this._apiKey);
		header.put("type", type);
		header.put("criteria", criteria);
		
		return this.updateRawHTTPRequest( JSONValue.toJSONString( header ) );
		
	}
	
	private String buildPostDataRequest( String type, ArrayList< LinkedHashMap<String,Object> > data )
	{
						
		LinkedHashMap<String,Object> header = new LinkedHashMap<String,Object>();
		header.put("api_key", this._apiKey);
		header.put("type", type);
		header.put("data", data);
		
		return this.updateRawHTTPRequest( JSONValue.toJSONString( header ) );
		
	}
	
	private String updateRawHTTPRequest( String rr)
	{
		this._aso.setRawHTTPRequest(rr);
		return rr;
		
	}
	
	
	
}
