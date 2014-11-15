package com.example.anything;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainPageActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	private static String userId;
	private static String noOfMsgs;
	private int msgs;

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);

		Bundle b = getIntent().getExtras();
		userId = b.getString("key");
		noOfMsgs = b.getString("msg");

		try {
			msgs = Integer.parseInt(noOfMsgs);
		} catch (NumberFormatException e) {

		}

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		case 4:
			mTitle = getString(R.string.title_section4);
			break;
		case 5:
			mTitle = getString(R.string.title_section5);
			break;
		case 6:
			mTitle = getString(R.string.title_section6);
			break;
		case 7:
			mTitle = getString(R.string.title_section7);
			break;

		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.item_view, menu);

			restoreActionBar();
			if (msgs > 0) {
				menu.findItem(R.id.messages).setIcon(
						R.drawable.ic_action_new_email);
			} else {
				menu.findItem(R.id.messages)
						.setIcon(R.drawable.ic_action_email);
			}
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.messages) {
			openMessages();
		} else if (id == R.id.logout) {
			logOut();
		} else if (id == R.id.addnewad) {
			addNewItem();
		} else if (id == R.id.search) {
			searchView();
		} else if (id == R.id.account) {
			accountView();
		}
		return super.onOptionsItemSelected(item);
	}

	private void accountView() {
		Intent myIntent = new Intent(MainPageActivity.this,
				ProfileViewActivity.class);
		Bundle b = new Bundle();
		b.putString("key", userId);
		b.putString("msg", noOfMsgs);
		b.putString("who", userId);
		myIntent.putExtras(b);
		startActivity(myIntent);
	}

	private void addNewItem() {
		Intent myIntent = new Intent(MainPageActivity.this,
				ItemAddActivity.class);
		Bundle b = new Bundle();
		b.putString("key", userId);
		b.putString("msg", noOfMsgs);
		myIntent.putExtras(b);
		startActivity(myIntent);
	}

	private void searchView() {
		Intent myIntent = new Intent(MainPageActivity.this,
				SearchActivity.class);
		Bundle b = new Bundle();
		b.putString("key", userId);
		b.putString("msg", noOfMsgs);
		myIntent.putExtras(b);
		startActivity(myIntent);
	}

	private void logOut() {
		Intent myIntent = new Intent(MainPageActivity.this, LogOutActivity.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(myIntent);
		finish();
	}

	private void openMessages() {
		Intent myIntent = new Intent(MainPageActivity.this,
				MessagesActivity.class);
		Bundle b = new Bundle();
		b.putString("key", userId);
		b.putString("msg", noOfMsgs);
		myIntent.putExtras(b);
		startActivity(myIntent);

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_page,
					container, false);
			WebView view = (WebView) rootView.findViewById(R.id.webView1);
			String cat = (Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));

			String url = "http://192.168.172.1:80/shop_webservice/MainPage.php?userlog="
					+ userId + "&&page=1&&cat=" + cat + "&&search=null";

			Log.d("url", url);
			view.getSettings().setJavaScriptEnabled(true);
			view.loadUrl(url);
			view.setWebViewClient(new HelloWebViewClient());

			return rootView;
		}

		public class HelloWebViewClient extends WebViewClient {
			@Override
			public boolean shouldOverrideUrlLoading(WebView webView1, String url) {

				if (url.contains("Itemview")) {
					String itemId = url.substring(url.lastIndexOf("@") + 1);

					Intent intent = new Intent(getActivity(),
							ItemViewActivity.class);
					Bundle b = new Bundle();
					b.putString("key", userId);
					b.putString("itemId", itemId);
					b.putString("msg", noOfMsgs);
					intent.putExtras(b);
					getActivity().startActivity(intent);
					Log.d("item id", itemId);
					return false;
				} else {
					webView1.loadUrl(url);
					return true;
				}
			}
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainPageActivity) activity).onSectionAttached(getArguments()
					.getInt(ARG_SECTION_NUMBER));
		}

	}

}
