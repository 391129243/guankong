package com.learn.all_electric.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.learn.all_electric.LoginActivity;
import com.learn.all_electric.base.BaseActivity;
import com.learn.all_electric.constants.Constant;
import com.learn.all_electric.myinterface.OnNetWorkChangedListener;

import java.util.ArrayList;
import java.util.List;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private List<OnNetWorkChangedListener> mNetworkChangedListeners = new ArrayList<OnNetWorkChangedListener>();

    public void registerReceiver(OnNetWorkChangedListener listener) {
        if (!mNetworkChangedListeners.contains(listener)) {
            mNetworkChangedListeners.add(listener);
        }
    }

    public void unregisterReceiver(OnNetWorkChangedListener listener) {
        if (mNetworkChangedListeners.contains(listener)) {
            mNetworkChangedListeners.remove(listener);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Constant.NETWORK_CONNECTION_CHANGE)) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (null != mNetworkInfo && mNetworkInfo.isAvailable()) {
                if (mNetworkChangedListeners != null && mNetworkChangedListeners.size() > 0) {
                    for (OnNetWorkChangedListener listener : mNetworkChangedListeners) {
                        listener.onNetworkChange(mNetworkInfo.isConnected(), mNetworkInfo.getType());
                    }
                }
            } else {

                if (mNetworkChangedListeners != null && mNetworkChangedListeners.size() > 0) {
                    for (OnNetWorkChangedListener listener : mNetworkChangedListeners) {
                        listener.onNetworkChange(false, 0);
                    }
                }
            }
        }
    }




}
