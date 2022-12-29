package com.hybridsearch.model;

/**
 * @Author Anthony Z.
 * @Date 29/12/2022
 * @Description:
 */
public class Features {

    private String requestID;
    private int timeUsed;
    private String errorMessage;
    private double[][] feature;
    private int dimension;

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public double[][] getFeature() {
        return feature;
    }

    public void setFeature(double[][] feature) {
        this.feature = feature;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }
}
