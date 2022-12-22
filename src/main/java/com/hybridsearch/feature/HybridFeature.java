package com.hybridsearch.feature;

import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;
import net.semanticmetadata.lire.imageanalysis.features.LireFeature;

import java.awt.image.BufferedImage;

/**
 * @Author Anthony Z. anthony.zj.he@outlook.com
 * @Date 22/12/2022
 * @Description:
 */
public class HybridFeature implements GlobalFeature, LireFeature {
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
