package com.learn.all_electric.control;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.learn.all_electric.base.BaseApplication;
import com.learn.all_electric.R;
import com.learn.all_electric.view.CountDownProgressBar;

public class PrepareLayout extends LinearLayout {

    private IOnButtonClickListener clickListener;

    public CountDownProgressBar getCpb_countdown() {
        return cpb_countdown;
    }

    private CountDownProgressBar cpb_countdown;

    public PrepareLayout(@NonNull Context context) {
        super(context);
        initView();
    }

    public PrepareLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PrepareLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_instructions, this, true);

        cpb_countdown = (CountDownProgressBar) rootView.findViewById(R.id.cpb_countdown);

        TextView btn_confirm = (TextView) rootView.findViewById(R.id.btn_confirm);

        btn_confirm.setOnClickListener(v -> clickListener.TimerOuthandler());

            TextView intitle=rootView.findViewById(R.id.intitle);
            intitle.setText(BaseApplication.app_name);

        rootView.setOnClickListener(v -> {
            // intentionally empty
        });


    }

    public void setClickListener(IOnButtonClickListener listener) {
        clickListener = listener;
    }

    public void stopTimer() {
        cpb_countdown.stop();
    }

    public interface IOnButtonClickListener {

        void TimerOuthandler();

    }


}
