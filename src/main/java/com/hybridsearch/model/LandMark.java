package com.hybridsearch.model;

/**
 * @Author Anthony Z.
 * @Date 23/12/2022
 * @Description:
 *
 * x: [eye_x, , , , ,]
 * y: [eye_y, , , , ,]
 */
public class LandMark {
    private int[] x;
    private int[] y;

    public LandMark(int[] x, int[] y){
        this.x = x;
        this.y = y;
    }


    public int[] getX() {
        return x;
    }

    public void setX(int[] x) {
        this.x = x;
    }

    public int[] getY() {
        return y;
    }

    public void setY(int[] y) {
        this.y = y;
    }
}
