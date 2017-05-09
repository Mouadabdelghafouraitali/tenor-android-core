package com.tenor.android.core.model.impl;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * The response model for image
 */
public class Image implements Serializable {
    private static final long serialVersionUID = -8616498739266612929L;
    private String url;

    @SerializedName("dims")
    private int[] dimensions;

    /**
     * @return url of the raw asset
     */
    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return dimensions != null && dimensions.length == 2 ? dimensions[0] : -1;
    }

    public int getHeight() {
        return dimensions != null && dimensions.length == 2 ? dimensions[1] : -1;
    }

    public float getAspectRatio() {
        return (float) getWidth() / getHeight();
    }
}
