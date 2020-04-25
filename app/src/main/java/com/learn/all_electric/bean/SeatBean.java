package com.learn.all_electric.bean;

/**
 * 座位
 */
public class SeatBean {

    private String seatName;
    private boolean checked;//是否被选中

    public SeatBean(String seatName,boolean checked){
        this.seatName = seatName;
        this.checked = checked;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
