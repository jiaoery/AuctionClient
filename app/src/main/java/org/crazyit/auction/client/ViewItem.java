package org.crazyit.auction.client;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import org.crazyit.BaseFragmentActivity;

public class ViewItem extends BaseFragmentActivity
{
	// 重写getFragment()方法，该Activity显示该方法返回的Fragment
	@Override
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
