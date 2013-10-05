package cn.edu.bjut.bjutclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends Activity {
	public final static int EXTRA_TYPE_MY = 1;
	public final static int EXTRA_TYPE_JW = 2;
	public final static int EXTRA_TYPE_FILE = 3;
	public final static String MY_NAME = "cn.edu.bjut.bjutclient.my.name";
	public final static String MY_PASSWORD = "cn.edu.bjut.bjutclient.my.password";
	public final static String JW_NAME = "cn.edu.bjut.bjutclient.jw.name";
	public final static String JW_PASSWORD = "cn.edu.bjut.bjutclient.jw.password";
	public final static String MAIN_STRING = "cn.edu.bjut.bjutclient.string";
	public final static String MAIN_STRING_PW = "cn.edu.bjut.bjutclient.stringpw";
	public final static String MAIN_STRING_USR = "cn.edu.bjut.bjutclient.stringusr";
	public final static String MAIN_STATUS = "cn.edu.bjut.bjutclient.status";
	public final static String MAIN_ACTION_MY = "cn.edu.bjut.bjutclient.my.action";
	public final static String EXTRA_TYPE = "cn.edu.bjut.bjutclient.type";
	private EditText myLoginName;
	private EditText myLoginPasswd;
	private mainReceiver receiver;
	private int loginType = EXTRA_TYPE_MY;
	private ProgressDialog pd;

	public void loginAction(View v) {
		pd = ProgressDialog.show(this, "登录中", "加载中，请稍后……");
		SharedPreferences userpass = this.getPreferences(Context.MODE_PRIVATE);
		userpass.edit()
				.putString((loginType == EXTRA_TYPE_MY) ? MY_NAME : JW_NAME,
						myLoginName.getText().toString()).commit();
		userpass.edit()
				.putString(
						(loginType == EXTRA_TYPE_MY) ? MY_PASSWORD
								: JW_PASSWORD,
						myLoginPasswd.getText().toString()).commit();
		Intent mServiceIntent = new Intent(this, Background.class);
		mServiceIntent.putExtra(EXTRA_TYPE, loginType);
		mServiceIntent.putExtra(MAIN_STRING_USR, myLoginName.getText()
				.toString());
		mServiceIntent.putExtra(MAIN_STRING_PW, myLoginPasswd.getText()
				.toString());
		this.startService(mServiceIntent);
	}

	private class mainReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if(pd!=null)pd.dismiss();
			if (intent.getIntExtra(MAIN_STATUS, 255) == 0) {
				if (loginType > 2)
				{
					startActivity(new Intent(MainActivity.this, jwUI.class)
							.putExtra(MAIN_STRING,
									intent.getStringExtra(MAIN_STRING)));
					loginType -=2 ;
				}
				else
					startActivity(new Intent(MainActivity.this,
							(loginType == EXTRA_TYPE_MY) ? myLoginUI.class
									: jwUI.class).putExtra(MAIN_STRING,
							intent.getStringExtra(MAIN_STRING)));
			} else if (intent.getIntExtra(MAIN_STATUS, 255) == 3)
				new AlertDialog.Builder(MainActivity.this).setTitle(
						MainActivity.this.getString(R.string.WrongPW)).show();
			else if (intent.getIntExtra(MAIN_STATUS, 255) == 1
					|| intent.getIntExtra(MAIN_STATUS, 255) == 2)
				new AlertDialog.Builder(MainActivity.this).setTitle(
						MainActivity.this.getString(R.string.NetworkDown))
						.show();
			else if (intent.getIntExtra(MAIN_STATUS, 255) == 4)
				new AlertDialog.Builder(MainActivity.this).setTitle("未保存")
						.show();
			else
				new AlertDialog.Builder(MainActivity.this).setTitle("未知错误")
						.show();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final SharedPreferences userpass = this
				.getPreferences(Context.MODE_PRIVATE);
		final RadioButton myRadio = (RadioButton) this
				.findViewById(R.id.myRadio);
		final RadioButton jwRadio = (RadioButton) this
				.findViewById(R.id.jwRadio);
		myLoginName = (EditText) this.findViewById(R.id.loginName);
		myLoginPasswd = (EditText) this.findViewById(R.id.loginPasswd);
		myRadio.setChecked(true);
		myRadio.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				jwRadio.setChecked(false);
				myLoginName.setText(userpass.getString(MY_NAME, ""));
				myLoginPasswd.setText(userpass.getString(MY_PASSWORD, ""));
				loginType = EXTRA_TYPE_MY;

			}

		});
		jwRadio.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myRadio.setChecked(false);
				myLoginName.setText(userpass.getString(JW_NAME, ""));
				myLoginPasswd.setText(userpass.getString(JW_PASSWORD, ""));
				loginType = EXTRA_TYPE_JW;
			}

		});
		myLoginName.setText(userpass.getString(MY_NAME, ""));
		myLoginPasswd.setText(userpass.getString(MY_PASSWORD, ""));
		receiver = new mainReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(MAIN_ACTION_MY);
		this.registerReceiver(receiver, filter);
	}

	protected void onDestory() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.savedcourse:
			loginType += 2;
			this.startService(new Intent(this, Background.class).putExtra(
					EXTRA_TYPE, EXTRA_TYPE_FILE));
			break;
		}

		return false;
	}

}
