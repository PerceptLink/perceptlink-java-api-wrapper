
package runtime;

import java.util.Date;

/**

An interface for the development and description of Engagement classes

 @author Scott Riley, PerceptLink.com
 */
public interface ApiEngagement
{
	
	public void contextBuilder(String name, Object value);
	public void identityBuilder(String name, Object value);
	public void featureBuilder(String name, Object value);
	public void itemsetBuilder(ApiItemRecord item);
	public void setEngagement(String engagementType, double engagementWeight);
	
}
