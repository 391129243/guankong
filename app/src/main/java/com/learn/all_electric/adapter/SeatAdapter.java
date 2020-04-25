package com.learn.all_electric.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.learn.all_electric.R;
import com.learn.all_electric.bean.SeatBean;

import java.util.List;
import java.util.zip.Inflater;

/**
 * 座位
 */
public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatHolder>{

    private List<SeatBean> beans;
    private int mCheckSeatPosition = -1;


    public SeatAdapter(List<SeatBean> beans) {
        this.beans = beans;
    }

    @NonNull
    @Override
    public SeatHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        SeatHolder viewHolder = new SeatHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_seat,
                        viewGroup, false));
        //设置点击的监听器
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SeatHolder seatHolder, int i) {
        seatHolder.bind(beans.get(i), i);
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }



    class SeatHolder extends RecyclerView.ViewHolder {

        TextView mSeat;
        RadioButton mSeatChecked;

        SeatHolder(@NonNull View itemView) {
            super(itemView);
            mSeat = itemView.findViewById(R.id.tv_item_seat);
            mSeatChecked = itemView.findViewById(R.id.rb_item_seat);
        }

        void bind(SeatBean bean, int position) {
            mSeat.setText(bean.getSeatName());
            mSeatChecked.setOnCheckedChangeListener(null);
            mSeatChecked.setChecked(bean.isChecked());
            mSeatChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        if (mCheckSeatPosition != position) {
                            // 先取消上个item的勾选状态
                            if (mCheckSeatPosition != -1){
                                beans.get(mCheckSeatPosition).setChecked(false);
                                notifyItemChanged(mCheckSeatPosition);
                            }
                            //设置新Item的勾选状态
                            mCheckSeatPosition = position;
                            beans.get(mCheckSeatPosition).setChecked(true);
                            notifyItemChanged(mCheckSeatPosition);
                            if (onItemClickListener!=null){
                                onItemClickListener.onItemClick(mCheckSeatPosition);
                            }

                        }
                    } else {
                        // 取消勾选状态
                        mCheckSeatPosition = -1;
                        beans.get(position).setChecked(false);
                        notifyItemChanged(position);
                    }
                }
            });
        }
    }

    //定义item的点击接口
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;//声明一下这个接口
    //提供setter方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
