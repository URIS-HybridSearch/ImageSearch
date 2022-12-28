package com.hybridsearch.model;

/**
 * @Author Anthony Z.
 * @Date 22/12/2022
 * @Description:
 */
public class FeatureCacheModel {
    private byte[] bytes;
    private String key;

    public FeatureCacheModel(byte[] bytes, String key) {
        this.bytes = bytes;
        this.key = key;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
