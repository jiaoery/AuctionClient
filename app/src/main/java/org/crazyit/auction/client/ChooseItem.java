package org.crazyit.auction.client;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import org.crazyit.BaseFragmentActivity;

public class ChooseItem extends BaseFragmentActivity
	implements Callbacks
{
	@Override
	public Fragment getFragment()
	{
		ChooseItemFragment fragment = new ChooseItemFragment();
		Bundle args = new Bundle();
		args.putString("kindName", getIntent()
			.getStringExtra("kindName"));
		fragment.setArguments(args);
		return fragment;
	}
	@Override
	public void onItemSelected(Integer id, Bundle bundle)
	{
		Intent intent = new Intent(this , AddBid.class);
		intent.putExtra("itemId", bundle.getString("itemId"));
		startActivity(intent);
	}
}
