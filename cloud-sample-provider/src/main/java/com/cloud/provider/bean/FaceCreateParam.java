package com.cloud.provider.bean;

import java.util.Map;

/**
 * @ClassName FaceCreateParam
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/12 13:25
 */
public class FaceCreateParam {
    /**
     * 人脸库对应的项目ID
     */
    private Long id;
    /**
     * 创建的人脸库名称
     */
    private String face_set_name;
    private String image_base64;
    private Map<String,Object> external_fields;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFace_set_name() {
        return face_set_name;
    }

    public void setFace_set_name(String face_set_name) {
        this.face_set_name = face_set_name;
    }

    public String getImage_base64() {
        return image_base64;
    }

    public void setImage_base64(String image_base64) {
        this.image_base64 = image_base64;
    }

    public Map<String, Object> getExternal_fields() {
        return external_fields;
    }

    public void setExternal_fields(Map<String, Object> external_fields) {
        this.external_fields = external_fields;
    }
}
