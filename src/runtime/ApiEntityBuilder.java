
package runtime;

import java.util.LinkedHashMap;

/**

An interface for the development and description of EntityBuilder classes 

 @author Scott Riley, PerceptLink.com
 */
public interface ApiEntityBuilder
{
	
	public void builder(String name, Object value);
	public LinkedHashMap<String,Object> output();
}
