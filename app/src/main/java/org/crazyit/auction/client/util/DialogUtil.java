package org.crazyit.auction.client.util;

import org.crazyit.auction.client.AuctionClientActivity;
import org.crazyit.auction.client.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

public class DialogUtil {

	private static AlertDialog loadingDialog;
	private static AlertDialog retryDialog;

	// 定义一个显示消息的对话框
	public static void showDialog(final Context ctx
			, String msg, boolean goHome) {
		// 创建一个AlertDialog.Builder对象
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx)
				.setMessage(msg).setCancelable(false);
		if (goHome) {
			builder.setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent i = new Intent(ctx, AuctionClientActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					ctx.startActivity(i);
				}
			});
		} else {
			builder.setPositiveButton("确定", null);
		}
		builder.create().show();
	}

	// 定义一个显示指定组件的对话框
	public static void showDialog(Context ctx, View view) {
		new AlertDialog.Builder(ctx)
				.setView(view).setCancelable(false)
				.setPositiveButton("确定", null)
				.create()
				.show();
	}

	/**
	 * 显示/隐藏“正在加载”对话框
	 * 正在加载对话框 与 swipeRefreshLayout是矛盾的，
	 * 所以autoProgress为true就显示正在加载对话框
	 * 为false就判断Activity是不是SwipeRefreshActivity，再决定是否显示
	 *
	 * @param activity     Activity
	 * @param show         显示true、隐藏false
	 */
	public static void showProgress(final Activity activity,boolean show) {
		if (activity == null) {
			return;
		}
			if (show) {//显示
				showProgress(activity);
			} else {//隐藏
				dismissProgress(activity);
			}
	}

	/**
	 * 显示加载过程dialog
	 * @param activity
     */
	private static void showProgress(Activity activity) {
		if(loadingDialog==null&&activity!=null){
			loadingDialog= ProgressDialog.show(activity, activity.getString(R.string.notification), activity.getString(R.string.logining));
			loadingDialog.show();
		}


	}

	/**
	 * 过程dialog消失
	 */
	private static void dismissProgress(Activity activity){
		if(loadingDialog!=null&&activity!=null){
			loadingDialog.dismiss();
		}
	}

}

