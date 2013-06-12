package com.devT.bootcamp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class tweetQuery extends Activity {
	
	public final static String TWEET_QUERY = "com.devT.bootcamp.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tweet_query);
	}
	
    public void Search(View view)
    {
    	finish();//call finish function when button is pressed
    }
    
    @Override
    public void finish() {
    	//creates intent to give desired query to home activity
    	Intent data = new Intent ();
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	data.putExtra(TWEET_QUERY, message);
    	
    	setResult(RESULT_OK, data);//says everything is ok
    	
    	super.finish();
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
