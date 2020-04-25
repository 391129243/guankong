package com.learn.all_electric.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;


import com.learn.all_electric.R;

import java.util.List;


public class ChioseChildAdapter extends RecyclerView.Adapter<ChioseChildAdapter.ChioseChildViewHodler> {
    private Context mContext;
    private List<String> lists;
    private int selIndex = -1;

    public ChioseChildAdapter(List<String> lists) {
        this.lists = lists;
    }

    public ChioseChildAdapter(List<String> lists, int selIndex, RadioButtonClickListener listener) {
        this.lists = lists;
        this.selIndex = selIndex;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChioseChildViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chiose_child, viewGroup, false);
        return new ChioseChildViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChioseChildViewHodler chioseChildViewHodler, final int i) {
        chioseChildViewHodler.chiosechildRb.setChecked(false);
        if (selIndex == i) {
            chioseChildViewHodler.chiosechildRb.setChecked(true);
        }
        chioseChildViewHodler.chiosechildRb.setText(lists.get(i));
        chioseChildViewHodler.chiosechildRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRadioButtonClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void setSelIndex(int selIndex) {
        this.selIndex = selIndex;
        notifyDataSetChanged();
    }

    class ChioseChildViewHodler extends RecyclerView.ViewHolder {
        RadioButton chiosechildRb;

        public ChioseChildViewHodler(@NonNull View itemView) {
            super(itemView);
            chiosechildRb = itemView.findViewById(R.id.chiosechildRb);
        }
    }

    RadioButtonClickListener listener;

    public interface RadioButtonClickListener {
        void onRadioButtonClick(int postion);
    }
}
