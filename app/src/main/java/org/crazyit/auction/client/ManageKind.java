package org.crazyit.auction.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class ManageKind extends FragmentActivity
		implements Callbacks
{
	public Fragment getFragment()
	{
		return new ManageKindFragment();
	}

	@Override
	public void onItemSelected(Integer id, Bundle bundle)
	{
		// 当用户单击ManageKindFragment中添加按钮时，启动AddKind Activity
		Intent i = new Intent(this , AddKind.class);
		startActivity(i);
	}
}
