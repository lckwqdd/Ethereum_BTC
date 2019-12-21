package com.mirko.androidutil.view.loading;


import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.mirko.androidutil.R;


public class ProgressLoading {
	private Context mContext;
	private Dialog progressDialog;

	public ProgressLoading(Context context) {
		mContext = context;
	}


	/**
	 * 进度加载，文字自定义
	 *
	 * @param dialogMsg
	 * @return
	 */
	public Dialog loadDialog(String dialogMsg){
		progressDialog = new Dialog(mContext, R.style.progress_dialog);
		progressDialog.setContentView(R.layout.progress_loading);
		progressDialog.setCancelable(true);
		progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
		msg.setText(dialogMsg);
		progressDialog.setCanceledOnTouchOutside(true);
		progressDialog.show();
		return progressDialog;
	}

	/**
	 * 加载默认文本
	 * @return
	 */
	public Dialog loadDialog() {
		progressDialog = new Dialog(mContext, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.progress_loading);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("玩命加载中...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
        return progressDialog;
	}

	public void removeDialog() {
		if (progressDialog != null && progressDialog.isShowing()){
			progressDialog.dismiss();
		}
	}
}
    