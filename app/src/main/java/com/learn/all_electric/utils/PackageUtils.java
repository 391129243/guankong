package com.learn.all_electric.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.learn.all_electric.R;
import com.learn.all_electric.bean.ExamDetailReponse;
import com.learn.all_electric.constants.PackageConstants;

public class PackageUtils {
    /**
     * 根据考试编号获取包名
     * @param questionNumer 考试编号
     * @return
     */
    public static String getPackageName(String questionNumer){
        if(questionNumer.equals("SN1")){
            return PackageConstants.SN12_MIRROR_PACKAGENAME;
        }else if(questionNumer.equals("SN7")){
            return PackageConstants.SN7_BALANCEBAR_PACKAGENAME;
        }else if(questionNumer.equals("SN8")){
            return PackageConstants.SN8_SCALE_DENSITY_PACKAGENAME;
        }else if(questionNumer.equals("SN9")) {
            return PackageConstants.SN9_SCALE_WEIGHT_PACKAGENAME;
        }else if(questionNumer.equals("SN11")){
            return PackageConstants.SN11_LIGHT_PACKAGENAME;
        }else if(questionNumer.equals("SN12")){
            return PackageConstants.SN12_MIRROR_PACKAGENAME;
        }else if(questionNumer.equals("SN13")) {
            return PackageConstants.SN13_CONVEX_PACKAGENAME;
        }else{
            return "";
        }

    }

    //判断设备中是否已经安装了包名为packageName的应用
    public static boolean isApkInstalled(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据实验名称获取考试编号SN
     * @param context
     * @param experimentName
     * @return
     */
    public static String getQuestionNumber(Context context,String experimentName){
        if(experimentName.equals(context.getString(R.string.admin_name_experiment_sn1))){
            return "SN1";
        }else if(experimentName.equals(context.getString(R.string.admin_name_experiment_sn2))){
            return "SN2";
        }else if(experimentName.equals(context.getString(R.string.admin_name_experiment_sn3))){
            return "SN3";
        }else if(experimentName.equals(context.getString(R.string.admin_name_experiment_sn4))) {
            return "SN4";
        }else if(experimentName.equals(context.getString(R.string.admin_name_experiment_sn5))){
            return "SN5";
        }else if(experimentName.equals(context.getString(R.string.admin_name_experiment_sn6))){
            return "SN6";
        }else if(experimentName.equals(context.getString(R.string.admin_name_experiment_sn7))) {
            return "SN7";
        }else if(experimentName.equals(context.getString(R.string.admin_name_experiment_sn8))) {
            return "SN8";
        }else if(experimentName.equals(context.getString(R.string.admin_name_experiment_sn9))) {
            return "SN9";
        }else if(experimentName.equals(context.getString(R.string.admin_name_experiment_sn10))) {
            return "SN10";
        }else if(experimentName.equals(context.getString(R.string.admin_name_experiment_sn11))) {
            return "SN11";
        }else if(experimentName.equals(context.getString(R.string.admin_name_experiment_sn12))) {
            return "SN12";
        }else if(experimentName.equals(context.getString(R.string.admin_name_experiment_sn13))) {
            return "SN13";
        }else{
            return "";
        }
    }



    /**
     * 根据实验名称获取考试编号SN
     * @param context
     * @param questionNo
     * @return
     */
    public static String getExperimentName(Context context,String questionNo){
        if(questionNo.equals("SN1")){
            return context.getString(R.string.admin_name_experiment_sn1);
        }else if(questionNo.equals("SN2")){
            return context.getString(R.string.admin_name_experiment_sn2);
        }else if(questionNo.equals("SN3")){
            return context.getString(R.string.admin_name_experiment_sn3);
        }else if(questionNo.equals("SN4")) {
            return context.getString(R.string.admin_name_experiment_sn4);
        }else if(questionNo.equals("SN5")){
            return context.getString(R.string.admin_name_experiment_sn5);
        }else if(questionNo.equals("SN6")){
            return context.getString(R.string.admin_name_experiment_sn6);
        }else if(questionNo.equals("SN7")) {
            return context.getString(R.string.admin_name_experiment_sn7);
        }else if(questionNo.equals("SN8")) {
            return context.getString(R.string.admin_name_experiment_sn8);
        }else if(questionNo.equals("SN9")) {
            return context.getString(R.string.admin_name_experiment_sn9);
        }else if(questionNo.equals("SN10")) {
            return context.getString(R.string.admin_name_experiment_sn10);
        }else if(questionNo.equals("SN11")) {
            return context.getString(R.string.admin_name_experiment_sn11);
        }else if(questionNo.equals("SN12")) {
            return context.getString(R.string.admin_name_experiment_sn12);
        }else if(questionNo.equals("SN13")) {
            return context.getString(R.string.admin_name_experiment_sn13);
        }else{
            return "";
        }
    }
}
