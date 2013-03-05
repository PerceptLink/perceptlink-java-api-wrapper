package runtime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.TimeZone;

/**
 * @exclude
 * @author Scott Riley, PerceptLink.com
 */
class ApiDataPacketBuilder 
{
	
	private final String DTS_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private SimpleDateFormat _dts_formatter = null;

	public ApiDataPacketBuilder() 
	{
	
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		this._dts_formatter = this.getSimpleDateFormatter();
		
	}
	
	public ArrayList< LinkedHashMap<String,Object> >  buildDataPacket( ArrayList<ApiEngagementRecord> recs )
	{
		ArrayList< LinkedHashMap<String,Object> > dataMap = new ArrayList< LinkedHashMap<String,Object> >();
						
		for(ApiEngagementRecord item : recs)
		{
			
			LinkedHashMap<String,Object> rec = new LinkedHashMap<String,Object>();
			LinkedHashMap<String,Object> chrono = new LinkedHashMap<String,Object>();
			LinkedHashMap<String,Object> engagement = new LinkedHashMap<String,Object>();
			
			chrono.put("occurred", this.convertDateToDatetimeString( item.getDate() ));		
			
			engagement.put("type", item.getEngagementType());
			engagement.put("weight", item.getEngagementWeight());
			
			rec.put("chrono", chrono);
			rec.put("engagement", engagement);
			rec.put("identity",item.getIdentity());
			rec.put("context",item.getContext());
			rec.put("features",item.getFeatures());
			rec.put("itemset",  new ArrayList< LinkedHashMap<String,Object> >());
			
			ArrayList< LinkedHashMap<String,Object> > itemset = new ArrayList< LinkedHashMap<String,Object> >();
			
			for(ApiItemRecord asi : item.getItemset())
			{
				LinkedHashMap<String,Object> itemInfo = new LinkedHashMap<String,Object>();
				itemInfo.put("item_id", asi.getItemId());
				itemInfo.put("features", asi.output());
								
				itemset.add(itemInfo);
				
			}
			
			rec.put("itemset", itemset);
			
			dataMap.add(rec);
			
			
		}
		
		return dataMap;
		
	}
	
	private SimpleDateFormat getSimpleDateFormatter()
	{
		return new SimpleDateFormat(this.DTS_FORMAT);

	}

	private String convertDateToDatetimeString(Date dt)
	{

		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		Date dt2 = cal.getTime();
		return _dts_formatter.format(dt2);


	}
	

}
