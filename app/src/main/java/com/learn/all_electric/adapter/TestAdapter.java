package com.learn.all_electric.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.learn.all_electric.R;
import com.learn.all_electric.bean.TestBean;

import java.util.List;

/**
 * 实验
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {

    private List<TestBean> beans;
    private SettingClickListener mListener;

    public TestAdapter(List<TestBean> beans) {
        this.beans = beans;
    }

    public void setSettingClickListener(SettingClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public TestHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_test,
                viewGroup, false);
        return new TestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestHolder testHolder, int i) {
        testHolder.bind(beans.get(i));
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    class TestHolder extends RecyclerView.ViewHolder {

        TextView mStep;
        ImageView mSettings;
        LinearLayout mItemStep;

        TestHolder(@NonNull View itemView) {
            super(itemView);
            mStep = itemView.findViewById(R.id.tv_item_step);
            mSettings = itemView.findViewById(R.id.iv_item_settings);
            mItemStep = itemView.findViewById(R.id.ll_item_step);
        }

        void bind(TestBean bean) {
            mStep.setText(bean.getStep());
            mSettings.setVisibility(bean.isSetting() ? View.VISIBLE : View.INVISIBLE);
            mSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null){
                        mListener.onClick();
                    }
                }
            });
            mItemStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        mListener.onJump();
                    }
                }
            });
        }
    }

    public interface SettingClickListener{
        void onClick();
        void onJump();
    }

}
