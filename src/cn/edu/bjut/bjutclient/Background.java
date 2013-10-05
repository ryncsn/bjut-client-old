package cn.edu.bjut.bjutclient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;

public class Background extends IntentService {

	// private static boolean sdcardIn;

	public Background() {
		super("BJUTC_Background");
		System.out.println("Back");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (intent.getIntExtra(MainActivity.EXTRA_TYPE, 0) == MainActivity.EXTRA_TYPE_MY) {
			new myLoginBg().execute(
					intent.getStringExtra(MainActivity.MAIN_STRING_USR),
					intent.getStringExtra(MainActivity.MAIN_STRING_PW));
		}
		if (intent.getIntExtra(MainActivity.EXTRA_TYPE, 0) == MainActivity.EXTRA_TYPE_JW) {
			new jwLoginBg().execute(
					intent.getStringExtra(MainActivity.MAIN_STRING_USR),
					intent.getStringExtra(MainActivity.MAIN_STRING_PW));
		}
	}

	public boolean putStringCache(String name, String content) {
		return true;
	}

	public String getStringCache(String name) {
		return null;
	}

	private class myLoginBg extends AsyncTask<String, Integer, String> {
		private int flag = 0;
		private String firstPage;

		@Override
		protected void onPostExecute(String params) {
			Intent intent = new Intent();
			intent.setAction(MainActivity.MAIN_ACTION_MY);
			intent.putExtra(MainActivity.MAIN_STRING, firstPage);
			intent.putExtra(MainActivity.MAIN_STATUS, flag);
			sendBroadcast(intent);
		}

		protected String doInBackground(String... params) {
			myLoginModule client = new myLoginModule();

			try {
				client.loginIn(params[0], params[1]);
				firstPage = client
						.getPage("https://my.bjut.edu.cn/index.portal");
			} catch (ClientProtocolException e) {
				flag = 1;
				e.printStackTrace();
			} catch (IOException e) {
				flag = 2;
				e.printStackTrace();
			} catch (Exception e) {
				if (e.getMessage().equals("WRONG_PASSWD"))
					flag = 3;
				else
					e.printStackTrace();
			}
			return firstPage;
		}

	}

	private class jwLoginBg extends AsyncTask<String, Integer, String> {
		private int flag;
		private String firstPage;

		@Override
		protected void onPostExecute(String params) {
			Intent intent = new Intent();
			intent.setAction(MainActivity.MAIN_ACTION_MY);
			intent.putExtra(MainActivity.MAIN_STRING, firstPage);
			intent.putExtra(MainActivity.MAIN_STATUS, flag);
			sendBroadcast(intent);
		}

		protected String doInBackground(String... params) {
			jwLoginModule client = new jwLoginModule();

			try {
				client.loginIn(params[0], params[1]);
				firstPage = client.getCourseTable(params[0]);
			} catch (ClientProtocolException e) {
				flag = 1;
				e.printStackTrace();
			} catch (IOException e) {
				flag = 2;
				e.printStackTrace();
			} catch (Exception e) {
				if (e.getMessage().equals("WRONG_PASSWD"))
					flag = 3;
				else
					e.printStackTrace();
			}
			return firstPage;
		}

	}

}
