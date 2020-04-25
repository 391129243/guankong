package com.learn.all_electric.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.learn.all_electric.R;
import com.learn.all_electric.bean.ExamListBean;

import java.util.List;

public class ExplainAdapter extends RecyclerView.Adapter<ExplainAdapter.ExplainViewHodler> {
    private List<ExamListBean.ExplainBean> beans;

    public ExplainAdapter(List<ExamListBean.ExplainBean> beans) {
        this.beans = beans;
    }

    @NonNull
    @Override
    public ExplainViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_explain, viewGroup,false);
        return new ExplainViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExplainViewHodler explainViewHodler, int i) {
        ExamListBean.ExplainBean explainBean = beans.get(i);
        if (explainBean.getImagePath() != null && !"".endsWith(explainBean.getImagePath())) {
            explainViewHodler.itemExplainImage.setVisibility(View.VISIBLE);
        }else if(explainBean.getImageMip()!=0) {
            explainViewHodler.itemExplainImage.setVisibility(View.VISIBLE);
            explainViewHodler.itemExplainImage.setImageResource(explainBean.getImageMip());
        }else {
            explainViewHodler.itemExplainImage.setVisibility(View.GONE);
        }
        explainViewHodler.itemExplainTitle.setText(explainBean.getExplain());
//        Log.d("test","数据=="+explainBean.getExplain());
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    class ExplainViewHodler extends RecyclerView.ViewHolder {
        TextView itemExplainTitle;
        ImageView itemExplainImage;

        public ExplainViewHodler(@NonNull View itemView) {
            super(itemView);
            itemExplainTitle = itemView.findViewById(R.id.itemExplainTitle);
            itemExplainImage = itemView.findViewById(R.id.itemExplainImage);
        }
    }
}
