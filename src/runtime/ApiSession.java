package runtime;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.json.simple.parser.JSONParser;

/**
 
A class to provide the functionality of an Api Session

  @author Scott Riley, PerceptLink.com
 */
public class ApiSession 
{

	final private int _batch_size = 1000;
	private String _apiKey = null;
	private String _apiPostURL = null;
	private String _rawHTTPRequest;
	private String _rawHTTPResponse;
	private LinkedHashMap<String,Object> _mostRecentApiSessionRecord = null;
	private ArrayList<ApiEngagementRecord> _asr_list = new ArrayList<ApiEngagementRecord>();
	
	
	/**
	 *
	* @param apiKey -- A String containing the Api Key from PerceptLink
	@param apiPostURL -- A String containing the target URL at PerceptLink
	 */
	public ApiSession(String apiKey, String apiPostURL)
	{
		
		this._apiKey = apiKey;
		this._apiPostURL = apiPostURL;
		
		if (this._apiKey == null)
		{
			
			this.consolePrint("Did not supply an Api Key", true);
		}
		
		if (this._apiPostURL == null)
		{
			
			this.consolePrint("Did not supply an Api URL", true);
		}
	
	}
	
	/**
	 
	A method to receive a completed Engagement record for posting to the remote service
	
	 @param aer -- An Engagement instance to add to the ApiSession's outgoing list
	 */
	public void addEngagementEvent( ApiEngagementRecord aer)
	{
		
		this._asr_list.add(aer);
		
	}
	
	/**
	 
	A method to send Engagement events to the remote service
	
	 */
	public void dispatchEngagementEvents()
	{
		
		if (this._asr_list.size() < 1)
		{
			this.consolePrint("No records to dispatch", false);
			
		}
		
		ApiDataPacketBuilder pb = new ApiDataPacketBuilder();
		ArrayList< LinkedHashMap<String,Object> > dat = pb.buildDataPacket( this._asr_list); 
		
		int list_size = dat.size();
		int countdown = list_size; 
				
		int counter = 0;
		
		while(counter < list_size)
		{
			
			int end_marker = counter + this._batch_size;
			
			if ( end_marker > list_size )
			{
				
				end_marker = list_size;
				
			}
			
			ArrayList< LinkedHashMap<String,Object> > batch = new ArrayList< LinkedHashMap<String,Object> >( dat.subList(counter, end_marker ));
			
			//perform POST
			ApiRequestMaker areq = new ApiRequestMaker( this, this._apiPostURL, this._apiKey);
			
			long st = System.currentTimeMillis();
			String result = areq.postData( batch );
			long ed = System.currentTimeMillis();
			long timeRequired = ed - st;
			
			this.setRawHTTPResponse(result);
			
			//check status
			int rc = ApiResponseReader.getResultCode(result);
									
			if (rc < 400)
			{
				
				counter = end_marker;
				this._mostRecentApiSessionRecord = batch.get( batch.size()-1);
				this.consolePrint("Successfully sent batch of "+ batch.size() +" engagement records (" + counter + "/" + list_size +") in " +timeRequired+ " milliseconds", false);
								
			}
			
			else
			{
				
				String rm = ApiResponseReader.getResultMessage(result);
				this.consolePrint("Post failure with message: " + rm+ "; you can get most recent successfully sent record and try again", false);
				break;
				
			}
			
		}
		
		
	}
	
	/**
	 *
	 * @return Last record successfully received and saved during this session.
	 */
	LinkedHashMap<String,Object> getLastSessionRecordDispatched()
	{
		
		return this._mostRecentApiSessionRecord;
		
		
	}
	
	
	/**
	
	A method to acquire from the remote service the last Engagement record submitted.  (Useful for keeping tracking serial submissions)
	
	@return  LinkedHashMap instance containing the data in the most recent Engagement record submission
	*/
	
