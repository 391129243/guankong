package com.learn.all_electric.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jay.easykeyboard.SystemKeyBoardEditText;
import com.jay.easykeyboard.action.KeyBoardActionListener;
import com.learn.all_electric.constants.Constant;
import com.learn.all_electric.R;
import com.learn.all_electric.bean.ExperimentBean;
import com.learn.all_electric.exam.ExamUtils;
import com.learn.all_electric.myinterface.SaveClickListener;
import com.learn.all_electric.utils.BeanUtils;
import com.learn.all_electric.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zuoliji
 * @time 2020/2/22 13:48
 */
public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHodler> {
    private Context mContext;
    private List<ExperimentBean.StepBean> lists;
    private String title;
    private int examCount = 0;//当前所在步骤
    private String[] strs = {"一", "二"};
    private StepViewHodler hodler;
    private ChioseChildAdapter chioseChildAdapter;

    public StepAdapter(Context mContext) {
        this.mContext = mContext;
        lists = new ArrayList<>();
    }

    @NonNull
    @Override
    public StepViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_step, viewGroup, false);
        return new StepViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHodler hodler, int position) {
        this.hodler = hodler;
        ExperimentBean.StepBean stepBean = lists.get(position);
        hodler.sonStepNum.setText("步骤" + (position + 1) + ":");
        hodler.sonSteptitle.setText(CommonUtils.changeText(stepBean.getStepTitle()));
        hodler.sonSteptitleEnd.setVisibility(View.GONE);
        hodler.topEditText.setVisibility(View.GONE);
        hodler.topEditText.setEnabled(false);
        hodler.sonStepRecyclerView.setVisibility(View.GONE);
        hodler.sonStep1Addimage.setVisibility(View.GONE);
        hodler.layoutType3.setVisibility(View.GONE);
        if (position == 0) {
            hodler.itemStepTitle.setText(strs[examCount] + "、" + title);
            hodler.itemStepTitle.setVisibility(View.VISIBLE);
            hodler.itemStepTitleView.setVisibility(View.VISIBLE);
        } else {
            hodler.itemStepTitle.setVisibility(View.GONE);
            hodler.itemStepTitleView.setVisibility(View.GONE);
        }
        hodler.sonStep1Addimage.setVisibility(View.GONE);
        hodler.sonStep2Sub.setVisibility(View.GONE);
        if (stepBean.getType() == 0) {
            typeZero(stepBean, position);
        }
        typeBtn(stepBean, position);

        if (stepBean.getType() == 3) {
            typeThree(stepBean, position);
        }
        if (stepBean.getType() == 4) {
            typeFour(stepBean, position);
        }


        hodler.sonStep2Sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveClickListener != null) {
                    if (stepBean.getType() == 3) {
                        saveClickListener.onSaveAnswer(stepBean, position, 0, hodler.contentEditText1.getText().toString());
                    } else {
                        saveClickListener.onSaveClick(stepBean, position, 0);
                    }
                }
            }
        });
    }

    private void typeZero(ExperimentBean.StepBean stepBean, int position) {
        hodler.sonStep1Addimage.setVisibility(View.VISIBLE);
        for (int i = 0; i < stepBean.getImages().length; i++) {
            hodler.sonStep1Addimage.setImageResource(stepBean.getImages()[i]);
        }
    }

    private void typeBtn(ExperimentBean.StepBean stepBean, int position) {
        hodler.sonStep2Sub.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(stepBean.getBtnStr())) {
            hodler.sonStep2Sub.setVisibility(View.GONE);
        } else {
            hodler.sonStep2Sub.setVisibility(View.VISIBLE);
            hodler.sonStep2Sub.setBackgroundResource(R.drawable.seletor_btn);
            if (stepBean.getSelCount() == 1) {
                hodler.sonStep2Sub.setText("已完成");
                hodler.sonStep2Sub.setEnabled(false);
                hodler.contentEditText1.setEnabled(false);
            } else if (stepBean.getSelCount() == 0) {
                hodler.sonStep2Sub.setText(CommonUtils.changeText(stepBean.getBtnStr()));
                hodler.sonStep2Sub.setEnabled(true);
                hodler.contentEditText1.setEnabled(true);
            } else {
                hodler.sonStep2Sub.setText("已关闭");
                hodler.sonStep2Sub.setBackgroundResource(R.drawable.shape_noclick_btn);
                hodler.sonStep2Sub.setEnabled(false);
                hodler.contentEditText1.setEnabled(false);
            }
        }
    }

    private void typeThree(ExperimentBean.StepBean stepBean, int position) {
        hodler.layoutType3.setVisibility(View.VISIBLE);
        if (stepBean.getChioseMaps() != null) {
            for (Map.Entry<String, List<ExperimentBean.StepBean.StepChioseBean>> entry : stepBean.getChioseMaps().entrySet()) {
                if (entry.getValue() != null&&ExamUtils.ammeter_number.equals(entry.getKey())) {
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        ExperimentBean.StepBean.StepChioseBean chioseBean = entry.getValue().get(i);
                        if (i == 0) {
                            hodler.force_input1_statr.setText(CommonUtils.changeText(chioseBean.getChioseStart()));
                            hodler.contentEditText1.setOnKeyboardActionListener(actionListener);
                            hodler.contentEditText1.setText(chioseBean.getAnswer());
                            hodler.force_input1_end.setText(CommonUtils.changeText(chioseBean.getChioseEnd()));
                        }
                    }
                }
            }
        }

    }

    private void typeFour(ExperimentBean.StepBean stepBean, int position) {
        hodler.sonStepRecyclerView.setVisibility(View.VISIBLE);
        hodler.topEditText.setText("");
        GridLayoutManager manager = new GridLayoutManager(mContext, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        hodler.sonStepRecyclerView.setLayoutManager(manager);
        List<String> lists = new ArrayList<>();
        int answer = -1;
        if (stepBean.getChioseMaps() != null && stepBean.getChioseMaps().get(ExamUtils.is_eques) != null) {
            List<ExperimentBean.StepBean.StepChioseBean> stepChioseBeans = stepBean.getChioseMaps().get(ExamUtils.is_eques);
            if (stepChioseBeans.size() > 0) {
                ExperimentBean.StepBean.StepChioseBean stepChioseBean = stepChioseBeans.get(0);
                lists.addAll(stepChioseBean.getChiose());
                hodler.sonSteptitle.setText(CommonUtils.changeText(stepChioseBean.getChioseStart()));
                hodler.sonSteptitleEnd.setText(CommonUtils.changeText(stepChioseBean.getChioseEnd()));
                hodler.topEditText.setVisibility(View.VISIBLE);
                hodler.sonSteptitleEnd.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(stepChioseBean.getAnswer()) && BeanUtils.isNumeric(stepChioseBean.getAnswer())) {
                    answer = Integer.parseInt(stepChioseBean.getAnswer());
                    if (answer < stepChioseBean.getChiose().size()) {
                        hodler.topEditText.setText(stepChioseBean.getChiose().get(answer));
                    }
                }
            }
        }
        chioseChildAdapter = new ChioseChildAdapter(lists, answer, new ChioseChildAdapter.RadioButtonClickListener() {
            @Override
            public void onRadioButtonClick(int number) {
                chioseChildAdapter.setSelIndex(number);
                if (saveClickListener != null) {
                    saveClickListener.onSaveChiose(stepBean, position, 0, number);
                }
            }
        });
        hodler.sonStepRecyclerView.setAdapter(chioseChildAdapter);

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setExamCount(int examCount) {
        this.examCount = examCount;
    }

    public void addAll(List<ExperimentBean.StepBean> beanList) {
        this.lists.addAll(beanList);
        notifyDataSetChanged();
    }

    public void clear() {
        this.lists.clear();
    }


    class StepViewHodler extends RecyclerView.ViewHolder {
        TextView itemStepTitle, sonStepNum, sonSteptitle, sonSteptitleEnd, force_input1_statr, force_input1_end;
        Button sonStep2Sub;
        View itemStepTitleView;
        ImageView sonStep1Addimage;
        SystemKeyBoardEditText topEditText, contentEditText1;
        RecyclerView sonStepRecyclerView;
        RelativeLayout layoutType3;

        public StepViewHodler(@NonNull View itemView) {
            super(itemView);
            itemStepTitle = itemView.findViewById(R.id.item_step_title);
            itemStepTitleView = itemView.findViewById(R.id.item_step_title_view);

            sonStepNum = itemView.findViewById(R.id.sonStepNum);
            sonSteptitle = itemView.findViewById(R.id.sonSteptitle);
            topEditText = itemView.findViewById(R.id.topEditText);
            sonSteptitleEnd = itemView.findViewById(R.id.sonSteptitleEnd);

            sonStep1Addimage = itemView.findViewById(R.id.son_step_1_addimage);
            sonStep2Sub = itemView.findViewById(R.id.son_step_2_sub);

            sonStepRecyclerView = itemView.findViewById(R.id.son_step_4_RecyclerView);

            layoutType3 = itemView.findViewById(R.id.layoutType3);
            force_input1_statr = itemView.findViewById(R.id.force_input1_statr);
            contentEditText1 = itemView.findViewById(R.id.contentEditText1);
            force_input1_end = itemView.findViewById(R.id.force_input1_end);
        }
    }

    private SaveClickListener saveClickListener;

    public void setOnSaveClickListener(SaveClickListener saveClickListener) {
        this.saveClickListener = saveClickListener;
    }

    private KeyBoardActionListener actionListener = new KeyBoardActionListener() {
        @Override
        public void onComplete() {

        }

        @Override
        public void onTextChange(Editable editable) {

        }

        @Override
        public void onClear() {

        }

        @Override
        public void onClearAll() {

        }
    };
}
