package cn.edu.bjut.bjutclient;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class jwLoginModule {
	/**
	 * 
	 */
	private static DefaultHttpClient jwreader = new DefaultHttpClient();
	private final String postingurl = "http://gdjwgl.bjut.edu.cn/default2.aspx";

	public void loginIn(String name, String password, String vcode)
			throws ClientProtocolException, IOException, Exception {
		HttpPost httpRequest = new HttpPost(postingurl);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("__VIEWSTATE",
				"dDwyODE2NTM0OTg7Oz7QWQiyD/hfPCjVf5FA4RDQ+6tmDQ=="));
		params.add(new BasicNameValuePair("txtUserName", name));
		params.add(new BasicNameValuePair("TextBox2", password));
		params.add(new BasicNameValuePair("txtSecretCode", vcode));
		params.add(new BasicNameValuePair("RadioButtonList1", "Ñ§Éú"));
		params.add(new BasicNameValuePair("Button1", ""));
		params.add(new BasicNameValuePair("lbLanguage", ""));
		params.add(new BasicNameValuePair("hidPdrs", ""));
		params.add(new BasicNameValuePair("hidsc", ""));
		httpRequest
				.setEntity(new UrlEncodedFormEntity(params, HTTP.ISO_8859_1));
		HttpResponse httpResponse = (HttpResponse) jwreader
				.execute(httpRequest);
		if ((EntityUtils.toString(httpResponse.getEntity()).contains("alert")))
			throw new Exception("WRONG_PASSWD");
		if (httpResponse.getStatusLine().getStatusCode() == 200) {

		} else {
			throw new ClientProtocolException("Server_Down");
		}
	}

	public String getCourseTable(String name) throws ClientProtocolException,
			IOException {
		String tmp;
		System.out.println("Table Request");
		HttpGet httpRequestR = new HttpGet("http://gdjwgl.bjut.edu.cn/xskbcx.aspx?xh="+name);
		httpRequestR
				.addHeader("Referer","http://gdjwgl.bjut.edu.cn/");
		System.out.println("Before Table Request");
		HttpResponse httpResponseR = jwreader.execute(httpRequestR);
		System.out.println("After Table Request");
		tmp=EntityUtils.toString(httpResponseR.getEntity());
		System.out.println(tmp);
		return tmp;
	}

	public Bitmap getVcode() throws ClientProtocolException, IOException,
			Exception {

		HttpGet httpRequest = new HttpGet(
				"http://gdjwgl.bjut.edu.cn/CheckCode.aspx");
		HttpResponse httpResponse = jwreader.execute(httpRequest);
		HttpEntity entity = httpResponse.getEntity();
		byte[] bytes = EntityUtils.toByteArray(entity);
		Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return bitmap;

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
