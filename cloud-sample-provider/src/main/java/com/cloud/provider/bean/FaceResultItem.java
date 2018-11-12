package com.cloud.provider.bean;

import java.util.Map;

/**
 * @ClassName FaceResultItem
 * @Description 上传人脸之建模详情信息
 * @Author Administrator
 * @DATE 2018/11/12 13:18
 */
public class FaceResultItem {
    private BoundingBox bounding_box;
    private String external_image_id;
    private Map<String,Object> external_fields;
    private String face_id;

    public BoundingBox getBounding_box() {
        return bounding_box;
    }

    public void setBounding_box(BoundingBox bounding_box) {
        this.bounding_box = bounding_box;
    }

    public String getExternal_image_id() {
        return external_image_id;
    }

    public void setExternal_image_id(String external_image_id) {
        this.external_image_id = external_image_id;
    }

    public Map<String, Object> getExternal_fields() {
        return external_fields;
    }

    public void setExternal_fields(Map<String, Object> external_fields) {
        this.external_fields = external_fields;
    }

    public String getFace_id() {
        return face_id;
    }

    public void setFace_id(String face_id) {
        this.face_id = face_id;
    }
}
