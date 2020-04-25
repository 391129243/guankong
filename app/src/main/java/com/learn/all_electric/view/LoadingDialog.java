package com.learn.all_electric.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.learn.all_electric.R;
import com.learn.all_electric.myinterface.SpeedProgressListener;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 加载对话框
 */

public class LoadingDialog extends ProgressDialog {

    private String mMessage;

    private TextView mTitleTv;
    private ProgressBar progressBar;

    public LoadingDialog(Context context, String message, boolean canceledOnTouchOutside) {
        super(context, R.style.Theme_Light_LoadingDialog);
        this.mMessage = message;
        // 如果触摸屏幕其它区域,可以选择让这个progressDialog消失或者无变化
        setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_save);
        mTitleTv = (TextView) findViewById(R.id.tv_loading_dialog);
        mTitleTv.setText(Html.fromHtml(mMessage));
        setCancelable(false);//不可取消
        progressBar = findViewById(R.id.pb_loading_dialog);
    }

    int delay = 0;
    long time = 2000;

    public void setProgressBar() {
        if (progressBar == null) {
            return;
        }
        delay = 0;
        progressBar.setMax((int) time);
        progressBar.setProgress(0);
        Timer timer = new Timer();
        Handler handler = new Handler();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                delay += 10;
                                progressBar.setProgress(delay);
                                if (listener != null) {
                                    listener.onProgress(delay);
                                }
                                if (delay >= time) {
                                    dismiss();
                                    cancel();
                                }
                            }
                        });

                    }
                });
            }
        }, 10, 10);
    }

    public void setTitle(String message) {
        this.mMessage = message;
        mTitleTv.setText(Html.fromHtml(mMessage));
    }

    /**
     * @param time 时间:单位毫秒
     */
    public void setTime(long time) {
        this.time = time;
    }

    private Handler handler = new Handler();

    private SpeedProgressListener listener;

    public void setOnProgressListener(SpeedProgressListener listener) {
        this.listener = listener;
    }

}
