package com.devT.bootcamp;

import java.net.URL;

import android.graphics.Bitmap;

public class Tweet {
	public URL ImageURL;
	private Bitmap pic = null;
	private String username = "default username";
	private String tweet = "default tweet";
	private String date = "default date";
	
	public Tweet (Bitmap icon, String Text, String Author, String Date) 
	{
		setPic (icon);
		setUsername(Author);
		tweet = Text;		
		setDate(Date);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getTweet() {
		return tweet;
	}

	public void setTweet(String text) {
		this.tweet = text;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the pic
	 */
	public Bitmap getPic() {
		return pic;
	}

	/**
	 * @param pic the pic to set
	 */
	public void setPic(Bitmap pic) {
		this.pic = pic;
	}

}
