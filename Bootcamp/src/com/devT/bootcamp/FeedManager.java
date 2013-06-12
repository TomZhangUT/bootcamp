package com.devT.bootcamp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class FeedManager {

	private Context context = null;

	//Constructor
	public FeedManager (final Context context){
		this.context = context;
	}

	public List<Tweet> getSocialFeed (String query){

		//creates an arraylist of tweets
		List<Tweet> tweets = new ArrayList <Tweet>();

		String TAG = "Async"; //tag for debugging purposes

		//HTTP Client
		InputStream in = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet("http://search.twitter.com/search.json?q=.%23"+ query + "&src=typd");
			HttpResponse response = client.execute(request);
			in = response.getEntity().getContent();
			Log.d(TAG, "HttpClient Success");
		}
		catch (Exception e){
			Log.d(TAG, "HttpClient Failed");
		}

		//Parsing JSON into string
		String json = "";
		try{
			StringBuilder sb = new StringBuilder();
			String line = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			in.close();
			json = sb.toString();

			Log.d(TAG, "Buffer reader to JSON success");
		}
		catch (Exception e){
			Log.d(TAG, "Buffer reader to JSON failed");
		}

		//Creates JSONObject
		JSONObject jObj = null;
		try {
			jObj = new JSONObject(json);
			Log.d(TAG, "Creation of JSON success");
		} catch (JSONException e) {
			Log.d(TAG, "Creation of JSONObject Failed");
		}

		//Creates JSONArray
		JSONArray mTweets = null;
		// Getting Array of Tweets
		try {
			mTweets = jObj.getJSONArray("results");
		} catch (Exception e1) {
			Log.d(TAG, "Could not convert into JSONArray");
		}

		//set default values for variables
		String date = "default";
		String name ="default";
		String icon="default";
		String message="default";

		if (mTweets != null)//if conversion was successful
		{
			// looping through All entries
			for(int i = 0; i < mTweets.length(); i++){
				JSONObject c = null;

				try {
					//convert data into suitable strings
					c = mTweets.getJSONObject(i);
					
					date = c.getString("created_at");
					date = date.replace("+0000", "");
					
					name = c.getString("from_user_name");
					icon = c.getString("profile_image_url");
					message = c.getString("text");
				} catch (JSONException e) {
					Log.d(TAG, "Retrieving JSONObject Failed");
				}

				//use image URL to produce bitmap for Icon
				Bitmap bm = null;
				try {
					URL aURL = new URL(icon);
					final URLConnection conn = aURL.openConnection();
					conn.connect();
					BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
					bm = BitmapFactory.decodeStream(bis);
					bis.close();
				} catch (Exception e) {
					Log.d("ImageDownloader","Image download failed");
				}

				//create tweet object from data
				Tweet tweet = new Tweet (bm, message, name, date);
				tweets.add(tweet);//add tweet object to array list
			}
		}
		
		return tweets;
	}
}
