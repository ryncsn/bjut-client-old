package cn.edu.bjut.bjutclient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class myPassageBrowser extends Activity {

	TextView passage;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		setContentView(R.layout.my_browser);
		passage = (TextView) this.findViewById(R.id.TextContent);
		new myLoginBg()
				.execute(intent.getStringExtra(myLoginUI.MY_PASSAGE_ADD));
	}

	private class myLoginBg extends AsyncTask<String, Integer, String> {
		boolean Timeout = false;
		boolean NWFlag = false;

		@Override
		protected void onPreExecute() {
			passage.setText("加载中...");
		}

		protected void onCancelled() {

		}

		protected void onProgressUpdate(Integer... progress) {
		}

		protected void onPostExecute(String params) {
			setContentView(R.layout.my_browser);
			if (params != null) {
				((TextView) findViewById(R.id.TextContent))
						.setText(myParseModule.passageParser(params).trim());
			} else
				((TextView) findViewById(R.id.TextContent)).setText("Error");
		}

		protected String doInBackground(String... params) {
			myLoginModule client = new myLoginModule();
			String html = null;
			try {
				html = client.getPage(params[0]);
			} catch (ClientProtocolException e) {
				NWFlag = true;
				e.printStackTrace();
			} catch (IOException e) {
				NWFlag = true;
				e.printStackTrace();
			} catch (Exception e) {
				Timeout = true;
				e.printStackTrace();
			}
			return html;
		}

	}
}
