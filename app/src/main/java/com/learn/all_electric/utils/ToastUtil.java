package com.learn.all_electric.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.learn.all_electric.R;

public class ToastUtil {
    private static Toast mToast = null;
    private static String oldMsg;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void showToast(Context context, String message){

        if(mToast == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.module_layout_toast, null);
            TextView text = (TextView)view.findViewById(R.id.toast_txt);
            text.setText(message); //toast内容
            mToast = new Toast(context);
            mToast.setDuration(Toast.LENGTH_LONG);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setView(view);
            mToast.show();
            oneTime = System.currentTimeMillis();
        }else{
            twoTime = System.currentTimeMillis();
            if(message.equals(oldMsg)){
                if(twoTime - oneTime > Toast.LENGTH_SHORT){
                    mToast.show();
                }
            }else{
                oldMsg = message;
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.module_layout_toast, null);
                TextView text = (TextView)view.findViewById(R.id.toast_txt);
                text.setText(message); //toast内容
                mToast.setView(view);
                mToast.show();
            }
        }
        oneTime = twoTime;
    }

    public static void cancelToast(){
        if(null != mToast){
            mToast.cancel();
            mToast = null;
        }
    }
}
