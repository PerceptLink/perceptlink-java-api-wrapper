package runtime;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import javax.net.ssl.SSLException;

/**

 @author Scott Riley, PerceptLink.com
 */
class ApiHttpURLFetcher
{

	String _userAgent;
	int _timeout;
	String _content;
	String _method;
	int _response_code;
	ApiHttpResponseObject _hro;

	public ApiHttpURLFetcher(String ua, int timeout, String method)
	{

		this._userAgent = ua;
		this._content = "";
		this._timeout = timeout;
		this._method = method;
		this._response_code = 600;

	}

	public static void main(String[] args)
	{

		ApiHttpURLFetcher uf = new ApiHttpURLFetcher("test_agent_1", 500, "GET");

		long total = 0;
		int m_requests = 1;
		int tsize = 0;

		for (int i = 0; i < m_requests; i++)
		{
			uf.fetchContentAsHttpResponseObject("http://drudgereport.com/", "text/html");
			ApiHttpResponseObject tro = uf.getHttpResponseObject();

			if (uf.getSuccessState() == true)
			{

				total += tro.getTimeElapsed();
				tsize += tro.getSize();
			}




		}

		System.out.println(total);
		System.out.println(total / (double) m_requests);
		System.out.println(tsize);



	}

	public String getContent()
	{

		return this._content;

	}

	public int getResponseCode()
	{

		return this._response_code;
	}

	public void fetchContent(String url, String contentType)
	{

		this.fetchURL(url, contentType, false);

	}

	public void fetchContentAsHttpResponseObject(String url, String contentType)
	{

		this.fetchURL(url, contentType, true);

	}

	public ApiHttpResponseObject getHttpResponseObject()
	{

		return this._hro;

	}

	public boolean getSuccessState()
	{
		if (this._response_code < 400)
		{
			return true;

		}

		return false;
	}

	private void intialize()
	{

		this._content = "";
		this._response_code = 600;

	}
	
	public void postData(String url, String contentType, String data)
	{
		
		this.intialize();
		long timeTaken = 0;
		boolean errState = false;

		try
		{

			URL t_url = new URL(url);

			HttpURLConnection hc = (HttpURLConnection) t_url.openConnection();

			hc.setRequestMethod("POST");

			if (this._timeout > 0)
			{

				hc.setReadTimeout(this._timeout);
			}


			hc.setDoOutput(true);
			hc.setDoInput(true);
			hc.setFollowRedirects(false);
			hc.setRequestProperty("User-Agent", this._userAgent);
			hc.setRequestProperty("content-type", contentType);
			hc.setRequestProperty("Content-Length", "" + data.length());
			hc.setRequestProperty("Cache-Control", "no-cache");
			hc.setRequestProperty("charset", "utf-8");

			long st = System.currentTimeMillis();

			hc.connect();
			
			OutputStream out = hc.getOutputStream();
			out.write( data.getBytes());
			
			//System.out.println( data);
			
			hc.getHeaderFields();
			
			this._response_code = hc.getResponseCode();

			BufferedReader r = new BufferedReader(new InputStreamReader(hc.getInputStream()));
			StringBuilder res = new StringBuilder();

			String l;
			
			while ((l = r.readLine()) != null)
			{

				res.append(l);

			}
			
			//System.out.println( res.toString());

			r.close();
			hc.disconnect();
			timeTaken = System.currentTimeMillis() - st;

			this._content = res.toString();



		} 
		
		catch (SocketTimeoutException se)
		{
			System.out.println("Socket timeout exception: " + url);
			errState = true;
			
			//this._hro = new ApiHttpResponseObject(url, url, 600, this._content, timeTaken);

		} 
		
		catch (MalformedURLException me)
		{

			System.out.println("Bad url: " + url);
			errState = true;

		} 
		
		catch (SSLException ssle)
		{
			System.out.println("Bad SSL connection: " + url);
			errState = true;

		} 
		
		catch (IOException ie)
		{

			System.out.println("Bad connection: " + url);
			System.out.println(ie.toString());
			errState = true;
			
		}
		
		catch(Exception e)
		{
			
			System.out.println("Bad connection: " + url);
			System.out.println(e.toString());
			errState = true;
			
		}

		
		if (errState == true)
		{
			
			//System.exit(0);
		}
		
	}

	private void fetchURL(String url, String contentType, boolean makeHttpResponseObject)
	{
		this.intialize();
		long timeTaken = 0;
		boolean errState = false;

		try
		{

			URL t_url = new URL(url);

			HttpURLConnection hc = (HttpURLConnection) t_url.openConnection();


			hc.setRequestMethod(this._method);

			if (this._timeout > 0)
			{

				hc.setReadTimeout(this._timeout);
			}


			hc.setDoOutput(true);
			hc.setFollowRedirects(true);
			hc.setRequestProperty("User-Agent", this._userAgent);
			hc.setRequestProperty("Content-Type", contentType);
			hc.setRequestProperty("charset", "utf-8");

			long st = System.currentTimeMillis();

			hc.connect();

			hc.getHeaderFields();

			this._response_code = hc.getResponseCode();

			BufferedReader r = new BufferedReader(new InputStreamReader(hc.getInputStream()));
			StringBuilder res = new StringBuilder();

			String l;
			while ((l = r.readLine()) != null)
			{

				res.append(l);

			}

			r.close();
			hc.disconnect();
			timeTaken = System.currentTimeMillis() - st;

			this._content = res.toString();


			if (makeHttpResponseObject == true)
			{
				this._hro = new ApiHttpResponseObject(url, hc.getURL().toString(), this._response_code, this._content, timeTaken);

			}


		} 
		
		catch (SocketTimeoutException se)
		{
			System.out.println("Socket timeout exception: " + url);
			errState = true;
			//this._hro = new ApiHttpResponseObject(url, url, 600, this._content, timeTaken);

		} 
		
		catch (MalformedURLException me)
		{

			System.out.println("Bad url: " + url);
			errState = true;

		} 
		
		catch (SSLException ssle)
		{
			System.out.println("Bad SSL connection: " + url);
			errState = true;

		} 
		
		catch (IOException ie)
		{

			System.out.println("Bad connection: " + url);
			System.out.println(ie.toString());
			errState = true;
		}


		if (errState == true)
		{
			
			System.exit(1);
		}
		

	}
}
