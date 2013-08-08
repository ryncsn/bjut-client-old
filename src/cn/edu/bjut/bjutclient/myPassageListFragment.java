package cn.edu.bjut.bjutclient;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class myPassageListFragment extends ListFragment {
	public interface PassageSelectedListener {
		void JumpToPassage(int number, String type);
	}

	String type;
	View rootView;
	PassageSelectedListener mCallback;
	public static final String ARG_CONTENT = "titles";
	public static final String ARG_TYPE = "type";

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mCallback = (PassageSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement PassageSelectedListener");
		}
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.my_list_frag, container, false);
		return rootView;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		type = args.getString(ARG_TYPE);
		List<String> passageTitles = args.getStringArrayList(ARG_CONTENT);
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, passageTitles));
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		mCallback.JumpToPassage(position, type);
	}
}
