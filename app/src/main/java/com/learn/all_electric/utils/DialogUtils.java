package com.learn.all_electric.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.learn.all_electric.R;


public class DialogUtils {
    static DialogUtils dialogUtils;

    public static DialogUtils init() {
        if (dialogUtils == null) {
            dialogUtils = new DialogUtils();
        }
        return dialogUtils;
    }

    public void setDialogUtils(Context mContext, String title, String canle, String sub, View.OnClickListener listener) {
        Dialog dialog = new Dialog(mContext, R.style.Theme_Light_LoadingDialog);
        View view = View.inflate(mContext, R.layout.dialog_comfir, null);
        dialog.setContentView(view);
        TextView dititle = view.findViewById(R.id.dititle);
        TextView dicanlce = view.findViewById(R.id.dicanlce);
        TextView disub = view.findViewById(R.id.disub);
        View dialgview = view.findViewById(R.id.dialgview);
        dititle.setText(Html.fromHtml(title));
        if (!TextUtils.isEmpty(canle)) {
            dicanlce.setText(canle);
        }else {
            dicanlce.setVisibility(View.GONE);
            dialgview.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(sub)) {
            disub.setText(sub);
        }else{
            disub.setVisibility(View.GONE);
            dialgview.setVisibility(View.GONE);
        }
        disub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog.cancel();
                if (listener != null) {
                    listener.onClick(view);
                }
            }
        });
        dicanlce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog.cancel();
            }
        });
        dialog.setCancelable(false);//不可取消
        dialog.show();
    }

}
