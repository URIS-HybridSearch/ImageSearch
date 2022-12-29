package com.hybridsearch.model;

/**
 * @Author Anthony Z.
 * @Date 23/12/2022
 * @Description:
 */
public class Face {

    private String imageBase64 = "";
    private LandMark landMark;

    public Face(String imageBase64, LandMark landMark) {
        this.imageBase64 = imageBase64;
        this.landMark = landMark;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public LandMark getLandMark() {
        return landMark;
    }

    public void setLandMark(LandMark landMark) {
        this.landMark = landMark;
    }
}
