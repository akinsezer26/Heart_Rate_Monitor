package com.example.ozcan.heartrate;

/**
 * Created by ozcan on 14.05.2018.
 */

public class MounthStaticsClass {

    private int min;
    private int avg;
    private int max;

    public MounthStaticsClass(int _min,int _avg,int _max){
        min=_min;
        max=_max;
        avg=_avg;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getAvg() {
        return avg;
    }

    public void setAvg(int avg) {
        this.avg = avg;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
