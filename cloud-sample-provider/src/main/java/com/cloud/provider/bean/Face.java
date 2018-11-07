package com.cloud.provider.bean;

/**
 * @ClassName Face
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/7 10:31
 */
public class Face {
    private BoundingBox bounding_box;
    private Attributes attributes;
    private Landmark landmark;

    public BoundingBox getBounding_box() {
        return bounding_box;
    }

    public void setBounding_box(BoundingBox bounding_box) {
        this.bounding_box = bounding_box;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Landmark getLandmark() {
        return landmark;
    }

    public void setLandmark(Landmark landmark) {
        this.landmark = landmark;
    }
}
