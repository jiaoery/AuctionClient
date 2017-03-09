package org.crazyit.auction.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import org.crazyit.BaseFragmentActivity;
import org.crazyit.constant.CONSTANT;

public class ChooseKind extends BaseFragmentActivity
	implements Callbacks
{
	@Override
	public Fragment getFragment()
	{
		return new ChooseKindFragment();
	}
	@Override
	public void onItemSelected(Integer id, Bundle bundle)
	{
		Intent intent = new Intent(this , ChooseItem.class);
		intent.putExtra("kindName", bundle.getString("kindName"));
		intent.putExtra(CONSTANT.COMMON_TITLE,bundle.getString("kindName"));
		startActivity(intent);
	}
}
