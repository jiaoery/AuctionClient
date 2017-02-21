package org.crazyit.auction.client;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class ViewItem extends FragmentActivity
{
	// 重写getFragment()方法，该Activity显示该方法返回的Fragment
	protected Fragment getFragment()
	{
		ViewItemFragment fragment = new ViewItemFragment();
		Bundle arguments = new Bundle();
		arguments.putString("action"
				, getIntent().getStringExtra("action"));
		fragment.setArguments(arguments);
		return fragment;
	}
}
