package com.learn.all_electric.utils;


import com.learn.all_electric.bean.ExperimentBean;


/* 初始试题工具 */
public class BeanUtils {


    public static int[] rands() {
        boolean[] bool = new boolean[3];
        int randInt = 0;//新建变量用于临时存储产生的随机数
        int[] temp = new int[3];
        for (int i = 0; i < 3; i++) {
            do {
                randInt = (int) (Math.random() * 3);
            } while (bool[randInt]);
            bool[randInt] = true;
            temp[i] = randInt;
            System.out.println(randInt);
        }
        return temp;
    }
    public static int[] rands(int size) {
        boolean[] bool = new boolean[size];
        int randInt = 0;//新建变量用于临时存储产生的随机数
        int[] temp = new int[size];
        for (int i = 0; i < size; i++) {
            do {
                randInt = (int) (Math.random() * size);
            } while (bool[randInt]);
            bool[randInt] = true;
            temp[i] = randInt;
            System.out.println(randInt);
        }
        return temp;
    }

    public static ExperimentBean.StepBean.StepChioseBean setStepChioseBean(String... strings) {
        ExperimentBean.StepBean.StepChioseBean chioseBean = new ExperimentBean.StepBean.StepChioseBean();
        chioseBean.setChioseStart(strings[0]);
        chioseBean.setChioseEnd(strings[1]);
        return chioseBean;
    }


    /**
     * 1.判断字符串是否仅为数字:
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {

        for (int i = str.length(); --i >= 0; ) {

            if (!Character.isDigit(str.charAt(i))) {

                return false;

            }

        }

        return true;
    }

}
