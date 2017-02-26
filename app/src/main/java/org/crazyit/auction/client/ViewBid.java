package org.crazyit.auction.client;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import org.crazyit.BaseFragmentActivity;

public class ViewBid extends BaseFragmentActivity
{
	@Override
	protected Fragment getFragment()
	{
		return new ViewBidFragment();
	}
}
