package com.mirko.androidutil.view;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

    public static Toast mToast = null;
    private static String oldMsg;
    private static long time;

    public static void init() {
    }

    public static void alert(final Context context, final String message) {
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (!message.equals(oldMsg)) { // 当显示的内容不一样时，即断定为不是同一个Toast
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        time = System.currentTimeMillis();
                    } else {
                        // 显示内容一样时，只有间隔时间大于2秒时才显示
                        if (System.currentTimeMillis() - time > 2000) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            time = System.currentTimeMillis();
                        }
                    }
                    oldMsg = message;
                }
            });
        }
//		alert(context,message,2000);
    }

//	public static void alert(Context context,String message,int duration) {
//		Message msg = new Message();
//		BundleObject data = new BundleObject();
//		data.put("context", context);
//		data.put("message", message);
//		msg.obj = data;
//		showAlertDialogHandler.sendMessage(msg);
//	}
//
//	private static Handler showAlertDialogHandler = new Handler() {
//		public void handleMessage(Message msg) {
//			final BundleObject data    = (BundleObject) msg.obj;
//			String message = data.getString("message");
//			if(message == null) {
//				message = "";
//			}
//			Toast toast   = Toast.makeText((Context)data.get("context"), message, Toast.LENGTH_SHORT);
//			toast.show();
//		};
//	};
}
