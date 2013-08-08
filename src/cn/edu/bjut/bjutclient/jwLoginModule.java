package cn.edu.bjut.bjutclient;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class jwLoginModule implements Serializable {

	private static final long serialVersionUID = -8894070995329973277L;
	/**
	 * 
	 */
	private static DefaultHttpClient jwreader = new DefaultHttpClient();
	private final String postingurl = "http://gdjwgl.bjut.edu.cn/default3.aspx";

	public void loginIn(String name, String password)
			throws ClientProtocolException, IOException, Exception {
		HttpPost httpRequest = new HttpPost(postingurl);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("__VIEWSTATE",
				"dDwtMTM2MTgxNTk4OTs7PkL61KRe+P7B/RXt5uyvWkaRApS0"));
		params.add(new BasicNameValuePair("TextBox1", name));
		params.add(new BasicNameValuePair("TextBox2", password));
		params.add(new BasicNameValuePair("ddl_js", "Ñ§Éú"));
		params.add(new BasicNameValuePair("Button1", " µÇ Â¼ "));
		httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.ISO_8859_1));
		HttpResponse httpResponse = (HttpResponse) jwreader
				.execute(httpRequest);
		if ((EntityUtils.toString(httpResponse.getEntity()).contains("错误")))
			throw new Exception("WRONG_PASSWD");
		if (httpResponse.getStatusLine().getStatusCode() == 200) {

		} else {
			throw new ClientProtocolException("Server_Down");
		}
	}

	public String getCourseTable(String name) throws ClientProtocolException, IOException {
		HttpGet httpRequest = new HttpGet("http://gdjwgl.bjut.edu.cn/xskbcx.aspx?xh="+name);
		HttpResponse httpResponse = jwreader
				.execute(httpRequest);
		return EntityUtils.toString(httpResponse.getEntity());

	}
	public CookieStore getCookies() {
		return jwreader.getCookieStore();
	}

	public boolean checkCookies() {
		return false;

	}


	private List<String> getLinks(String html) {
		return null;
	}

	public List<String> getPassage(String html) {
		return null;

	}

}
