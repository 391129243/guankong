package com.learn.all_electric.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.learn.all_electric.R;

public class CustomSettingItem extends RelativeLayout {

    private RelativeLayout setItemLayout;
    private ImageView mTitleIcon;
    private TextView mTitleTxt;
    private TextView mConetentTxt;

    private int icon_id;
    private String title;
    private String content;




    public CustomSettingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initAttrArray(context, attrs);
        initView(context);

    }



    public CustomSettingItem(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    private void initAttrArray(Context context, AttributeSet attrs){
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSettingItem);
        title = mTypedArray.getString(R.styleable.CustomSettingItem_title_text);
        content = mTypedArray.getString(R.styleable.CustomSettingItem_content_text);
        icon_id = mTypedArray.getResourceId(R.styleable.CustomSettingItem_title_icon,R.drawable.signal_icon);
        mTypedArray.recycle();
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.item_setting, this, true);
        setItemLayout = (RelativeLayout)findViewById(R.id.system_item_layout);
        mTitleTxt = (TextView)findViewById(R.id.system_item_title_tv);
        mConetentTxt = (TextView)findViewById(R.id.system_item_title_content_tv);
        mTitleIcon = (ImageView)findViewById(R.id.system_item_img);
        mTitleTxt.setText(title);
        mConetentTxt.setText(content);
        mTitleIcon.setImageResource(icon_id);

    }

    public void setItemContent(String content){
        this.content = content;
        mConetentTxt.setText(content);
    }

    private OnSettingItemClickListener onSetItemClickListener;
    public interface OnSettingItemClickListener{
        public void onSettingItemClick();
    }

    public void setOnSettingItemClickListener(OnSettingItemClickListener listener){
        this.onSetItemClickListener = listener;
        setItemLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(null != onSetItemClickListener){
                    onSetItemClickListener.onSettingItemClick();
                }
            }
        });

    }





}
