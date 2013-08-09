package cn.edu.bjut.bjutclient;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class jwUI extends FragmentActivity {
	public final static String MY_PASSAGE_ADD = "cn.edu.bjut.client.passageadd";
	jwPagerAdapter currentMyPagerAdapter;
	ViewPager jwViewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_ui);
		jwViewPager = (ViewPager) findViewById(R.id.my_ui_pager);
		Intent intent = getIntent();
		jwParseModule parser = new jwParseModule(
				intent.getStringExtra(MainActivity.TRANSIT_JW_PAGE));
		currentMyPagerAdapter = new jwPagerAdapter(getSupportFragmentManager(),
				parser.getTable());
		jwViewPager.setAdapter(currentMyPagerAdapter);

	}

	public class jwPagerAdapter extends FragmentStatePagerAdapter {
		private String[] weeks = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六",
				"星期日" };
		private int count = 7;
		ArrayList<ArrayList<String>> courses;

		public jwPagerAdapter(FragmentManager fm,
				ArrayList<ArrayList<String>> courses) {
			super(fm);
			this.courses = courses;
		}

		@Override
		public Fragment getItem(int i) {
			Bundle args = new Bundle();
			Fragment fragment = new jwFragment();
			args.putStringArrayList(jwFragment.ARG_CONTENT, courses.get(i));
			fragment.setArguments(args);
			return fragment;
		}

		public int getCount() {
			return count;
		}

		public CharSequence getPageTitle(int position) {
			return weeks[position];
		}
	}
}