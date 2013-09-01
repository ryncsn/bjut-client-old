package cn.edu.bjut.bjutclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class myLoginUI extends FragmentActivity implements
		myPassageListFragment.PassageSelectedListener {
	public final static String MY_PASSAGE_ADD = "cn.edu.bjut.client.passageadd";
	private myParseModule parser;
	private myPagerAdapter currentMyPagerAdapter;
	private ViewPager myViewPager;

	@Override
	public void JumpToPassage(int number, String type) {
		Intent openpassage = new Intent(this, myPassageBrowser.class);
		openpassage.putExtra(MY_PASSAGE_ADD, "https://my.bjut.edu.cn/"+parser.getLink(type).get(number));
		startActivity(openpassage);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_ui);
		myViewPager = (ViewPager) findViewById(R.id.my_ui_pager);
		Intent intent = getIntent();
		parser = new myParseModule(
				intent.getStringExtra(MainActivity.TRANSIT_MY_PAGE));
		currentMyPagerAdapter = new myPagerAdapter(getSupportFragmentManager(),
				parser);
		myViewPager.setAdapter(currentMyPagerAdapter);
	}

	public class myPagerAdapter extends FragmentStatePagerAdapter {
		private int count = 8;
		myParseModule parser;

		public myPagerAdapter(FragmentManager fm, myParseModule parser) {
			super(fm);
			this.parser = parser;
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = new myPassageListFragment();
			Bundle args = new Bundle();
			args.putStringArrayList(myPassageListFragment.ARG_CONTENT,
					parser.getTitle(myParseModule.INFO[i]));
			args.putString(myPassageListFragment.ARG_TYPE,myParseModule.INFO[i]);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return count;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return myParseModule.TITLE[position];
		}
	}
}
