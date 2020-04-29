package com.learn.all_electric.adapter;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
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

                if(null != onItemClickListener){
                    int position = viewHolder.getAdapterPosition();
                    onItemClickListener.onItemClick(position);
                }

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
        ImageView mSeatChecked;

        SeatHolder(@NonNull View itemView) {
            super(itemView);
            mSeat = (TextView) itemView.findViewById(R.id.tv_item_seat);
            mSeatChecked = (ImageView) itemView.findViewById(R.id.rb_item_seat);
        }

        void bind(SeatBean bean, int position) {
            mSeat.setText(bean.getSeatName());


            if(bean.isChecked()){
                mSeatChecked.setBackgroundResource(R.drawable.icon_examroom_seat_pressed);
            }else {
                mSeatChecked.setBackgroundResource(R.drawable.icon_examroom_seat_normal);
            }


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
