package com.hybridsearch.utils;

import com.google.gson.Gson;
import com.hybridsearch.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Anthony Z.
 * @Date 23/12/2022
 * @Description:
 */
public class FaceTool {

    private static Gson gson = new Gson();
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
        String raw = HttpClientUtil.detectFace(base64);
        System.out.println(raw);
        ExtractResult er = gson.fromJson(raw, ExtractResult.class);
        List<SearchFeature> searchFeatures = new ArrayList<>();
        for(int i=0; i<er.getFaceNums(); i++){
            FaceFeature faceFeature = er.getResult()[i];
            int x1 = faceFeature.getLeft();
            int y1 = faceFeature.getTop();
            int x2 = x1 + faceFeature.getWidth();
            int y2 = y1 + faceFeature.getHeight();
            SearchFeature searchFeature = new SearchFeature(x1, y1, x2, y2, 0, faceFeature.getBlur(), faceFeature.getLandMark());
            searchFeatures.add(searchFeature);
        }
        return searchFeatures;
    }

    public Features getFeatures(List<Face> faces){
        String raw = HttpClientUtil.getFeature(faces);
        Features features = gson.fromJson(raw, Features.class);
        return features;
    }

    public static double cosineSimilarity(double[] vectorA, double[] vectorB){
        double dotProduct = 0.0d;
        double normA = 0.0d;
        double normB = 0.0d;

        for(int i=0; i<vectorA.length; i++){
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }

        if(normA == 0 || normB == 0){
            throw new IllegalArgumentException();
        }

        return (0.5 + 0.5 * (dotProduct/(Math.sqrt(normA) * Math.sqrt(normB))));
    }

}
