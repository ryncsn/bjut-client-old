package cn.edu.bjut.bjutclient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	public final static int MY_EXTRA_TYPE_MY = 1;
	public final static int MY_EXTRA_JW_TYPE = 2;
	public final static String MY_EXTRA_NAME = "cn.edu.bjut.bjutclient.my.name";
	public final static String MY_EXTRA_TYPE = "cn.edu.bjut.bjutclient.type";
	public final static String MY_EXTRA_PASSWORD = "cn.edu.bjut.bjutclient.my.password";
	public final static String TRANSIT_MY_PAGE = "cn.edu.bjut.bjutclient.my.loginpage";
	public final static String TRANSIT_JW_PAGE = "cn.edu.bjut.bjutclient.jw.loginpage";
	private EditText myLoginName;
	private EditText myLoginPasswd;

	public void myLoginAction(View v) {
		System.out.println("Start Activity");
		new myLoginBg().execute(myLoginName.getText().toString(), myLoginPasswd
				.getText().toString());
	}
	public void jwLoginAction(View v) {
		System.out.println("Start Activity");
		new jwLoginBg().execute(myLoginName.getText().toString(), myLoginPasswd
				.getText().toString());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myLoginName = (EditText) this.findViewById(R.id.myLoginName);
		myLoginPasswd = (EditText) this.findViewById(R.id.myLoginPasswd);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class myLoginBg extends AsyncTask<String, Integer, String> {
		private boolean WrongPWFlag = false;
		private boolean NWFlag = false;
		private String firstPage;

		@Override
		protected void onPreExecute() {
			setContentView(R.layout.login);
		}

		protected void onCancelled() {

		}

		protected void onProgressUpdate(Integer... progress) {
		}

		protected void onPostExecute(String params) {
			if (WrongPWFlag) {
				setContentView(R.layout.activity_main);
				new AlertDialog.Builder(MainActivity.this).setTitle(
						MainActivity.this.getString(R.string.WrongPW)).show();
			} else if (NWFlag) {
				setContentView(R.layout.activity_main);
				new AlertDialog.Builder(MainActivity.this).setTitle(
						MainActivity.this.getString(R.string.NetworkDown))
						.show();
			} else
				setContentView(R.layout.activity_main);
				startActivity(new Intent(MainActivity.this, myLoginUI.class)
						.putExtra(TRANSIT_MY_PAGE, firstPage));
		}

		protected String doInBackground(String... params) {
			myLoginModule client = new myLoginModule();

			try {
				client.loginIn(params[0], params[1]);
				firstPage = client
						.getPage("https://my.bjut.edu.cn/index.portal");
			} catch (ClientProtocolException e) {
				NWFlag = true;
				e.printStackTrace();
			} catch (IOException e) {
				NWFlag = true;
				e.printStackTrace();
			} catch (Exception e) {
				if (e.getMessage().equals("WRONG_PASSWD"))
					WrongPWFlag = true;
				else
					e.printStackTrace();
			}
			return firstPage;
		}

	}

	private class jwLoginBg extends AsyncTask<String, Integer, String> {
		private boolean WrongPWFlag = false;
		private boolean NWFlag = false;
		private String firstPage;
		@Override
		protected void onPreExecute() {
			setContentView(R.layout.login);
		}

		protected void onCancelled() {

		}

		protected void onProgressUpdate(Integer... progress) {
		}

		protected void onPostExecute(String params) {
			if (WrongPWFlag) {
				setContentView(R.layout.activity_main);
				new AlertDialog.Builder(MainActivity.this).setTitle(
						MainActivity.this.getString(R.string.WrongPW)).show();
			} else if (NWFlag) {
				setContentView(R.layout.activity_main);
				new AlertDialog.Builder(MainActivity.this).setTitle(
						MainActivity.this.getString(R.string.NetworkDown))
						.show();
			} else
				setContentView(R.layout.activity_main);
				startActivity(new Intent(MainActivity.this, jwUI.class)
						.putExtra(TRANSIT_JW_PAGE, firstPage));
		}

		protected String doInBackground(String... params) {
			jwLoginModule client = new jwLoginModule();

			try {
				client.loginIn(params[0], params[1]);
				firstPage = client.getCourseTable(params[0]);
			} catch (ClientProtocolException e) {
				NWFlag = true;
				e.printStackTrace();
			} catch (IOException e) {
				NWFlag = true;
				e.printStackTrace();
			} catch (Exception e) {
				if (e.getMessage().equals("WRONG_PASSWD"))
					WrongPWFlag = true;
				else
					e.printStackTrace();
			}
			return firstPage;
		}

	}

}
