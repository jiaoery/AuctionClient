package org.crazyit.auction.client;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import org.crazyit.BaseFragmentActivity;

public class AddItem extends BaseFragmentActivity
{
	@Override
	public Fragment getFragment()
	{
		return new AddItemFragment();
	}
}