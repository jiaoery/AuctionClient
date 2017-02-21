package org.crazyit.auction.client;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class AddItem extends FragmentActivity
{
	public Fragment getFragment()
	{
		return new AddItemFragment();
	}
}