package org.crazyit.auction.client;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import org.crazyit.BaseFragmentActivity;

public class AddBid extends BaseFragmentActivity
{
	@Override
	public Fragment getFragment()
	{
		AddBidFragment fragment = new AddBidFragment();
		Bundle args = new Bundle();
		args.putString("itemId", getIntent()
			.getStringExtra("itemId"));
		fragment.setArguments(args);
		return fragment;
	}
}
