package com.hybridsearch.model;

/**
 * @Author Anthony Z.
 * @Date 29/12/2022
 * @Description:
 */
public class ExtractResult {
    private String errorMessage;
    private int faceNums;
    private int dimension;
    private String requestID;
    private int timeUsed;
    private FaceFeature[] result;

    public ExtractResult(String errorMessage, int faceNums, int dimension, String requestID, int timeUsed, FaceFeature[] result) {
        this.errorMessage = errorMessage;
        this.faceNums = faceNums;
        this.dimension = dimension;
        this.requestID = requestID;
        this.timeUsed = timeUsed;
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getFaceNums() {
        return faceNums;
    }

    public void setFaceNums(int faceNums) {
        this.faceNums = faceNums;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

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

    public FaceFeature[] getResult() {
        return result;
    }

    public void setResult(FaceFeature[] result) {
        this.result = result;
    }
}
