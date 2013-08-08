package cn.edu.bjut.bjutclient;

import java.io.IOException;
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

public class myLoginModule {
	private boolean logedin = false;
	private static DefaultHttpClient myreader = new DefaultHttpClient();

	public void loginIn(String name, String password)
			throws ClientProtocolException, IOException, Exception {
		if(logedin&&checkCookies())
			return;
		HttpPost httpRequest = new HttpPost(
				"https://my.bjut.edu.cn/userPasswordValidate.portal");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Login.Token1", name));
		params.add(new BasicNameValuePair("Login.Token2", password));
		httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		HttpResponse httpResponse = (HttpResponse) myreader
				.execute(httpRequest);
		if ((EntityUtils.toString(httpResponse.getEntity()).contains("错误")))
			throw new Exception("WRONG_PASSWD");
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
		} else {
			throw new ClientProtocolException("SERVER_DOWN");
		}
	}

	public String getPage(String html) throws ClientProtocolException,
			IOException, Exception {
		HttpResponse httpResponse;
		HttpGet httpRequest = new HttpGet(html);
		httpResponse = myreader.execute(httpRequest);
		return EntityUtils.toString(httpResponse.getEntity());
	}

	public void setCookie(CookieStore cookieStore) {
		myreader.setCookieStore(cookieStore);
	}

	public CookieStore getCookies() {
		return myreader.getCookieStore();
	}

	public boolean checkCookies() {
		return false;

	}

}
