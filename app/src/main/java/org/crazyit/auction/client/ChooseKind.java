package org.crazyit.auction.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class ChooseKind extends FragmentActivity
	implements Callbacks
{
	public Fragment getFragment()
	{
		return new ChooseKindFragment();
	}
	@Override
	public void onItemSelected(Integer id, Bundle bundle)
	{
		Intent intent = new Intent(this , ChooseItem.class);
		intent.putExtra("kindId", bundle.getLong("kindId"));
		startActivity(intent);
	}
}
