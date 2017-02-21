package org.crazyit.auction.client;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class ViewBid extends FragmentActivity
{
	protected Fragment getFragment()
	{
		return new ViewBidFragment();
	}
}
