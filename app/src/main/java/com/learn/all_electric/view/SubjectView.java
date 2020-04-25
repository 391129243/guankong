package com.learn.all_electric.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.learn.all_electric.R;

import static com.learn.all_electric.R.color.exam_setting_title_color;

/**
 * 自定义学科选择
 */
public  class SubjectView extends RelativeLayout implements View.OnClickListener{
    private RelativeLayout parentLayout;
    private ImageView subject_img;
    private TextView subject_tv;
    private Context mContext;
    private int content_color;

    private OnSubjectBtnClickListener mCtmSubjectBtnListener;
    public void setOnImageButtonClickListener(OnSubjectBtnClickListener customImgBtnClickListener){
        //在setter中把这个接口的实现赋值给这个loginview的上面定义的接口
        this.mCtmSubjectBtnListener = customImgBtnClickListener;

    }
    //接口
    public interface OnSubjectBtnClickListener{
        public void customSubjectButtonClicked(View v);//传的参数
    }

    public SubjectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_custom_subjectview, this, true);
        parentLayout = (RelativeLayout)findViewById(R.id.subjectview_parent_layout);
        subject_img = (ImageView)findViewById(R.id.subjectview_subjet_img);
        subject_tv = (TextView)findViewById(R.id.subjectview_subjet_tv);
        parentLayout.setOnClickListener(this);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.SubjectView);
        String content_text = attrArray.getString(R.styleable.SubjectView_content_text);
        content_color = attrArray.getColor(R.styleable.SubjectView_content_text_color, getResources().getColor(R.color.exam_setting_title_color));
        int content_bg = attrArray.getResourceId(R.styleable.SubjectView_content_img, R.drawable.icon_subject_selection_chemical);
        subject_img.setImageResource(content_bg);
        subject_tv.setText(content_text);
        subject_tv.setTextColor(content_color);
        attrArray.recycle();
    }

    /*设置图片接口*/
    public void setBtnImageResource(int resId){
        subject_img.setImageResource(resId);
    }

    /*设置文字接口*/
    public void setText(String str){
        subject_tv.setText(str);
    }
    /*设置文字大小*/
    public void setTextSize(float size){
        subject_tv.setTextSize(size);
    }

    /*设置文字大小*/
    public void setTextColor(int resId){
        subject_tv.setTextColor(resId);
    }

    public void setBackgroudResId(int resId){
        parentLayout.setBackgroundResource(resId);
    }

    @Override
    public void onClick(View view) {
        if(null != mCtmSubjectBtnListener){
            mCtmSubjectBtnListener.customSubjectButtonClicked(view);
        }
    }
}
