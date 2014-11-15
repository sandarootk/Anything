package com.example.anything;

import com.example.anything.ItemViewActivity.HelloWebViewClient;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Build;

public class MessageViewActivity extends ActionBarActivity {

	private String userId;
	private String itemId;
	private String noOfMsgs;
	private int msgs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_view);

		Bundle b = getIntent().getExtras();
		userId = b.getString("key");
		itemId = b.getString("itemId");
		noOfMsgs = b.getString("msg");
		Log.d("item view", itemId);

		try {
			msgs = Integer.parseInt(noOfMsgs);
		} catch (NumberFormatException e) {

		}

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		String url = "http://192.168.172.1:80/shop_webservice/Messageview.php?userlog="
				+ userId + "&&item=" + itemId;
		Log.d("url", url);
		WebView view = (WebView) this.findViewById(R.id.webView1);

		view.getSettings().setJavaScriptEnabled(true);
		view.loadUrl(url);
		view.setWebViewClient(new HelloWebViewClient());
	}

	public class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView webView1, String url) {
			if (url.contains("backToTheItem")) {
				Log.d("url", url);

				Intent myIntent = new Intent(MessageViewActivity.this,
						MessageViewActivity.class);
				Bundle b = new Bundle();
				b.putString("key", userId);
				b.putString("itemId", itemId);
				b.putString("msg", noOfMsgs);
				myIntent.putExtras(b);
				startActivity(myIntent);

				return false;
			} else if (url.contains("backToTheList")) {
				Log.d("url", url);
				Intent myIntent = new Intent(MessageViewActivity.this,
						MessagesActivity.class);
				Bundle b = new Bundle();
				b.putString("key", userId);
				b.putString("msg", noOfMsgs);
				myIntent.putExtras(b);
				startActivity(myIntent);

				return false;
			} else {
				Log.d("url", url);
				webView1.loadUrl(url);
				return true;
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);

		setTitle("");
		
		if (msgs > 0) {
			menu.findItem(R.id.messages)
					.setIcon(R.drawable.ic_action_new_email);
		} else {
			menu.findItem(R.id.messages).setIcon(R.drawable.ic_action_email);
		}

		return true;
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
		} else if (id == R.id.addnewad) {
			addNewItem();
		} else if (id == R.id.logout) {
			logOut();
		} else if (id == android.R.id.home) {
			goToMain();
		} else if (id == R.id.account) {
			accountView();
		}
		return super.onOptionsItemSelected(item);
	}

	private void accountView() {
		Intent myIntent = new Intent(MessageViewActivity.this,
				ProfileViewActivity.class);
		Bundle b = new Bundle();
		b.putString("key", userId);
		b.putString("msg", noOfMsgs);
		b.putString("who", userId);
		myIntent.putExtras(b);
		startActivity(myIntent);
	}

	private void addNewItem() {
		Intent myIntent = new Intent(MessageViewActivity.this,
				ItemAddActivity.class);
		Bundle b = new Bundle();
		b.putString("key", userId);
		b.putString("msg", noOfMsgs);
		myIntent.putExtras(b);
		startActivity(myIntent);
	}

	private void goToMain() {
		Intent myIntent = new Intent(MessageViewActivity.this,
				MainPageActivity.class);
		Bundle b = new Bundle();
		b.putString("key", userId);
		b.putString("msg", noOfMsgs);
		myIntent.putExtras(b);
		startActivity(myIntent);

	}

	private void logOut() {
		Intent myIntent = new Intent(MessageViewActivity.this,
				LoginActivity.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(myIntent);
		finish();
	}

	private void openMessages() {
		Intent myIntent = new Intent(MessageViewActivity.this,
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

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_message_view,
					container, false);
			return rootView;
		}
	}

}
