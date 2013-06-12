package com.devT.bootcamp;

import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/* Program: Android Mobile Application Bootcamp
 * Author: Tom Zhang (tomzhang94@gmail.com)
 * Submitted: U of T Developers
 * 
 * Description: Basic twitter app which retrieves a JSON using Async tasks and HTTPClient.
 * 				Then displays tweets (author, date and icon) into list view. Users may change
 * 				search results and refresh results using the menu. However, no animation has been 
 * 				implemented. 
 * 																								*/

public class MainActivity extends ListActivity {

	private FeedManager mgr;
	private String query = "mobiledev";//search index
	private static final int REQUEST_CODE = 10;//arbituary request code

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mgr = new FeedManager(getApplicationContext());
		loadData();//calls async task

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//menu item control
		switch (item.getItemId()) {
		case R.id.refresh:
			refresh();//refresh results
			return true;
		case R.id.search:
			search();//search for new results
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void refresh() {
		loadData();//reloads data
	}

	private void search() {
		//sends intent to open new activity for new search query
		Intent intent = new Intent (this, tweetQuery.class);
		startActivityForResult(intent, REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			if (data.hasExtra(tweetQuery.TWEET_QUERY)) {
				
				//makes the string valid
				query = data.getStringExtra(tweetQuery.TWEET_QUERY);
				query = query.substring(query.indexOf('#')+1);
				query = query.trim();
				
				//sets the title to current search query
				TextView Title = (TextView) this.findViewById(R.id.Title);
				Title.setText("#"+ query);
				
				//reloads data
				loadData();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void loadData(){
		AsyncTask <String, Void, List<Tweet>> asyncTask = new AsyncTask<String, Void, List<Tweet>>(){
			private ProgressDialog progressDialog = new ProgressDialog (MainActivity.this);
			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				//show loading screen
				progressDialog.setTitle("Loading Tweets");
				if (!progressDialog.isShowing())
				{
					progressDialog.show();
				}
			}

			@Override
			protected List<Tweet> doInBackground(String... params) {
				//execute back end
				return mgr.getSocialFeed(query);
			}

			protected void onPostExecute(List<Tweet> result) {
				super.onPostExecute(result);
				//close loading screen
				if (progressDialog.isShowing()){
					progressDialog.hide();
				}
				
				if (result.size() <= 0) //if query is unsuccessful
				{
					setListAdapter(null);
					TextView empty = (TextView) findViewById(android.R.id.empty);
					empty.setText("No Search Results");
					
				}
				else//if query was successful
				{
					TweetAdapter listAdapter = new TweetAdapter (getApplicationContext(), result);
					setListAdapter (listAdapter);
				}
			}
		};
		asyncTask.execute();//executes async task
	}
}
