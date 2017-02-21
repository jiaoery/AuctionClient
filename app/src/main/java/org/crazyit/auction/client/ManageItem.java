package org.crazyit.auction.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class ManageItem extends FragmentActivity
		implements Callbacks
{
	public Fragment getFragment()
	{
		return new ManageItemFragment();
	}
	@Override
	public void onItemSelected(Integer id, Bundle bundle)
	{
		// 当用户单击添加按钮时，将会启动AddItem Activity
		Intent i = new Intent(this , AddItem.class);
		startActivity(i);
	}
}