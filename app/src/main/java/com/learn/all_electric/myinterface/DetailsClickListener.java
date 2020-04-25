package com.learn.all_electric.myinterface;

import android.view.View;

import com.learn.all_electric.bean.ExperimentBean;

public interface DetailsClickListener {
    void onDetailsClick(ExperimentBean bean,View view,int position);
}
