package org.crazyit.auction.client;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class AddBid extends FragmentActivity
{
	public Fragment getFragment()
	{
		AddBidFragment fragment = new AddBidFragment();
		Bundle args = new Bundle();
		args.putInt("itemId", getIntent()
			.getIntExtra("itemId", -1));
		fragment.setArguments(args);
		return fragment;
	}
}
