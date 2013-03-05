package runtime;


/**

 @author Scott Riley
 */
class ApiHttpResponseObject
{

	String _original_url;
	String _redirect_url;
	int _response_code;
	String _content;
	long _time_elapsed;

	public ApiHttpResponseObject(String original_url, String redirect_url,
			int response_code, String content, long time_elapsed)
	{

		this._original_url = original_url;
		this._redirect_url = redirect_url;
		this._response_code = response_code;
		this._content = content;
		this._time_elapsed = time_elapsed;


	}

	public String getOriginalURL()
	{

		return this._original_url;

	}

	public String getRedirectURL()
	{

		return this._redirect_url;

	}

	public String getLatestURL()
	{
		if (this._redirect_url != null)
		{
			return this._redirect_url;

		}

		return this._original_url;

	}

	public String getContent()
	{
		return this._content;

	}

	public int getResponseCode()
	{

		return this._response_code;

	}

	public int getSize()
	{

		return this._content.length();

	}

	public long getTimeElapsed()
	{
		return this._time_elapsed;

	}
}
