package framework.retrieval.oth.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClient4Util {

	/**
	 * java后台直接post请求
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String postRequest(String url, HttpEntity requestEntity)
			throws ClientProtocolException, IOException {

		String rntStr = "";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost req = new HttpPost(url);
			if (requestEntity != null) {
				req.setEntity(requestEntity);
			}

			System.out.println("executing request to ");

			HttpResponse rsp = httpclient.execute( req);
			HttpEntity entity = rsp.getEntity();

			System.out.println(rsp.getStatusLine());
			Header[] headers = rsp.getAllHeaders();
			for (int i = 0; i < headers.length; i++) {
				System.out.println(headers[i]);
			}
			if (entity != null) {
				rntStr = EntityUtils.toString(entity);
			}

		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return rntStr;

	}

	/**
	 * @param args
	 * @throws F
	 * @throws ClientProtocolException
	 */
	public static void main(String[] args) throws ClientProtocolException,IOException {

		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		formParams.add(new BasicNameValuePair("user", "{'email':'12323'}"));
		HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");

		String a = postRequest("http://127.0.0.1:8080/jeesite/f/restful/show", entity);
		System.out.println(a);

	}

}
