package com.learn.all_electric.control;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.learn.all_electric.base.BaseApplication;
import com.learn.all_electric.R;
import com.learn.all_electric.adapter.ExplainAdapter;
import com.learn.all_electric.adapter.StepAdapter;
import com.learn.all_electric.bean.ExperimentBean;
import com.learn.all_electric.exam.ExamUtils;
import com.learn.all_electric.myinterface.SaveClickListener;
import com.learn.all_electric.myinterface.TabClickListener;
import com.learn.all_electric.utils.DialogUtils;
import com.learn.all_electric.view.LoadingDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StepLayout1 extends LinearLayout {


    public StepLayout1(Context context) {
        super(context);
        initView();
    }

    public StepLayout1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public StepLayout1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private TextView topTitle;
    private RecyclerView topRecycler, stepLayoutRecycler;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.step_layout_1, this, true);

        topTitle = view.findViewById(R.id.step_top_title);
        topRecycler = view.findViewById(R.id.step_top_Recycler);
        stepLayoutRecycler = view.findViewById(R.id.stepLayoutRecycler);

        titleLinera = view.findViewById(R.id.step_layout_1_title_linear);
        addTabLayout();
        initData();
    }

    private LinearLayout titleLinera;

    /* 添加顶部tab布局 */
    private void addTabLayout() {
        titleLinera.removeAllViews();
        if (BaseApplication.examListBean != null) {
            for (int j = 0; j < BaseApplication.examListBean.getBeanLists().size(); j++) {
                ExperimentBean experimentBean = BaseApplication.examListBean.getBeanLists().get(j);
                countMap.put(experimentBean.getTitle(), -1);
                TextView textView = new TextView(getContext());
                titleLinera.addView(textView);
                textView.setText(experimentBean.getTitle());
                textView.setBackgroundResource(R.drawable.shape_bark_r5);
                int top = (int) getResources().getDimension(R.dimen.dp_5);
                int left = (int) getResources().getDimension(R.dimen.dp_15);
                textView.setPadding(left, top, left, top);
                if (j > 0) {
                    LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = (int) getResources().getDimension(R.dimen.dp_15);
                    textView.setLayoutParams(params);
                } else {
                    textView.setBackgroundResource(R.drawable.shape_bark_blue_r5);
                }
                textView.setId(j);
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (tabClickListener != null) {
                            tabClickListener.onTabClick(view.getId());
                        }
                    }
                });
            }
        }
    }

    private ExperimentBean bean;
    private int stepCount = 0, examCount = 0;//stepCount当前所在的位置 examCount实验下标
    private Map<String, Integer> countMap = new HashMap<>();
    private StepAdapter mAdapter;

    /* tab点击事件 */
    private TabClickListener tabClickListener = new TabClickListener() {
        @Override
        public void onTabClick(int position) {
            if (bean != null) {
                countMap.put(bean.getTitle(), stepCount);
                if ((examCount == 0 && stepCount < bean.getStepBeans().size() - 1) || (examCount == 1 && stepCount < bean.getStepBeans().size() - 2)) {
                    String title = BaseApplication.examListBean.getBeanLists().get(examCount).getTitle();
                    DialogUtils.init().setDialogUtils(getContext(), title + "实验尚未完成,<br />切换至其他实验将会导致当前实验不可继续操作,确定离开？", "取消", "是", new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //保持全部数据
                            for (ExperimentBean.StepBean stepBean : BaseApplication.examListBean.getBeanLists().get(examCount).getStepBeans()) {
                                if (stepBean.getSelCount() == 0) {
                                    stepBean.setSelCount(2);
                                }
                            }
                            countMap.put(bean.getTitle(), bean.getStepBeans().size() - 1);
                            jumpExam(position);
                        }
                    });
                } else {
                    jumpExam(position);
                }
            }
        }
    };

    private void jumpExam(int position) {
        examCount = position;
        changeTextBg();
        bean = BaseApplication.examListBean.getBeanLists().get(position);
        stepCount = countMap.get(bean.getTitle());
        mAdapter.clear();
        mAdapter.setTitle(bean.getTitle());
        mAdapter.setExamCount(examCount);
        mAdapter.addAll(bean.getStepBeans());
        if (clickListener != null) {
            clickListener.onChange(stepCount, examCount);
        }
    }

    /* 改变tab选择样式 */
    private void changeTextBg() {
        int size = titleLinera.getChildCount();
        for (int i = 0; i < size; i++) {
            View view = titleLinera.getChildAt(i);
            if (examCount == i) {
                view.setBackgroundResource(R.drawable.shape_bark_blue_r5);
            } else {
                view.setBackgroundResource(R.drawable.shape_bark_r5);
            }
        }
    }

    private void initData() {
        if (BaseApplication.examListBean != null) {
            topTitle.setText(BaseApplication.examListBean.getExamName());
            LinearLayoutManager manager = new LinearLayoutManager(this.getContext()) {
                @Override
                public boolean canScrollVertically() {
                    return true;
                }
            };
            topRecycler.setLayoutManager(manager);
            topRecycler.setAdapter(new ExplainAdapter(BaseApplication.examListBean.getExplainBeans()));

            stepLayoutRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new StepAdapter(getContext());
            mAdapter.setOnSaveClickListener(saveClickListener);
            stepLayoutRecycler.setAdapter(mAdapter);
            if (BaseApplication.examListBean.getBeanLists().size() > 0) {
                bean = BaseApplication.examListBean.getBeanLists().get(0);
                mAdapter.setTitle(bean.getTitle());
                mAdapter.addAll(bean.getStepBeans());
            }
        }
    }

    LoadingDialog mLoadingDialog;

    public void showLoading(String string) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getContext(), string, false);
        }
        mLoadingDialog.show();
        mLoadingDialog.setProgressBar();
    }


    /**
     * 隐藏加载的进度框
     */
    public void hideLoading() {
        Handler handler = new Handler();
        if (mLoadingDialog != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mLoadingDialog.hide();
                    if (mLoadingDialog != null) {
                        mLoadingDialog.dismiss();
                    }
                    mLoadingDialog = null;
                }
            });
        }
    }

    /* 点击保存事件 */
    private SaveClickListener saveClickListener = new SaveClickListener() {
        @Override
        public void onSaveClick(ExperimentBean.StepBean stepBean, int position, int index) {
            showLoading("正在保存数据,<br />请勿操作硬件!");
            stepCount = position;
            stepBean.setSelCount(1);
           mAdapter.notifyItemChanged(position);
            //保存
            if (clickListener != null) {
                clickListener.onSaveClick(stepBean, position, examCount);
            }
            if (stepBean.getType() == 2) {
                mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        for (ExperimentBean.StepBean stepBean : BaseApplication.examListBean.getBeanLists().get(examCount).getStepBeans()) {
                            if (stepBean.getSelCount() == 0) {
                                stepBean.setSelCount(2);
                            }
                        }
                        //下一个实验
                        examCount = index + 1;
                        tabClickListener.onTabClick(examCount);
                        stepLayoutRecycler.smoothScrollToPosition(0);
                        hideLoading();
                    }
                });
            }
        }

        @Override
        public void onChange(int position, int index) {

        }

        @Override
        public void onSaveChiose(ExperimentBean.StepBean stepBean, int position, int index, int number) {
            stepCount = position;
            stepBean.setSelCount(1);
            List<ExperimentBean.StepBean.StepChioseBean> stepChioseBeans = stepBean.getChioseMaps().get(ExamUtils.is_eques);
            if (stepChioseBeans != null && stepChioseBeans.size() > 0) {
                stepChioseBeans.get(0).setAnswer(number + "");
            }
            stepBean.getChioseMaps().put(ExamUtils.is_eques, stepChioseBeans);
            mAdapter.notifyItemChanged(position);
            //保存
            if (clickListener != null) {
                clickListener.onSaveChiose(stepBean, position, examCount,number);
            }
        }

        @Override
        public void onSaveAnswer(ExperimentBean.StepBean stepBean, int position, int index, String answer) {
            showLoading("正在保存数据,<br />请勿操作硬件!");
            stepCount = position;
            stepBean.setSelCount(1);
            List<ExperimentBean.StepBean.StepChioseBean> stepChioseBeans = stepBean.getChioseMaps().get(ExamUtils.ammeter_number);
            if (stepChioseBeans != null && stepChioseBeans.size() > 0) {
                stepChioseBeans.get(0).setAnswer(answer);
            }
            stepBean.getChioseMaps().put(ExamUtils.ammeter_number, stepChioseBeans);
            mAdapter.notifyItemChanged(position);
            if (clickListener != null) {
                clickListener.onSaveAnswer(stepBean, position, examCount, answer);
            }
        }
    };

    private SaveClickListener clickListener;

    public void setSaveClickListener(SaveClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setNofitAdapter() {
        if (mAdapter != null) {
            bean = BaseApplication.examListBean.getBeanLists().get(examCount);
            mAdapter.clear();
            mAdapter.setTitle(bean.getTitle());
            stepCount = 0;
            mAdapter.addAll(bean.getStepBeans());
            mAdapter.notifyDataSetChanged();
        }
    }


}
