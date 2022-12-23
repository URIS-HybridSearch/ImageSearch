package com.hybridsearch.utils;

import com.hybridsearch.model.SearchFeature;

import java.util.List;

/**
 * @Author Anthony Z.
 * @Date 23/12/2022
 * @Description:
 */
public class FaceTool {
    private static float threshold = 0.75f;


    private static volatile FaceTool instance = null;

    // not quite sure 这里是否需要双重检查
    public static FaceTool getInstance(){
        if(instance == null){
            synchronized (FaceTool.class){
                if(instance == null){
                    instance = new FaceTool();
                }
            }
        }
        return instance;
    }

    public List<SearchFeature> detectFace(String base64){
//        String raw = HttpClientUtil
        return null;
    }

}
