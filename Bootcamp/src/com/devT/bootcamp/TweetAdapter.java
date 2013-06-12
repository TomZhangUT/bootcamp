package com.devT.bootcamp;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetAdapter extends BaseAdapter{

	//global variables
	private List<Tweet> socialFeed;
	private Context context;

	//new constructor for adapter
	public TweetAdapter(final Context context, List<Tweet> socialFeed)
	{
		super();
		this.socialFeed = socialFeed;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		//gets current view
		
		//fills up view if nothing is there
		if (row == null)
		{
			final LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = vi.inflate (R.layout.tweet, null);
		}

		Tweet tweet = (Tweet) getItem(position);//finds appropriate tweet

		ImageView Icon = (ImageView) row.findViewById(R.id.Icon);
		if (tweet.getPic()==null)
		{
			Icon.setImageResource(R.drawable.ok);//if there is no image, put default image
		}
		else
		{
			Icon.setImageBitmap(tweet.getPic());//else bind icon to view
		}

		//binds username with appropriate text view
		final TextView profileName = (TextView) row.findViewById(R.id.User);
		profileName.setText(tweet.getUsername());

		//binds tweet with appropriate text view
		final TextView tweetMessage = (TextView)row.findViewById(R.id.listDescription);
		tweetMessage.setText(tweet.getTweet());

		//binds date with appropriate text view
		final TextView date = (TextView)row.findViewById(R.id.Date);
		date.setText(tweet.getDate());

		return row;
	}

	@Override
	public int getCount() {

		return socialFeed.size();//get the total size
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return socialFeed.get(position);//gets the item at a given index
	}

	@Override
	public long getItemId(int position) {
		return position;//returns the position
	}

}
