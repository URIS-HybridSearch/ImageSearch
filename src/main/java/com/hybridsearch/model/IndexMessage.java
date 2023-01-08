package com.hybridsearch.model;

/**
 * @Author Anthony HE, anthony.zj.he@outlook.com
 * @Date 8/1/2023
 * @Description:
 */
public class IndexMessage {

    private String id;
    private double[] feature;
    private String videoName = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double[] getFeature() {
        return feature;
    }

    public void setFeature(double[] feature) {
        this.feature = feature;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String video_name) {
        this.videoName = video_name;
    }
}
