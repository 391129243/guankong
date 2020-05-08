package com.learn.all_electric.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.learn.all_electric.R;
import com.learn.all_electric.bean.ChooseExperimentBean;

import java.util.ArrayList;
import java.util.List;

public class ExperimentAdapter extends RecyclerView.Adapter<ExperimentAdapter.ExperimentHolder> {
    private List<ChooseExperimentBean> mExperimentList = new ArrayList<ChooseExperimentBean>();
    private static Context mContext;

    public ExperimentAdapter(Context context,List<ChooseExperimentBean> list){
        this.mExperimentList = list;
        this.mContext = context;
    }

    public void setExperimentList(List<ChooseExperimentBean> list){
        this.mExperimentList = list;
    }

    static class ExperimentHolder extends RecyclerView.ViewHolder {
        View experimentView;
        RelativeLayout parentLayout;
        ImageView mUpdateImg;
        TextView mExperimentNameTxt;

        public ExperimentHolder(@NonNull View itemView) {
            super(itemView);
            experimentView = itemView;
            parentLayout = (RelativeLayout)itemView.findViewById(R.id.parent_layout) ;
            mUpdateImg = (ImageView)itemView.findViewById(R.id.item_experiment_update_img);
            mExperimentNameTxt = (TextView)itemView.findViewById(R.id.item_experiment_name_tv);
        }

        void bind(ChooseExperimentBean bean, int position) {
            mExperimentNameTxt.setText(bean.getExperiment_name());
            if(bean.isUpdate()){
                mUpdateImg.setVisibility(View.VISIBLE);
            }else{
                mUpdateImg.setVisibility(View.GONE);
            }

            if(bean.isCheck()){
                parentLayout.setBackgroundResource(R.drawable.shape_item_experiment_bg);
                mExperimentNameTxt.setTextColor(mContext.getResources().getColor(R.color.white));

            }else{
                parentLayout.setBackgroundResource(R.drawable.selector_item_experiment_bg);
                mExperimentNameTxt.setTextColor(mContext.getResources().getColor(R.color.admin_experiment_name_color));
            }


        }

    }

    @NonNull
    @Override
    public ExperimentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyler_experiment,
                viewGroup, false);
        ExperimentHolder experimentHolder = new ExperimentHolder(view);
        //设置点击的监听器
        experimentHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(null != onItemClickListener){
                    int position = experimentHolder.getAdapterPosition();
                    onItemClickListener.onItemClick(position);
                }

            }
        });

        experimentHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(null != onItemClickListener){
                    int position = experimentHolder.getAdapterPosition();
                    onItemClickListener.onItemLongClick(position);
                }
                return false;
            }
        });

        experimentHolder.mUpdateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击进行升级
                if(null != onItemClickListener){
                    int position = experimentHolder.getAdapterPosition();
                    onItemClickListener.onItemUpdateVersion(position);
                }
            }
        });
        return experimentHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ExperimentHolder experimentHolder, int i) {
        experimentHolder.bind(mExperimentList.get(i), i);
    }

    @Override
    public int getItemCount() {
        return mExperimentList.size();
    }



    //定义item的点击接口
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onItemLongClick(int position);
        void onItemUpdateVersion(int position);

    }

    private ExperimentAdapter.OnItemClickListener onItemClickListener;//声明一下这个接口
    //提供setter方法
    public void setOnItemClickListener(ExperimentAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