	public LinkedHashMap<String,Object> fetchLastEngagementRecordSubmitted()
	{
		
		ApiRequestMaker areq = new ApiRequestMaker( this, this._apiPostURL, this._apiKey);
		String res = areq.fetchLastEngagementRecordSubmitted();
		this.setRawHTTPResponse(res);
		
		LinkedHashMap<String,Object> retval = new LinkedHashMap<String,Object>();
		JSONParser jp = new JSONParser(  );
			
		try 
		{
				
			Object nobj = jp.parse(this.getRawHTTPResponse(), ApiResponseReader.getContainerFactory());
			retval = (LinkedHashMap<String,Object>) nobj;
						
		}
		
		catch(Exception e)
		{
			
			System.out.println( "Failure on JSON parsing: ");
			e.printStackTrace();
			return null;
		}
		
		ArrayList< LinkedHashMap<String,Object >> data = this.extractData();
		
		return data.get(0);
	}
	
	/**
	 
	A method to obtain from the remote service all the Item recommendations for this Api Key.  The actual recommendations will be accessed via the extractData method.
	 
	 */
	public void getItemRecommendations()
	{
		
		ApiRequestMaker areq = new ApiRequestMaker( this, this._apiPostURL, this._apiKey);
		
		String res = areq.fetchRecommendations();
		this.setRawHTTPResponse(res);
		
				
			
	}
	
	/**
	
	A method to obtain the Item recommendations for a single Item Id
	
	@param req -- An ApiSingletonRequest delimiting the Item Id for the request.  The actual recommendations will be accessed via the extractData method.
	*/
	
	public void getItemRecommendation( ApiSingletonRequest req)
	{
		
		ApiRequestMaker areq = new ApiRequestMaker( this, this._apiPostURL, this._apiKey);
		String res = areq.fetchRecommendation( req);
		this.setRawHTTPResponse(res);		
							
	}
	
	/**
	 
	A method to obtain from the remote service all the content allocations for this Api Key.  The actual allocations will be accessed via the extractData method.
	 
	 */
	public void getContentAllocations()
	{
		
		ApiRequestMaker areq = new ApiRequestMaker( this, this._apiPostURL, this._apiKey);
		String res = areq.fetchAllocations();
		this.setRawHTTPResponse(res);
						
	}
	
	/**
	
	A method to obtain the content allocations for a single group specifier
	
	@param req -- An ApiSingletonRequest delimiting the group specifier for the request.  The actual allocations will be accessed via the extractData method.
	*/
	
	public void getContentAllocation( ApiSingletonRequest req )
	{
		
		ApiRequestMaker areq = new ApiRequestMaker( this, this._apiPostURL, this._apiKey);
		String res = areq.fetchAllocation( req);
		this.setRawHTTPResponse(res);
						
		
	}
	
	/**

	A method to get the received recommendations or allocations 
	
	@return An ArrayList of LinkedHashMap instances, each containing a dictionary of the recommendations or allocations
	*/
	
	public ArrayList< LinkedHashMap<String,Object> > extractData()
	{
		this.verifyFetchState( this._rawHTTPResponse );
		return ApiResponseReader.getDataElements( this._rawHTTPResponse );
		
	}
	
	void setRawHTTPRequest(String rr)
	{
		this._rawHTTPRequest = rr;
		
	}
	
	String getRawHTTPRequest()
	{
		
		return this._rawHTTPRequest;
	}
	
	void setRawHTTPResponse(String rr)
	{
		this._rawHTTPResponse = rr;
		
	}
	
	String getRawHTTPResponse()
	{
		
		return this._rawHTTPResponse;
	}
	
	
	
	private void verifyFetchState(String res)
	{
		
		if (ApiResponseReader.getResultCode(res) > 200)
		{
			this.consolePrint("Error on Fetch: " + ApiResponseReader.getResultMessage(res), true);
			
		}
		
	}
			
	private void consolePrint(String message, boolean die)
	{
		
		System.out.println( message );
		
		if (die == true)
		{
			
			System.exit(1);
		}
		
		
	}
	
}
