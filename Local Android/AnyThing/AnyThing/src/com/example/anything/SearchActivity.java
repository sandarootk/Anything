package com.example.anything;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.SearchView;
import android.widget.TextView;

public class SearchActivity extends Activity implements
		SearchView.OnQueryTextListener {

	private SearchView mSearchView;
	private TextView mStatusView;
	private static String userId;
	private static String noOfMsgs;
	private static String searchKey = "";
	private static String cat = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

		Bundle b = getIntent().getExtras();
		userId = b.getString("key");
		noOfMsgs = b.getString("msg");
		searchKey = b.getString("searchKey");

		setContentView(R.layout.activity_main);

		String url = "http://192.168.172.1:80/shop_webservice/MainPage.php?userlog="
				+ userId + "&&page=1&&cat=" + cat + "&&search=" + searchKey;
		Log.d("url", url);
		WebView view = (WebView) this.findViewById(R.id.webView1);

		view.getSettings().setJavaScriptEnabled(true);
		view.loadUrl(url);
		view.setWebViewClient(new HelloWebViewClient());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		mSearchView = (SearchView) searchItem.getActionView();
		setupSearchView(searchItem);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == R.id.logout) {
			logOut();
		} else if (id == R.id.addnewad) {
			addNewItem();
		} else if (id == R.id.account) {
			accountView();
		}
		return super.onOptionsItemSelected(item);
	}

	private void accountView() {
		Intent myIntent = new Intent(SearchActivity.this,
				ProfileViewActivity.class);
		Bundle b = new Bundle();
		b.putString("key", userId);
		b.putString("msg", noOfMsgs);
		b.putString("who", userId);
		myIntent.putExtras(b);
		startActivity(myIntent);
	}

	private void addNewItem() {
		Intent myIntent = new Intent(SearchActivity.this, ItemAddActivity.class);
		Bundle b = new Bundle();
		b.putString("key", userId);
		b.putString("msg", noOfMsgs);
		myIntent.putExtras(b);
		startActivity(myIntent);
	}

	private void logOut() {
		Intent myIntent = new Intent(SearchActivity.this, LoginActivity.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(myIntent);
		finish();
	}

	private void setupSearchView(MenuItem searchItem) {

		if (isAlwaysExpanded()) {
			mSearchView.setIconifiedByDefault(false);
			mSearchView.requestFocus();
		} else {
			searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
					| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		}

		mSearchView.setOnQueryTextListener(this);
	}

	public boolean onQueryTextChange(String newText) {
		searchKey = newText;
		return false;
	}

	public boolean onQueryTextSubmit(String query) {
		searchKey = query;

		Intent myIntent = new Intent(SearchActivity.this, SearchActivity.class);
		Bundle b = new Bundle();
		b.putString("key", userId);
		b.putString("msg", noOfMsgs);
		b.putString("searchKey", searchKey);
		b.putString("cat", cat);
		myIntent.putExtras(b);
		startActivity(myIntent);

		return true;
	}

	public boolean onClose() {
		return false;
	}

	protected boolean isAlwaysExpanded() {
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Intent myIntent = new Intent(SearchActivity.this,
					MainPageActivity.class);
			Bundle b = new Bundle();
			b.putString("key", userId);
			b.putString("msg", noOfMsgs);
			myIntent.putExtras(b);
			startActivity(myIntent);

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView webView1, String url) {

			if (url.contains("Itemview")) {
				String itemId = url.substring(url.lastIndexOf("@") + 1);

				Intent intent = new Intent(SearchActivity.this,
						ItemViewActivity.class);
				Bundle b = new Bundle();
				b.putString("key", userId);
				b.putString("itemId", itemId);
				b.putString("msg", noOfMsgs);
				intent.putExtras(b);
				SearchActivity.this.startActivity(intent);
				Log.d("item id", itemId);
				return false;
			} else {
				webView1.loadUrl(url);
				return true;
			}
		}
	}

}
