package com.learn.all_electric.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.learn.all_electric.LoginActivity;
import com.learn.all_electric.UploadScoreService;
import com.learn.all_electric.constants.Constant;
import com.learn.all_electric.constants.SharedConstants;
import com.learn.all_electric.myinterface.OnNetWorkChangedListener;
import com.learn.all_electric.myinterface.OnReceiveScoreListener;
import com.learn.all_electric.utils.InternetUtils;
import com.learn.all_electric.utils.LogUtil;
import com.learn.all_electric.utils.PreferenceUtil;
import com.learn.all_electric.utils.RequestManager;
import com.learn.all_electric.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UploadScoreReceiver extends BroadcastReceiver {
    private List<OnReceiveScoreListener> onReceiveScoreListeners = new ArrayList<OnReceiveScoreListener>();

    public void registerReceiver(OnReceiveScoreListener listener) {
        if (!onReceiveScoreListeners.contains(listener)) {
            onReceiveScoreListeners.add(listener);
        }
    }

    public void unregisterReceiver(OnReceiveScoreListener listener) {
        if (onReceiveScoreListeners.contains(listener)) {
            onReceiveScoreListeners.remove(listener);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Constant.BROACAST_UPLOAD_SCORE.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String fileName = bundle.getString("score_file_name");
                Toast.makeText(context, "成功接收上传分数的广播：" + fileName, Toast.LENGTH_LONG).show();
                //起个线程进行文件的读写

                if (onReceiveScoreListeners != null && onReceiveScoreListeners.size() > 0) {
                    for (OnReceiveScoreListener listener : onReceiveScoreListeners) {
                        listener.onReceiveFileName(fileName);
                    }
                }

            }
        }

    }


}
