package com.hybridsearch.feature;

import com.hybridsearch.utils.FaceTool;
import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;
import net.semanticmetadata.lire.imageanalysis.features.LireFeature;

import java.awt.image.BufferedImage;
import java.util.Base64;


public class HybridFeature implements GlobalFeature, LireFeature {
    private boolean Compact = false;
    private static String FIELD_NAME_HYBRID_FEATURE = "HybridFeature";
    private double[] histogram = new double[256];
    private FaceTool faceTool;
    private Base64.Encoder encoder;

    public double[] getHistogram() {
        return histogram;
    }

    public void setHistogram(double[] histogram) {
        this.histogram = histogram;
    }

    public HybridFeature(){
        faceTool = FaceTool.getInstance();
        encoder = Base64.getEncoder();
    }
    @Override
    public void extract(BufferedImage image) {

    }

    @Override
    public double[] getFeatureVector() {
        return new double[0];
    }

    @Override
    public String getFeatureName() {
        return null;
    }

    @Override
    public String getFieldName() {
        return null;
    }

    @Override
    public byte[] getByteArrayRepresentation() {
        return new byte[0];
    }

    @Override
    public void setByteArrayRepresentation(byte[] featureData) {

    }

    @Override
    public void setByteArrayRepresentation(byte[] featureData, int offset, int length) {

    }

    @Override
    public double getDistance(LireFeature feature) {
        return 0;
    }
}
