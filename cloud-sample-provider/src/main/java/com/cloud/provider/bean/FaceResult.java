package com.cloud.provider.bean;

import java.util.List;

/**
 * @ClassName FaceResult
 * @Description 上传人脸响应数据
 * @Author Administrator
 * @DATE 2018/11/12 13:17
 */
public class FaceResult {
    private String face_set_id;
    private String face_set_name;
    private List<FaceResultItem> faces;

    public String getFace_set_id() {
        return face_set_id;
    }

    public void setFace_set_id(String face_set_id) {
        this.face_set_id = face_set_id;
    }

    public String getFace_set_name() {
        return face_set_name;
    }

    public void setFace_set_name(String face_set_name) {
        this.face_set_name = face_set_name;
    }

    public List<FaceResultItem> getFaces() {
        return faces;
    }

    public void setFaces(List<FaceResultItem> faces) {
        this.faces = faces;
    }
}
